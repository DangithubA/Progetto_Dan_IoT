package com.station.cooking.cheese.api_rest.services;

import com.station.cooking.cheese.api_rest.inventory.IInventoryData;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ApiAppConfig extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    private IInventoryData inventoryDataManager = null;

    public IInventoryData getInventoryCollectorPack(){
        if(this.inventoryDataManager == null)
            this.inventoryDataManager = new InventoryDataManager();
        return this.inventoryDataManager;
    }
}

