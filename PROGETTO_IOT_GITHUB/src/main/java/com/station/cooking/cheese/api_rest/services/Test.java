package com.station.cooking.cheese.api_rest.services;

import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManager;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManagerTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;

public class Test {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);


    public static void main(String[] args) throws IInventoryDataException, IOException, ClassNotFoundException {

        HashMap<String, String> map = new HashMap<>();

        FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
        ObjectInputStream oi = new ObjectInputStream(fi);

        // Read objects
        map = (HashMap<String, String>) oi.readObject();

        System.out.println(map.toString());

        oi.close();
        fi.close();
/*

        logger.info("{}", InventoryDataManagerTest.getInstance().getControlPanels());
        logger.info("{}", InventoryDataManagerTest.panelsList);
*/



    }
}
