package xcon.hotel.client;

import java.util.List;


public class DataBaseInformation {
    
    static long  magicCookie ;
    static int   numberOfFieldsInDatabase ;
    static List<String> columnNames ;
    public long getMagicCookie() {
        return magicCookie;
    }
    
    
    public static List<String> getColumnNames() {
        return columnNames;
    }

    public void setMagicCookie(long magicCookie) {
        DataBaseInformation.magicCookie = magicCookie;
    }
    
    
   
    public void setNumberOfFieldsIndatabase(int numberOfFieldsInDatabase) {
        DataBaseInformation.numberOfFieldsInDatabase = numberOfFieldsInDatabase ;
        
    }

    public void setColumnNames(List<String> columnNames) {
        DataBaseInformation.columnNames = columnNames ;
    } 
    
    
    
}
