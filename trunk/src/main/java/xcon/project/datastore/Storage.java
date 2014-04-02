package xcon.project.datastore;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.log4j.Logger;

public abstract class Storage {

    private static final Logger LOG = Logger.getLogger(Storage.class);

    private static final int DEFAULT_CAPACITY = 10;
    private int capacity = DEFAULT_CAPACITY;

    private static Map<String, Storage> implementationMap;
    private static Storage implementation;

    static {
        implementationMap = new HashMap<String, Storage>();
        ResourceBundle storages = ResourceBundle.getBundle("storages");
        Enumeration<String> storageKeys = storages.getKeys();
        while (storageKeys.hasMoreElements()) {

            String key = storageKeys.nextElement();
            Storage storage;
            try {
                String storageClass = storages.getString(key);
                storage = (Storage) Class.forName(storageClass).newInstance();
                implementationMap.put(key, storage);
            }
            catch (Exception e) {
                LOG.error("Could not instantiate", e);
            }
        }
        if (implementationMap.keySet().size() > 0) {
            setImplementation(implementationMap.keySet().iterator().next());
        }
        else {
            LOG.error("No Storage implementations provided in resource bundle");
        }
    }

    public static void setImplementation(String key) {

        Storage newImpl = implementationMap.get(key);
        if (newImpl != null) {

            Storage.implementation = newImpl;
            System.out.println("Using Storage implementation: "
                + Storage.implementation.getClass().getName());
        }
        else {
            System.out.println("No Storage implementation known with the name: "
                + key);
        }
    }

    public static Storage getImplementation() {
        return Storage.implementation;
    }

    public static Set<String> getImplKeys() {
        return implementationMap.keySet();
    }

    public void setCapacity(int capacity) {
        this.capacity = Math.max(getDataKeys().size(), capacity);
    }

    public int getCapacity() {
        return Math.max(getDataKeys().size(), capacity);
    }

    public abstract boolean store(String key, Object value);

    public abstract Object read(String key);

    public abstract Set<String> getDataKeys();

    public abstract String dumpContents();

    public abstract void delete(String key);

    public void clearValues() {
        for (String key : getDataKeys()) {
            store(key, "");
        }
    }

    public void clearAll() {

        for (String key : getDataKeys()) {
            delete(key);
        }
    }
}
