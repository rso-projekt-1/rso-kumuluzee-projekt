import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import rso.project.video.persistence.Video;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationPath("recommender")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecommenderResource {

    private Logger log = LogManager.getLogger(RecommenderResource.class.getName());


    @Inject
    private RecommenderBean recommenderBean;

    @Context
    private UriInfo uriInfo;


    @GET
    public Response getRecommendation(){
        Video video = recommenderBean.recommendRandom();
        if(video==null){
            log.warn("Error getting recommended item.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(video).build();
    }

}
