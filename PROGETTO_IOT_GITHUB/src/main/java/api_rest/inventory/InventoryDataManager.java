package api_rest.inventory;

import api_rest.exceptions.IInventoryDataConflict;
import api_rest.exceptions.IInventoryDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SenMLPack;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class InventoryDataManager implements IInventoryData{

    final protected Logger logger = LoggerFactory.getLogger(IInventoryData.class);


    @Override
    public SenMLPack createNewPack(SenMLPack pack) throws IInventoryDataException, IInventoryDataConflict {
        try {
            String record = new ObjectMapper().writeValueAsString(pack);

            FileWriter fileWriter = new FileWriter("recordSensorsFile", true);
            fileWriter.write(record);
            fileWriter.append("\n");
            fileWriter.flush();
            fileWriter.close();
            logger.info(record);

            return pack;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
