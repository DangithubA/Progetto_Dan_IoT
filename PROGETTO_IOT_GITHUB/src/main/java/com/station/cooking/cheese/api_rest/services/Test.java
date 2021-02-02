package com.station.cooking.cheese.api_rest.services;

import com.station.cooking.cheese.api_rest.exceptions.IInventoryDataException;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManager;
import com.station.cooking.cheese.api_rest.inventory.InventoryDataManagerTest;

public class Test {


    public static void main(String[] args) throws IInventoryDataException {

        InventoryDataManagerTest inventoryDataManagerTest = InventoryDataManagerTest.getInstance();

        System.out.println(String.format("%s", inventoryDataManagerTest.panelsList.keySet()));



    }
}
