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

public class PlaylistsResource {
    @Context
    private UriInfo uriInfo;

    @Inject
    private PlaylistsBean playlistsBean;

    @GET
    public Response getPlaylists(){
        List<Playlist> playlists = playlistsBean.getPlaylists(uriInfo);
        return Response.ok(playlists).build();
    }


    @GET
    @Path("/{playlistId}")
    public Response getPlaylist(@PathParam("playlistId") String playlistId) {

        Playlist playlist = playlistsBean.getPlaylist(playlistId);

        if (playlist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(playlist).build();
    }

    @POST
    public Response createPlaylist(Playlist playlist) {

        if (playlist.getPlaylist_name() == null || playlist.getPlaylist_name().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            playlist = playlistsBean.createPlaylist(playlist);
        }

        if (playlist.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(playlist).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(playlist).build();
        }
    }

    @PUT
    @Path("{playlistId}")
    public Response putPlaylist(@PathParam("playlistId") String playlistId, Playlist playlist) {

        playlist = playlistsBean.putPlaylist(playlistId, playlist);

        if (playlist == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (playlist.getId() != null)
                return Response.status(Response.Status.OK).entity(playlist).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{playlistId}")
    public Response deleteCustomer(@PathParam("playlistId") String playlistId) {

        boolean deleted = playlistsBean.deletePlaylist(playlistId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
