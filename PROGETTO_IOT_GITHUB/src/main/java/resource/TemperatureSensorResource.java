package resource;

import java.util.Random;
import model.RecipeDescriptor;
import java.util.UUID;

/**
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project mqtt-demo-smartobject
 * @created 12/10/2020 - 19:28
 */
public class TemperatureSensorResource extends SmartObjectResource<Double> {

    private RecipeDescriptor recipe;

    private double startTemperature;

    public static final String RESOURCE_UNIT = "C";

    public static final String RESOURCE_TYPE = "iot:sensor:temperature";

    public TemperatureSensorResource(RecipeDescriptor recipe){
        super(UUID.randomUUID().toString(), TemperatureSensorResource.RESOURCE_UNIT, TemperatureSensorResource.RESOURCE_TYPE);
        this.recipe = recipe;
        this.startTemperature = this.recipe.getTemperatures().get(0)-5.0;
    }

    @Override
    public void refreshValue() {

    }

}
