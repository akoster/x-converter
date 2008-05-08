package xcon.pilot.storage.impl;

import java.util.HashSet;
import java.util.Set;
import xcon.pilot.storage.Storage;

public class NullStorage extends Storage {

    @Override
    public void delete(String key) {
        System.out.println("NullStorage delete");
    }

    @Override
    public String dumpContents() {
        System.out.println("NullStorage dumpContents");
        return null;
    }

    @Override
    public Set<String> getDataKeys() {
        System.out.println("NullStorage getKeys");
        return new HashSet<String>();
    }

    @Override
    public Object read(String key) {
        System.out.println("NullStorage read");
        return null;
    }

    @Override
    public boolean store(String key, Object value) {
        System.out.println("NullStorage store");
        return true;
    }

}
