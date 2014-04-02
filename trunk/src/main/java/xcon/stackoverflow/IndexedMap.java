package xcon.stackoverflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Map implementation which keeps track of the inserted keys in a List. This
 * gives the client the opportunity to get a key from the map by its position.
 *
 * http://stackoverflow.com/questions/1509391/how-to-get-the-one-entry-from-hashmap-without-iterating/1510023#1510023
 */
public class IndexedMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;
	private List<K> keyIndex;

	public IndexedMap() {
		keyIndex = new ArrayList<K>();
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
	public K get(int index) {
		return keyIndex.get(index);
	}

	@Override
	public V put(K key, V value) {

		addKeyToIndex(key);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> source) {
		for (K key : source.keySet()) {
			addKeyToIndex(key);
		}
		super.putAll(source);
	}

	@Override
	public V remove(Object key) {
		keyIndex.remove(key);
		return super.remove(key);
	}

	private void addKeyToIndex(K key) {

		if (!keyIndex.contains(key)) {
			keyIndex.add(key);
		}
	}
}
