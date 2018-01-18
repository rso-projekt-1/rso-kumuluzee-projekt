package rso.project.video.api;


import rso.project.video.persistence.Video;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Path("/video")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VideoResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private VideoBean videoBean;

    @GET
    public Response getVideo(){
        List<Video> videos = videoBean.getVideos(uriInfo);
        return Response.ok(videos).build();
    }

    @GET
    @Path("/{videoId}")
    public Response getVideo(@PathParam("videoId") String videoId) {

        Video video = videoBean.getVideo(videoId);

        if (video == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(video).build();
    }
    @POST
    public Response createVideo(Video video) {

        if (video.getTitle() == null || video.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            video = videoBean.createVideo(video);
        }

        if (video.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(video).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(video).build();
        }
    }

}
