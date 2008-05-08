package xcon.pilot.storage.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;
import xcon.pilot.storage.Storage;

/**
 * Implementation of the Storage class which is based on object serialization.
 * @author Adriaan Koster
 */
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
        if (file.exists()) {
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
        }
        return value;
    }

    @Override
    public boolean store(String key, Object value) {

        boolean enoughCapacity =
            getDataKeys().size() < getCapacity() || getDataKeys().contains(key);

        if (enoughCapacity) {

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
        return enoughCapacity;
    }

    @Override
    public Set<String> getDataKeys() {

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
        for (String key : getDataKeys()) {

            Object value = read(key);
            buffer.append(key).append("=").append(value).append(" ");
        }
        return buffer.toString();
    }

    @Override
    public void delete(String key) {

        File file = new File(storageDir, key + EXTENSION);
        if (!file.canWrite()) {
            System.out.println("Delete: write protected: " + file);
        }
        else if (!file.exists()) {
            System.out.println("Delete: no such file or directory: " + file);
        }
        else {
            boolean success = file.delete();
            if (!success) {
                System.out.println("Deletion failed");
            }
        }
    }
}
