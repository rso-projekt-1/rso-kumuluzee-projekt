import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.apache.http.client.HttpClient;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
public class RatingBean {
    @PersistenceContext(unitName = "rating-jpa")
    private EntityManager em;

    private ObjectMapper objectMapper;

    private HttpClient httpClient;

    private Logger log = LogManager.getLogger(RatingBean.class.getName());

    public List<Rating> getRatings(UriInfo uriInfo){
        log.info("Geting ratings..");
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, Rating.class, queryParameters);
    }


    public Rating createRating(Rating rating){
        try {
            beginTx();
            em.persist(rating);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return rating;
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
