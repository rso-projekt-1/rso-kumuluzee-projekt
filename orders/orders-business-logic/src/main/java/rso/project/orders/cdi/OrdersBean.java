package rso.project.orders.cdi;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import rso.project.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;

@ApplicationScoped
public class OrdersBean {
    @PersistenceContext(unitName = "orders-jpa")
    private EntityManager em;

    public List<Order> getOrders(UriInfo uriInfo){
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, Order.class, queryParameters);
    }

    public Order getOrder(String orderId){
        Order order = em.find(Order.class,orderId);
        if(order==null)throw new NotFoundException();
        return order;
    }

    public Order createOrder(Order order){
        try {
            beginTx();
            em.persist(order);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return order;
    }

    public Order putOrder(String orderId, Order order){
        Order o = em.find(Order.class,orderId);
        if(o == null) return null;

        try {
            beginTx();
            order.setId(orderId);
            order = em.merge(order);
            commitTx();
        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return order;
    }

    public boolean deleteOrder(String orderId){
        Order order = em.find(Order.class,orderId);
        if(order == null) return false;

        try {
            beginTx();
            em.remove(order);
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
