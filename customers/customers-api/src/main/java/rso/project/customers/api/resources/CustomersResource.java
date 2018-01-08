package rso.project.customers.api.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.sun.org.apache.regexp.internal.RE;

import rso.project.Customer;
import rso.project.cdi.CustomersBean;
import rso.project.cdi.configuration.RestProperties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.management.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("customers")
public class CustomersResource {

    @Inject
    private RestProperties restProperties;

    @Inject
    private CustomersBean customersBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getCustomers(){
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
    @Path("healthy")
    public Response setHealthy(){
        boolean healthy = restProperties.isOrderServiceFakeHealthy();
        restProperties.setOrderServiceFakeHealthy(!healthy);
        return Response.ok(!healthy).build();
    }

    @PUT
    @Path("/{Customer_id}")
    public Response putCustomer(@PathParam("Customer_id") String customer_id, Customer customer){
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
        customer = customersBean.createCustomer(customer);
        if(customer == null) return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();

        if(customer.getId() != null){
            return Response.status(Response.Status.OK).entity(customer).build();
        }else{
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{Customer_id}")
    public Response deleteCustomer(@PathParam("Customer_id") String customer_id){
        boolean deleted = customersBean.deleteCustomer(customer_id);

        if(deleted) return Response.status(Response.Status.GONE).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    //REST ENDPOINT
    @GET
    @Path("/filtered")
    public Response getCustomersFiltered(){
        List<Customer> customers = customersBean.getCustomersFilter(uriInfo);
        if(customers == null || customers.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }else{
            return Response.status(Response.Status.OK).entity(customers).build();
        }
    }

}

