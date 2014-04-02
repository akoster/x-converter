package xcon.project.datastore.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import xcon.project.datastore.Storage;

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
    public boolean store(String key, Object value) {

        boolean enoughCapacity =
            getDataKeys().size() < getCapacity() || getDataKeys().contains(key);
        if (enoughCapacity) {
            data.put(key, value);
        }
        else {
            System.err.println("HashMapStorage: Could not store " + key + "="
                + value + ", capacity reached");
        }
        return enoughCapacity;
    }

    public Set<String> getDataKeys() {
        return data.keySet();
    }

    @Override
    public String dumpContents() {

        StringBuffer buffer = new StringBuffer();
        for (String key : getDataKeys()) {

            Object value = read(key);
            buffer.append(key).append("=").append(value).append(" ");
        }
        return buffer.toString();
    }

    public void delete(String key) {

        data.remove(key);
    }
}
