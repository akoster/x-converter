package xcon.hotel.db.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils {

    private static Map<Long, String> customerIds = new HashMap<Long, String>();;
    
    public static  String generateCustumerId(long recNo) {
        System.out.println("generating customer Id");
        Random random = new Random();
        // generate a number between of 8 digits

        int number = random.nextInt(89999999) + 10000000;
        String customerId = String.valueOf(number);
        // ik stop dit in een map omdat ik door de random alleen maar unieke
        // waardes wil hebben. Dit is nog niet geimplemteerd

        // make sure that the customerId uniek is for each customer
        if (customerIds.containsValue(customerId)) {
            generateCustumerId(recNo);
        }
        customerIds.put(recNo, customerId);
        return customerId;
    }
}
