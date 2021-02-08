package com.station.cooking.cheese.api_rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.station.cooking.cheese.api_rest.dto.RecipeCreationRequest;
import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataConflict;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManager;
import com.station.cooking.cheese.api_rest.services.ApiAppConfig;
import com.station.cooking.cheese.device.PanelMqttSmartObject;
import com.station.cooking.cheese.model.PanelDescriptor;
import com.station.cooking.cheese.model.RecipeDescriptor;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

@Path("/")
@Api("Recipe Resources")
public class RecipeResource {

    final protected Logger logger = LoggerFactory.getLogger(RecipeResource.class);

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
    @ApiOperation(value="Get Panels List")
    public Response getPanelsList(@Context ContainerRequestContext req,
                                    @Context UriInfo uriInfo) {
        try {

            logger.info("Loading the panels list");

            return Response.ok(this.conf.getInventoryDataManager().getControlPanels().keySet()).build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
    @GET
    @Path("panel/{panel_id}/recipe")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Get Recipe of the Panel")
    public Response getPanel(@Context ContainerRequestContext req,
                                  @Context UriInfo uriInfo, @PathParam("panel_id") String panel_id) {
        try {

            logger.info("Loading Panel's Recipe");

            HashMap<String, PanelDescriptor> panelDescriptorHashMap = new HashMap<>();
            panelDescriptorHashMap = this.conf.getInventoryDataManager().getControlPanels();

            if (panelDescriptorHashMap.containsKey(panel_id)){
                return Response.ok(panelDescriptorHashMap.get(panel_id).getRecipe()).build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Panel Not Found !")).build();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
    @POST
    @Path("panel/{panel_id}/recipe")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Update Recipe of the Panel")
    public Response createRecipe(@Context ContainerRequestContext req,
                                 @Context UriInfo uriInfo, @PathParam("panel_id") String panel_id, RecipeCreationRequest recipeCreationRequest) {
        try {

            logger.info("Creating Panel's Recipe");

            if (this.conf.getInventoryDataManager().getControlPanels().containsKey(panel_id)){

                RecipeDescriptor recipeDescriptor = (RecipeDescriptor) recipeCreationRequest;

                this.conf.getInventoryDataManager().createRecipe(panel_id, recipeDescriptor);

                return Response.created(new URI(String.format("%s",uriInfo.getAbsolutePath()))).build();

            }else{
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Panel Not Found !")).build();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
    @DELETE
    @Path("panel/{panel_id}/recipe")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Delete Recipe of the Panel")
    public Response deleteRecipe(@Context ContainerRequestContext req,
                                 @Context UriInfo uriInfo, @PathParam("panel_id") String panel_id) {
        try {

            logger.info("Creating Panel's Recipe");

            if (this.conf.getInventoryDataManager().getControlPanels().containsKey(panel_id)){

                this.conf.getInventoryDataManager().deleteRecipe(panel_id);

                return Response.noContent().build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Panel Not Found !")).build();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
    @PUT
    @Path("panel/{panel_id}/recipe/{phase}/temperature")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value="Modify temperature of a phase")
    public Response modifyTemperature(@Context ContainerRequestContext req,
                                 @Context UriInfo uriInfo, @PathParam("panel_id") String panel_id, @PathParam("phase") String phase, String temperature) {
        try {

            Double doubleTemp = Double.parseDouble(temperature);

            logger.info("Modifying Phase{} temperature", phase);

            if (this.conf.getInventoryDataManager().getControlPanels().containsKey(panel_id)){

                ArrayList<String> phases = this.conf.getInventoryDataManager().getControlPanels().get(panel_id).getRecipe().getPhases();
                ArrayList<Double> temps = this.conf.getInventoryDataManager().getControlPanels().get(panel_id).getRecipe().getTemperatures();

                for(int i = 0; i < temps.size(); i++){
                    if(phases.get(i).equals(phase)){
                        temps.set(i, doubleTemp);
                    }
                }

                this.conf.getInventoryDataManager().updateTemp(temps, panel_id);

                return Response.noContent().build();

            }else{
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Panel Not Found !")).build();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
    @PUT
    @Path("panel/{panel_id}/recipe/{phase}/time")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value="Modify time of a phase")
    public Response modifyTime(@Context ContainerRequestContext req,
                                      @Context UriInfo uriInfo, @PathParam("panel_id") String panel_id, @PathParam("phase") String phase, String time) {
        try {

            Double doubleTime = Double.parseDouble(time);

            logger.info("Modifying Phase{} time", phase);

            if (this.conf.getInventoryDataManager().getControlPanels().containsKey(panel_id)){

                ArrayList<String> phases = this.conf.getInventoryDataManager().getControlPanels().get(panel_id).getRecipe().getPhases();
                ArrayList<Double> times = this.conf.getInventoryDataManager().getControlPanels().get(panel_id).getRecipe().getTimes();

                for(int i = 0; i < times.size(); i++){
                    if(phases.get(i).equals(phase)){
                        times.set(i, doubleTime);
                    }
                }

                this.conf.getInventoryDataManager().updateTime(times, panel_id);

                return Response.noContent().build();

            }else{
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Panel Not Found !")).build();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
    @PUT
    @Path("panel/{panel_id}/recipe/{phase}")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value="Modify name of a phase")
    public Response modifyPhase(@Context ContainerRequestContext req,
                               @Context UriInfo uriInfo, @PathParam("panel_id") String panel_id, @PathParam("phase") String phase, String newPhase) {
        try {

            logger.info("Modifying Phase{} temperature", phase);

            if (this.conf.getInventoryDataManager().getControlPanels().containsKey(panel_id)){

                ArrayList<String> phases = this.conf.getInventoryDataManager().getControlPanels().get(panel_id).getRecipe().getPhases();

                for(int i = 0; i < phases.size(); i++){
                    if(phases.get(i).equals(phase)){
                        phases.set(i, newPhase);
                    }
                }

                this.conf.getInventoryDataManager().updatePhase(phases, panel_id);

                return Response.noContent().build();

            }else{
                return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.NOT_FOUND.getStatusCode(),"Panel Not Found !")).build();
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(new ErrorMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),"Internal Server Error !")).build();
        }
    }
}