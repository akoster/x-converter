package xcon.hotel.client;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CustomerIdGenerator {

    private static Set<String> customerIds = new HashSet<String>();
    private static Random random = new Random();

    // XXX: replace with java.util.UUID
    public static String generateCustumerId() {

        String customerId = null;
        while (customerId == null || customerIds.contains(customerId)) {
            customerId = String.valueOf(random.nextInt(99999999));
        }
        customerIds.add(customerId);
        return customerId;
    }
}
