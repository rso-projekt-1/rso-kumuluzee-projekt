package rso.project.carts.api.resources;

import rso.project.Cart;
import rso.project.carts.cdi.CartsBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private CartsBean cartsBean;

    @GET
    public Response getCarts(){
        List<Cart> carts = cartsBean.getCarts(uriInfo);
        return Response.ok(carts).build();
    }


    @GET
    @Path("/{cartId}")
    public Response getCart(@PathParam("cartId") String cartId) {

        Cart cart = cartsBean.getCart(cartId);

        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(cart).build();
    }

    @POST
    public Response createCart(Cart cart) {

        if (cart.getTitle() == null || cart.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            cart = cartsBean.createCart(cart);
        }

        if (cart.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(cart).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(cart).build();
        }
    }

    @PUT
    @Path("{cartId}")
    public Response putCart(@PathParam("cartId") String cartId, Cart cart) {

        cart = cartsBean.putCart(cartId, cart);

        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (cart.getId() != null)
                return Response.status(Response.Status.OK).entity(cart).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{cartId}")
    public Response deleteCustomer(@PathParam("cartId") String cartId) {

        boolean deleted = cartsBean.deleteCart(cartId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
