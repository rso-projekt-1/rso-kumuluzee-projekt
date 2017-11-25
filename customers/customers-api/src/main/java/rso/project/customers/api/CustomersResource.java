package rso.project.customers.api;

import com.sun.org.apache.regexp.internal.RE;
import org.glassfish.jersey.process.internal.RequestScoped;
import rso.project.Customer;
import rso.project.cdi.CustomersBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomersResource {

    @Inject
    private CustomersBean customersBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getCusomers(){
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
        List<Customer> customers;
        //customer = customersBean.getCustomer
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}

