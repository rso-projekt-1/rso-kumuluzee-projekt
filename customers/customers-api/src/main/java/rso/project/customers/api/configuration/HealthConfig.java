package rso.project.customers.api.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("health-properties")
@ApplicationScoped
public class HealthConfig {

    //etcd path environments/dev/services/customer-service/1.0.0/config/health-properties/customer-service-fake-healthy
    //@ConfigValue(value = "external-dependencies.customer-service.customerServiceFakeHealthy",watch = true)
    @ConfigValue(watch = true)
    private boolean customerServiceFakeHealthy;



    public boolean isCustomerServiceFakeHealthy(){ return customerServiceFakeHealthy; }
    public void setCustomerServiceFakeHealthy(boolean orderServiceFakeHealthy){
        this.customerServiceFakeHealthy = orderServiceFakeHealthy;
    }

}
