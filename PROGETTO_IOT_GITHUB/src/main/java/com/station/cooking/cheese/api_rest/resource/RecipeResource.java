package com.station.cooking.cheese.api_rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataConflict;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManager;
import com.station.cooking.cheese.api_rest.services.ApiAppConfig;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import com.station.cooking.cheese.process.MqttSmartObjectProcess;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.util.HashMap;

@Path("/")
@Api("Packs resources Collector")
public class RecipeResource {

    final protected Logger logger = LoggerFactory.getLogger(RecipeResource.class);

    private static InventoryDataManager inventoryDataManager = InventoryDataManager.getInstance();

    @SuppressWarnings("serial")
    public static class MissingKeyException extends Exception{}
    final ApiAppConfig conf;

    public RecipeResource(ApiAppConfig conf){
        this.conf = conf;
    }

    @GET
    @Path("panel")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Get Panels List")
    public Response getPanelsList(@Context ContainerRequestContext req,
                                    @Context UriInfo uriInfo) {
        try {

            logger.info("Loading the panels list {}", inventoryDataManager.panelsList);

            return Response.ok(inventoryDataManager.panelsList.keySet()).build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
}