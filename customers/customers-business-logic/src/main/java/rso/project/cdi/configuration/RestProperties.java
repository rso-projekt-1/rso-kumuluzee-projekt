package rso.project.cdi.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-properties")
public class RestProperties {

    @ConfigValue(value = "external-dependencies.order-service.enabled",watch = true)
    private boolean orderServiceEnabled;

    @ConfigValue(value = "external-dependencies.order-service.fakeHealthy",watch = true)
    private boolean orderServiceFakeHealthy;

    public boolean isOrderServiceFakeHealthy(){ return orderServiceFakeHealthy; }
    public void setOrderServiceFakeHealthy(boolean orderServiceFakeHealthy){
        this.orderServiceFakeHealthy = orderServiceFakeHealthy;
    }

    public boolean isOrderServiceEnabled(){
        return orderServiceEnabled;
    }
    public void setOrderServiceEnabled(boolean orderServiceEnabled){
        this.orderServiceEnabled = orderServiceEnabled;
    }


    @ConfigValue(value = "external-dependencies.cart-service.enabled",watch = true)
    private boolean cartServiceEnabled;

    @ConfigValue(value = "external-dependencies.cart-service.fakeHealthy",watch = true)
    private boolean cartServiceFakeHealthy;

    public boolean isCartServiceFakeHealthy(){ return cartServiceFakeHealthy; }
    public void setCartServiceFakeHealthy(boolean cartServiceFakeHealthy){
        this.cartServiceFakeHealthy = cartServiceFakeHealthy;
    }

    public boolean isCartServiceEnabled(){
        return cartServiceEnabled;
    }
    public void setCartServiceEnabled(boolean cartServiceEnabled){
        this.cartServiceEnabled = cartServiceEnabled;
    }
}
