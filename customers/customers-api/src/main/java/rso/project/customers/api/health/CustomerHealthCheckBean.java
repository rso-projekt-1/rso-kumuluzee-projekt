package rso.project.customers.api.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import rso.project.cdi.configuration.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class CustomerHealthCheckBean implements HealthCheck{
    @Inject
    private RestProperties restProperties;

    @Override
    public HealthCheckResponse call() {
        try {
            if (restProperties.isOrderServiceFakeHealthy()) {
                System.out.println("Up");
                return HealthCheckResponse.named(CustomerHealthCheckBean.class.getSimpleName()).up().build();

            } else {
                System.out.println("Down");
                return HealthCheckResponse.named(CustomerHealthCheckBean.class.getSimpleName()).up().build();
            }
        } catch (Exception e) {
            System.out.println("Whoops.");
        }
        return HealthCheckResponse.named(CustomerHealthCheckBean.class.getSimpleName()).up().build();
    }
}
