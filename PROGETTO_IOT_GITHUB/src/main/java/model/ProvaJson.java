package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ProvaJson {

    public static void main(String[] args) {

        ObjectMapper om = new ObjectMapper();

        try {

            RecipeDescriptor rd = om.readValue(new File("src/main/java/data/recipe.json"), RecipeDescriptor.class);

            System.out.println(rd.toString());
            System.out.println(rd.getTemperatures());
            System.out.println(rd.getTemperatures().get(2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
