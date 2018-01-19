import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import rso.project.video.persistence.Video;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class FriendsBean {
    @PersistenceContext(unitName = "friends-jpa")
    private EntityManager em;

    private Logger log = LogManager.getLogger(FriendsBean.class.getName());


    public List<Friend> getFriends(UriInfo uriInfo){
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, Friend.class, queryParameters);
    }

    public Friend getFriend(String friend_id){
        Friend friends = em.find(Friend.class,friend_id);

        if(friends==null)throw new NotFoundException();
        return friends;
    }

    public Friend createFriend(Friend friend){
        try {
            beginTx();
            em.persist(friend);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        log.info("Added friendship..");
        return friend;
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
