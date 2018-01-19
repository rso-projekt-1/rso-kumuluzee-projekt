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
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import rso.project.Customer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
class SubscribeBean {

    @PersistenceContext(unitName = "subscribe-jpa")
    private EntityManager em;

    private ObjectMapper objectMapper;

    private HttpClient httpClient;

    private Logger log = LogManager.getLogger(SubscribeBean.class.getName());

    @Inject
    private SubscribeBean subscribeBean;

    @Inject
    @DiscoverService(value="customer-service",environment = "dev", version = "*")
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

    public List<Subscribe> getSubscribes(UriInfo uriInfo){
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();

        return JPAUtils.queryEntities(em, Subscribe.class, queryParameters);
    }

    public Subscribe getSubscribe(String subscribeId){
        Subscribe subscribe = em.find(Subscribe.class,subscribeId);
        List<String> subscriber_ids = subscribe.getSubscriber_ids();
        List<Customer> subs = subscribeBean.getSubscribers(subscriber_ids);

        subscribe.setSubscribers(subs);

        if(subscribe==null)throw new NotFoundException();
        return subscribe;
    }
    @CircuitBreaker(requestVolumeThreshold = 2)
    @Fallback(fallbackMethod = "getSubscribersFallback")
    @Timeout(value=2, unit= ChronoUnit.SECONDS)
    public List<Customer> getSubscribers(List<String> subscriber_ids){

        if(basePath.isPresent()){
            ArrayList<Customer> customers = new ArrayList<Customer>();
            for(String customer_id : subscriber_ids) {
                try {
                    String request_uri = basePath.get() + "/v1/customers/" + customer_id;
                    //request_uri = basePath.get()+"/v1/orders/";

                    System.out.println(request_uri);
                    HttpGet request = new HttpGet(request_uri);
                    HttpResponse response = httpClient.execute(request);

                    int status = response.getStatusLine().getStatusCode();
                    log.info("Status " + status);
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        if(entity != null) {
                            Customer v = objectMapper.readValue(EntityUtils.toString(entity), objectMapper.getTypeFactory().constructType(Customer.class));
                            customers.add(v);
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
            return customers;
        }else{
            log.warn("Order-service not discovered.");
        }
        return new ArrayList<>();
    }

    public List<Customer> getSubscribersFallback(List<String> subscriber_ids){
        return new ArrayList<>();
    }

    public Subscribe createSubscribe(Subscribe subscription){
        try {
            beginTx();
            em.persist(subscription);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return subscription;
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
