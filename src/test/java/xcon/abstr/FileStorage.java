package xcon.abstr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileStorage extends Storage {

    private static final String STORAGE_DIR = "storage/";
    private static final String EXTENSION = ".ser";
    private File storageDir;

    public FileStorage() {
        storageDir = new File(STORAGE_DIR);
        storageDir.mkdirs();

    }

    @Override
    public Object read(String key) {

        Object value = null;

        File file = new File(storageDir, key + EXTENSION);
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            value = (Object) in.readObject();
            in.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return value;
    }

    @Override
    public void store(String key, Object value) {

        if (getKeys().size() < getCapacity()) {

            File file = new File(storageDir, key + EXTENSION);
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream(file);
                out = new ObjectOutputStream(fos);
                out.writeObject(value);
                out.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else {
            System.err.println("FileStorage: Could not store " + key + "="
                + value + ", capacity reached");
        }
    }

    public Set<String> getKeys() {

        String[] names = storageDir.list(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.endsWith(EXTENSION);
            }

        });
        Set<String> keys = new HashSet<String>();
        for (String name : names) {

            String key = name.replace(EXTENSION, "");
            keys.add(key);
        }
        return keys;
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

}
