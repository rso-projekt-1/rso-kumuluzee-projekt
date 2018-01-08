package rso.project.customers.api.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("health-properties")
public class HealthConfig {


    @ConfigValue(value = "external-dependencies.customer-service.customerServiceFakeHealthy",watch = true)
    private boolean customerServiceFakeHealthy;

    public boolean isCustomerServiceFakeHealthy(){ return customerServiceFakeHealthy; }
    public void setCustomerServiceFakeHealthy(boolean orderServiceFakeHealthy){
        this.customerServiceFakeHealthy = orderServiceFakeHealthy;
    }

}
