package rso.project.demo;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("demo")
public class DemoResource {

    @GET
    public Response getCustomers(){
        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder tmp = Json.createArrayBuilder();
        tmp.add("vz1528");
        tmp.add("jj5715");
        object.add("clani",tmp.build());
        object.add("opis_projekta","Projekt bo vseboval portal za pretok video vsebin. Zaenkrat vsebuje"
        + " 2 osnovni mikrostoritvi, ki med sabo komunicirata. Vzpostavljen je service discovery in integracija s travis CI."
        + " Mikrostoritve so postavljene v oblak z uporabo kubernetes.");

        JsonArrayBuilder mikrostoritve = Json.createArrayBuilder();
        mikrostoritve.add("http://169.51.22.8:30950/v1/orders/");
        mikrostoritve.add("http://169.51.22.8:31931/v1/customers/");

        object.add("mikrostoritve",mikrostoritve.build());

        JsonArrayBuilder github = Json.createArrayBuilder();
        github.add("https://github.com/rso-projekt-1/rso-kumuluzee-projekt");
        github.add("https://github.com/rso-projekt-1/rso-kumuluzee-projekt");
        object.add("github",github.build());

        JsonArrayBuilder travis = Json.createArrayBuilder();
        travis.add("https://travis-ci.org/rso-projekt-1/rso-kumuluzee-projekt/");
        travis.add("https://travis-ci.org/rso-projekt-1/rso-kumuluzee-projekt/");
        object.add("travis",travis.build());

        JsonArrayBuilder dockerhub = Json.createArrayBuilder();
        dockerhub.add("https://hub.docker.com/r/vitjanz/rso-projekt/");
        dockerhub.add("https://hub.docker.com/r/vitjanz/rso-projekt/");
        object.add("dockerhub",dockerhub.build());


        return Response.status(Response.Status.OK).entity(object.build().toString()).build();
    }
}
