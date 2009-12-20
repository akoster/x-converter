package xcon.pilot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Map implementation which keeps track of the inserted keys in a List. This
 * gives the client the opportunity to get a key from the map by its position.
 * 
 * @author Adriaan
 */
@SuppressWarnings("unchecked")
public class IndexedMap extends HashMap {

	private static final long serialVersionUID = 1L;
	private List<Object> keyIndex;

	public IndexedMap() {
		keyIndex = new ArrayList<Object>();
	}

	/**
	 * Returns the key at the specified position in this Map's keyIndex.
	 * 
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range (index < 0 || index >= size())
	 */
	public Object get(int index) {
		return keyIndex.get(index);
	}

	@Override
	public Object put(Object key, Object value) {

		addKeyToIndex(key);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map source) {

		for (Object key : source.keySet()) {
			addKeyToIndex(key);
		}
		super.putAll(source);
	}

	private void addKeyToIndex(Object key) {

		if (!keyIndex.contains(key)) {
			keyIndex.add(key);
		}
	}

	@Override
	public Object remove(Object key) {

		keyIndex.remove(key);
		return super.remove(key);
	}
}
