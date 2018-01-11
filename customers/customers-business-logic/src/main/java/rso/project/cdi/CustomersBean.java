package rso.project.cdi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.fault.tolerance.annotations.CommandKey;
import com.kumuluz.ee.fault.tolerance.annotations.GroupKey;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import rso.project.Customer;
import rso.project.Order;
import rso.project.cdi.configuration.RestProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequestScoped
public class CustomersBean {
    @PersistenceContext(unitName = "customers-jpa")
    private EntityManager em;

    private ObjectMapper objectMapper;

    private HttpClient httpClient;

    @Inject
    @DiscoverService(value="order-service",environment = "dev", version = "*")
    private Optional<String> basePath;

    @Inject
    private RestProperties restProperties;

    @Inject
    private CustomersBean customersBean;



    @PostConstruct
    private void init() {
        httpClient = HttpClientBuilder.create().build();
        objectMapper = new ObjectMapper();

        //basePath = "http://localhost:8081/v1/";
    }

    public Customer getCustomer(String customer_id){
        System.out.println("Get customer-bean");
        Customer customer = em.find(Customer.class, customer_id);
        if(customer == null) throw new NotFoundException();
        //if(restProperties.isOrderServiceEnabled()) {
        List<Order> orders = customersBean.getOrders(customer_id);
        customer.setOrders(orders);
        //}
        return customer;
    }

    @CircuitBreaker(requestVolumeThreshold = 2)
    @Fallback(fallbackMethod = "getOrdersFallback")
    @Timeout(value=2, unit=ChronoUnit.SECONDS)
    public List<Order> getOrders(String customer_id){
        if(basePath.isPresent()){
        try {
            String request_uri = basePath.get()+"/v1/orders?where=customerId:EQ:"+customer_id;
            //request_uri = basePath.get()+"/v1/orders/";

            System.out.println(request_uri);
            HttpGet request = new HttpGet(request_uri);
            HttpResponse response = httpClient.execute(request);

            int status = response.getStatusLine().getStatusCode();
            System.out.println("Status "+status);
            if(status >= 200 && status < 300){
                HttpEntity entity = response.getEntity();

                if(entity != null) return getObjects(EntityUtils.toString(entity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("Error: "+basePath.get());
        } catch (IOException e) {
            String msg = e.getClass().getName()+ " occured: "+e.getMessage();
            System.out.println(msg);
            throw new InternalServerErrorException(msg);
        }
        }else{
            System.out.println("Order-service not discovered.");
        }
        return new ArrayList<>();
    }

    public List<Order> getOrdersFallback(String customer_id){
        System.out.println("Fallback Method.");
        Order tmp = new Order();
        tmp.setId("9999");
        tmp.setCustomerId(customer_id);
        tmp.setTitle("Lion King");
        tmp.setDescription("Lions");
        ArrayList<Order> t = new ArrayList<>();
        t.add(tmp);
        return t;
    }


    private List<Order> getObjects(String json) throws IOException {
        return json == null ? new ArrayList<>() : objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
    }


    public List<Customer> getCustomers(){
        System.out.println("Getting Customers.-bean");
        Query query = em.createNamedQuery("Customer.getAll", Customer.class);
        return query.getResultList();
    }





    public Customer createCustomer(Customer customer){
        System.out.println("Creating customer");
        try{
            beginTx();
            em.persist(customer);
            commitTx();
        }catch (Exception e){
            System.out.println("Creating customer failed.");

            rollbackTx();
            return null;
        }
        return customer;
    }

    public Customer putCustomer(String customer_id, Customer customer){
        Customer c = em.find(Customer.class, customer_id);
        if(c == null) return null;

        try {
            beginTx();
            customer.setId(c.getId());
            customer = em.merge(customer);
            commitTx();
        }catch (Exception e){
            rollbackTx();
            return null;
        }
        return customer;
    }

    //@Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteCustomer(String customer_id){
        Customer customer = em.find(Customer.class, customer_id);
        if(customer == null) return false;

        try {
            beginTx();
            em.remove(customer);
            commitTx();
        }catch (Exception e){
            rollbackTx();
        }
        return true;
    }

    public List<Customer> getCustomersFilter(UriInfo uri_info){
    QueryParameters queryParameters = QueryParameters.query(uri_info.getRequestUri().getQuery()).defaultOffset(0).build();
    List<Customer> customers = JPAUtils.queryEntities(em,Customer.class,queryParameters);
    return customers;
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
