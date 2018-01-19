import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("subscribe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubscribeResource {
    @Context
    private UriInfo uriInfo;

    @Inject
    private SubscribeBean subscribeBean;

    @GET
    public Response getSubscribes(){
        List<Subscribe> subscribes = subscribeBean.getSubscribers(uriInfo);
        return Response.ok(subscribes).build();
    }


    @GET
    @Path("/{subscribeId}")
    public Response getSubscribe(@PathParam("subscribeId") String subscribeId) {

        Subscribe subscribe = subscribeBean.getSubscribe(subscribeId);

        if (subscribe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(subscribe).build();
    }


    @POST
    public Response createSubscribe(Subscribe subscribe) {

        if (subscribe.getChannel_name() == null || subscribe.getChannel_name().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            subscribe = subscribeBean.createSubscribe(subscribe);
        }

        if (subscribe.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(subscribe).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(subscribe).build();
        }
    }
}
