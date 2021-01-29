package api_rest.exceptions;

public class IInventoryDataConflict extends Exception{
    public IInventoryDataConflict(String errorMessage){
        super(errorMessage);
    }
}
