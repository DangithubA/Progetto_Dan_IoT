package api_rest.services;


import api_rest.inventory.IInventoryData;
import api_rest.inventory.InventoryDataManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class ApiAppConfig extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    private IInventoryData inventoryData = null;

    public IInventoryData getInventoryData(){
        if(this.inventoryData == null)
            this.inventoryData = new InventoryDataManager();
        return this.inventoryData;
    }
}
