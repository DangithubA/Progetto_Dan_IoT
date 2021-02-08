package com.station.cooking.cheese.api_rest.services;


import com.station.cooking.cheese.api_rest.resource.RecipeResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * @author: Daniele Barbieri
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */


public class ApiAppService extends Application<ApiAppConfig> {

    private static final Logger logger = LoggerFactory.getLogger(ApiAppService.class);

    public static void main(String[] args) throws Exception {

        new ApiAppService().run(new String[]{"server", args.length > 0 ? args[0] : "configuration.yml"});

    }


    public void run(ApiAppConfig appConfig, Environment environment) {

        environment.jersey().register(new RecipeResource(appConfig));

        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

    @Override
    public void initialize(Bootstrap<ApiAppConfig> bootstrap) {


        bootstrap.addBundle(new SwaggerBundle<ApiAppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApiAppConfig configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }
}