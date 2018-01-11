package rso.project.cdi.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-properties")
public class RestProperties {

    @ConfigValue(watch = true)
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
}
