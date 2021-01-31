package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * @author: Daniele Barbieri Powered by Marco Picone
 * @date: 30/01/2021
 * @project: Progetto_Dan_IoT
 */

public class ProvaJson {

    public static void main(String[] args) {

        ObjectMapper om = new ObjectMapper();

        try {

            RecipeDescriptor rd = om.readValue(new File("src/main/java/data/recipe.json"), RecipeDescriptor.class);

            System.out.println(rd.toString());
            System.out.println(rd.getTemperatures());
            System.out.println(rd.getTemperatures().get(2));
            //prova pull
            System.out.println(rd.getTemperatures().get(1));
            System.out.println(rd.getTemperatures().get(3));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
