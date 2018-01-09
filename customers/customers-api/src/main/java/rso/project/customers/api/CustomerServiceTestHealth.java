package rso.project.customers.api;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import rso.project.customers.api.configuration.HealthConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class CustomerServiceTestHealth implements HealthCheck{
    //@Inject
    //private HealthConfig healthConfig;

    @Override
    public HealthCheckResponse call(){
        /*try{
            if(restProperties.isOrderServiceFakeHealthy()){
                System.out.println("Up");
                return HealthCheckResponse.named(CustomerServiceTestHealth.class.getSimpleName()).up().build();

            }else{
                System.out.println("Down");
                return HealthCheckResponse.named(CustomerServiceTestHealth.class.getSimpleName()).up().build();
            }
        }catch (Exception e){
            System.out.println("Whoops.");
        }*/
        System.out.println("In test");
        return HealthCheckResponse.named(CustomerServiceTestHealth.class.getSimpleName()).up().build();
    }
}
