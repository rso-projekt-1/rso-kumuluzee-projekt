import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import rso.project.video.persistence.Video;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PlaylistsBean {
    @PersistenceContext(unitName = "playlists-jpa")
    private EntityManager em;

    private ObjectMapper objectMapper;

    private HttpClient httpClient;

    private Logger log = LogManager.getLogger(PlaylistsBean.class.getName());

    @Inject
    private PlaylistsBean playlistsBean;

    @Inject
    @DiscoverService(value="video-service",environment = "dev", version = "*")
    private Optional<String> basePath;

    @PostConstruct
    private void init() {
        HttpClientBuilder tmp = HttpClientBuilder.create();
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(2000);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(2000);
        tmp.setDefaultRequestConfig(requestBuilder.build());
        httpClient = tmp.build();
        objectMapper = new ObjectMapper();
    }

    public List<Playlist> getPlaylists(UriInfo uriInfo){
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, Playlist.class, queryParameters);
    }

    public Playlist getPlaylist(String playlistsId){
        Playlist playlists = em.find(Playlist.class,playlistsId);
        List<String> video_ids = playlists.getVideo_ids();
        List<Video> videos = playlistsBean.getVideos(video_ids);

        playlists.setFilms(videos);

        if(playlists==null)throw new NotFoundException();
        return playlists;
    }

    public List<Video> getVideos(List<String> video_ids){
        if(basePath.isPresent()){
            ArrayList<Video> videos = new ArrayList<Video>();
            for(String video_id : video_ids) {
                try {
                    String request_uri = basePath.get() + "/v1/video/" + video_id;
                    //request_uri = basePath.get()+"/v1/orders/";

                    System.out.println(request_uri);
                    HttpGet request = new HttpGet(request_uri);
                    HttpResponse response = httpClient.execute(request);

                    int status = response.getStatusLine().getStatusCode();
                    log.info("Status " + status);
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        if(entity != null) {
                            Video v = objectMapper.readValue(EntityUtils.toString(entity), objectMapper.getTypeFactory().constructType(Video.class));
                            videos.add(v);
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    log.error("Error: " + basePath.get());
                } catch (IOException e) {
                    String msg = e.getClass().getName() + " occured: " + e.getMessage();
                    System.out.println(msg);
                    throw new InternalServerErrorException(msg);
                }
            }
            return videos;
        }else{
            log.warn("Order-service not discovered.");
        }
        return new ArrayList<>();
    }


    public Playlist createPlaylist(Playlist playlists){
        try {
            beginTx();
            em.persist(playlists);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return playlists;
    }

    public Playlist putPlaylist(String playlistsId, Playlist playlists){
        Playlist o = em.find(Playlist.class,playlistsId);
        if(o == null) return null;

        try {
            beginTx();
            playlists.setId(playlistsId);
            playlists = em.merge(playlists);
            commitTx();
        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return playlists;
    }

    public boolean deletePlaylist(String playlistsId){
        Playlist playlists = em.find(Playlist.class,playlistsId);
        if(playlists == null) return false;

        try {
            beginTx();
            em.remove(playlists);
            commitTx();
        }catch (Exception err){
            rollbackTx();
            return false;
        }
        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
