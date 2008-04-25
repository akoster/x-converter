package xcon.pilot.storage;

import java.util.Set;
//import com.google.inject.ImplementedBy;

//@ImplementedBy(FileStorage.class)
public abstract class Storage {

    private static final int DEFAULT_CAPACITY = 10;
    private int capacity = DEFAULT_CAPACITY;

    public void setCapacity(int capacity) {
        this.capacity = Math.max(getKeys().size(), capacity);
    }

    public int getCapacity() {
        return Math.max(getKeys().size(), capacity);
    }

    public abstract void store(String key, Object value);

    public abstract Object read(String key);

    public abstract Set<String> getKeys();

    public abstract String dumpContents();
}
