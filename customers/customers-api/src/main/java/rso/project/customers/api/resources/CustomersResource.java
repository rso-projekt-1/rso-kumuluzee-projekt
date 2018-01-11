package rso.project.customers.api.resources;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.logs.cdi.Log;
import com.kumuluz.ee.logs.cdi.LogParams;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.sun.org.apache.regexp.internal.RE;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import rso.project.Customer;
import rso.project.cdi.CustomersBean;
import rso.project.cdi.configuration.RestProperties;
import rso.project.customers.api.configuration.HealthConfig;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.management.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Log
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("customers")
public class CustomersResource {

    @Inject
    private HealthConfig healthConfig;

    @Inject
    private CustomersBean customersBean;

    @Context
    protected UriInfo uriInfo;

    /*@Inject
    @Metric(name = "customer_request_counter")
    private Counter customerRequestCounter;
    */

    @Inject
    @Metric(name = "customer_request_meter")
    private Meter customer_request_meter;

    //count created users with createCustomer
    @Inject
    @Metric(name = "customer_counter")
    private Counter customerCounter;

    private Logger log = LogManager.getLogger(CustomersResource.class.getName());

    @GET
    @Path("/customerCount")
    public Response getCustomerCount(){
        return Response.ok(customerCounter).build();
    }

    @GET
    @Path("/customerRequestMeter")
    public Response getCustomerRequestsMeter(){
        return Response.ok(customer_request_meter).build();
    }



    @GET
    public Response getCustomers(){
        log.info("Getting Customers");
        customer_request_meter.mark();
        List<Customer> customerList = customersBean.getCustomers();
        return Response.ok(customerList).build();
    }

    @GET
    @Path("/{Customer_id}")
    public Response getCustomer(@PathParam("Customer_id") String customer_id){
        Customer customer = customersBean.getCustomer(customer_id);
        if(customer == null) return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(customer).build();
    }

    @GET
    @Path("/healthy")
    public Response getHealthy(){
        log.info("Get health property");

        boolean healthy = healthConfig.isCustomerServiceFakeHealthy();
        //healthConfig.setCustomerServiceFakeHealthy(!healthy);
        return Response.status(Response.Status.OK).entity(healthy).build();
    }
    @POST
    @Path("/healthy")
    public Response setHealthy(Boolean healthy){
        log.info("Set health property/simulate bad response");
        //boolean healthy = healthConfig.isCustomerServiceFakeHealthy();
        healthConfig.setCustomerServiceFakeHealthy(healthy);
        return Response.status(Response.Status.OK).entity(healthy).build();
    }

    @PUT
    @Path("/{Customer_id}")
    public Response putCustomer(@PathParam("Customer_id") String customer_id, Customer customer){
        log.info("Put customer "+customer_id);
        customer = customersBean.putCustomer(customer_id,customer);
        if(customer == null) return Response.status(Response.Status.NOT_FOUND).build();

        if(customer.getId() != null){
            return Response.status(Response.Status.OK).entity(customer).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @POST
    public Response createCustomer(Customer customer){
        log.info("Create customer "+customer.getFirst_name()+" "+customer.getLast_name());
        customer = customersBean.createCustomer(customer);
        if(customer == null) return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();

        if(customer.getId() != null){
            //increase user count
            customerCounter.inc();
            return Response.status(Response.Status.OK).entity(customer).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{Customer_id}")
    public Response deleteCustomer(@PathParam("Customer_id") String customer_id){
        log.info("Delete customer "+customer_id);
        boolean deleted = customersBean.deleteCustomer(customer_id);

        if(deleted) return Response.status(Response.Status.GONE).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //REST ENDPOINT
    @GET
    @Path("/filtered")
    public Response getCustomersFiltered(){
        log.info("Customer Filtered ");
        List<Customer> customers = customersBean.getCustomersFilter(uriInfo);
        if(customers == null || customers.size() == 0) {
            log.warn("Customer Filter not found.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }else{
            return Response.status(Response.Status.OK).entity(customers).build();
        }
    }

}

