package rso.project.carts.cdi;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import rso.project.Cart;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;

@ApplicationScoped
public class CartsBean {
    @PersistenceContext(unitName = "carts-jpa")
    private EntityManager em;

    public List<Cart> getCarts(UriInfo uriInfo){
        System.out.println(uriInfo.getRequestUri().toString());
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, Cart.class, queryParameters);
    }

    public Cart getCart(String cartID){
        Cart cart = em.find(Cart.class,cartID);
        if(cart==null)throw new NotFoundException();
        return cart;
    }

    public Cart createCart(Cart cart){
        try {
            beginTx();
            em.persist(cart);
            commitTx();

        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return cart;
    }

    public Cart putCart(String cartID, Cart cart){
        Cart o = em.find(Cart.class,cartID);
        if(o == null) return null;

        try {
            beginTx();
            cart.setId(cartID);
            cart = em.merge(cart);
            commitTx();
        }catch (Exception err){
            rollbackTx();
            return null;
        }
        return cart;
    }

    public boolean deleteCart(String cartID){
        Cart cart = em.find(Cart.class,cartID);
        if(cart == null) return false;

        try {
            beginTx();
            em.remove(cart);
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
