package xcon.abstr;

import java.util.Set;


public abstract class Storage {

    private int capacity;
        
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public abstract void store(String key, Object value);
    
    public abstract Object read(String key);
    
    public abstract Set<String> getKeys();
    
    public abstract String dumpContents();
}
