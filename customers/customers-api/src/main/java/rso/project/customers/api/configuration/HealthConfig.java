package rso.project.customers.api.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("health-properties")
@ApplicationScoped
public class HealthConfig {


    @ConfigValue(watch = true)
    private boolean customerServiceFakeHealthy;

    public boolean isCustomerServiceFakeHealthy(){ return customerServiceFakeHealthy; }
    public void setCustomerServiceFakeHealthy(boolean orderServiceFakeHealthy){
        this.customerServiceFakeHealthy = orderServiceFakeHealthy;
    }

}
