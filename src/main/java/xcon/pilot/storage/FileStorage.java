package xcon.pilot.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the Storage class which is based on object serialization.
 * 
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
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}

	@Override
	public void store(String key, Object value) {

		boolean keyExists = getKeys().contains(key);

		if (getKeys().size() < getCapacity() || keyExists) {

			File file = new File(storageDir, key + EXTENSION);
			FileOutputStream fos = null;
			ObjectOutputStream out = null;
			try {
				fos = new FileOutputStream(file);
				out = new ObjectOutputStream(fos);
				out.writeObject(value);
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.err.println("FileStorage: Could not store " + key + "="
					+ value + ", capacity reached");
		}
	}

	@Override
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
        System.out.println("in file storage dump"); 
		StringBuffer buffer = new StringBuffer();
		for (String key : getKeys()) {

			Object value = read(key);
			buffer.append(key).append("=").append(value).append(" ");
		}
		return buffer.toString();
	}

}
