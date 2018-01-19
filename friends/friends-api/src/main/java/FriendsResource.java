import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("friends")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FriendsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private FriendsBean friendsBean;

    @GET
    public Response getFriends(){
        List<Friend> friends = friendsBean.getFriends(uriInfo);
        return Response.ok(friends).build();
    }


    @GET
    @Path("/{friendId}")
    public Response getFriendships(@PathParam("friendId") String friendId) {

        Friend friend = friendsBean.getFriend(friendId);

        if (friend == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(friend).build();
    }

    @POST
    public Response createFriend(Friend friends) {

        if (friends.getCustomer_id() == null || friends.getCustomer_id().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            friends = friendsBean.createFriend(friends);
        }

        if (friends.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(friends).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(friends).build();
        }
    }

}
