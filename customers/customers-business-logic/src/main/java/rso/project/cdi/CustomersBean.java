package rso.project.cdi;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import rso.project.Customer;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@RequestScoped
public class CustomersBean {
    @PersistenceContext(unitName = "customers-jpa")
    private EntityManager em;

    public List<Customer> getCustomers(){
        Query query = em.createNamedQuery("Customer.getAll", Customer.class);
        return query.getResultList();
    }

    public Customer getCustomer(String customer_id){
        Customer customer = em.find(Customer.class, customer_id);
        if(customer == null) throw new NotFoundException();
        return customer;
    }

    public Customer createCustomer(Customer customer){
        try{
            beginTx();
            em.persist(customer);
            commitTx();
        }catch (Exception e){
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
