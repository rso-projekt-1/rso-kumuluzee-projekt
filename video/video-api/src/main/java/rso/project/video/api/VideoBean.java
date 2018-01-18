package rso.project.video.api;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import rso.project.video.persistence.Video;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class VideoBean {
    @PersistenceContext(unitName = "video-jpa")
    private EntityManager em;


    public List<Video> getVideos(UriInfo uriInfo){
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, Video.class, queryParameters);
    }

    public Video getVideo(String videoId){
        Video video = em.find(Video.class,videoId);
        if(video==null)throw new NotFoundException();
        return video;
    }

    public Video createVideo(Video video){
        try {
            beginTx();
            em.persist(video);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return video;
    }

    public Video putVideo(String videoId, Video video){
        Video o = em.find(Video.class,videoId);
        if(o == null) return null;

        try {
            beginTx();
            video.setId(videoId);
            video = em.merge(video);
            commitTx();
        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return video;
    }

    public boolean deleteVideo(String videoId){
        Video video = em.find(Video.class,videoId);
        if(video == null) return false;

        try {
            beginTx();
            em.remove(video);
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
