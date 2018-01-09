package rso.project.customers.api.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("health-properties")
@ApplicationScoped
public class HealthConfig {


    //@ConfigValue(watch = true)
    @ConfigValue(value = "external-dependencies.customer-service.customerServiceFakeHealthy",watch = true)
    private boolean customerServiceFakeHealthy;

    private boolean fakeHealthy;

    public boolean isFakeHealty(){return fakeHealthy;}
    public void setFakeHealty(boolean fakeHealthy){this.fakeHealthy = fakeHealthy;}


    public boolean isCustomerServiceFakeHealthy(){ return customerServiceFakeHealthy; }
    public void setCustomerServiceFakeHealthy(boolean orderServiceFakeHealthy){
        this.customerServiceFakeHealthy = orderServiceFakeHealthy;
    }

}
