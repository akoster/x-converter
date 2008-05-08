package xcon.pilot.storage.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import xcon.pilot.storage.Storage;

public class HashMapStorage extends Storage {

    private Map<String, Object> data;

    public HashMapStorage() {
        init();
    }

    private void init() {
        this.data = new HashMap<String, Object>();
    }

    @Override
    public Object read(String key) {
        return data.get(key);
    }

    @Override
    public void store(String key, Object value) {

        if (getKeys().size() < getCapacity()) {
            data.put(key, value);
            System.out.println("Stored: " + key + "=" + value);
        }
        else {
            System.err.println("HashMapStorage: Could not store " + key + "="
                + value + ", capacity reached");
        }       
    }

    public Set<String> getKeys() {
        return data.keySet();
    }

    @Override
    public String dumpContents() {

        StringBuffer buffer = new StringBuffer();
        for (String key : getKeys()) {

            Object value = read(key);
            buffer.append(key).append("=").append(value).append(" ");
        }
        return buffer.toString();
    }

    public void delete(String key) {

        data.remove(key);
        System.out.println("Deleted: " + key);
    }
}
