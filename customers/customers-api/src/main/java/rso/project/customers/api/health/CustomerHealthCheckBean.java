package rso.project.customers.api.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import rso.project.cdi.configuration.RestProperties;
import rso.project.customers.api.configuration.HealthConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class CustomerHealthCheckBean implements HealthCheck{

    //@Inject
    //private HealthConfig healthConfig;

    @Override
    public HealthCheckResponse call() {
        /*try {
            if (healthConfig.isCustomerServiceFakeHealthy()) {
                System.out.println("Up");
                return HealthCheckResponse.named(CustomerHealthCheckBean.class.getSimpleName()).up().build();

            } else {
                System.out.println("Down");
                return HealthCheckResponse.named(CustomerHealthCheckBean.class.getSimpleName()).up().build();
            }
        } catch (Exception e) {
            System.out.println("Whoops.");
        }*/
        return HealthCheckResponse.named(CustomerHealthCheckBean.class.getSimpleName()).up().build();
    }
}
