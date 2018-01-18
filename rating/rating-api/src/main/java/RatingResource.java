import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("playlists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private RatingBean ratingBean;

    @GET
    public Response getRatings(){
        List<Rating> ratings = ratingBean.getRatings(uriInfo);
        return Response.ok(ratings).build();
    }

    @POST
    public Response createRating(Rating rating) {

        if (rating.getScore() == null || rating.getCustomer_id().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            rating = ratingBean.createRating(rating);
        }

        if (rating.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(rating).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(rating).build();
        }
    }

}
