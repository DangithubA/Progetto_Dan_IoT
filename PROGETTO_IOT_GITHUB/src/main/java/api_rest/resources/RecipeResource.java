package api_rest.resources;


import api_rest.services.ApiAppConfig;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Api("IoT Recipes Resource Endpoint")
public class RecipeResource {

    final protected Logger logger = LoggerFactory.getLogger(RecipeResource.class);

    @SuppressWarnings("serial")
    public static class MissingKeyException extends Exception{}
    final ApiAppConfig conf;

    public RecipeResource(ApiAppConfig conf) throws InterruptedException {
        this.conf = conf;
    }

    @GET
    @Path("quadro")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get lista dei quadri di controllo")
    public Response GetQuadri(@Context ContainerRequestContext requestContext){
        try{
            //
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal Server Error!")).build();
        }
    }

}
