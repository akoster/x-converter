package xcon.pilot;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapMerger {

	private static final int SUBMAP_COUNT = 100;
	private static final int SUBMAP_SIZE = 200;

	public static void main(String[] args) {

		SortedMap<String, SortedMap<String, String>> rootMap = createRootMap();
		long start = System.currentTimeMillis();
		mergeSamples(rootMap);
		long end = System.currentTimeMillis();

		System.out.println("Duration mergeSamples=" + (end - start));

		start = System.currentTimeMillis();
		mergeSamples2(rootMap);
		end = System.currentTimeMillis();

		System.out.println("Duration mergeSamples2=" + (end - start));

	}

	private static SortedMap<String, SortedMap<String, String>> createRootMap() {
		SortedMap<String, SortedMap<String, String>> data = new TreeMap<String, SortedMap<String, String>>();
		for (int n = 0; n < SUBMAP_COUNT; n++) {
			data.put("rootkey-" + n, createSubMap());
		}
		return data;
	}

	private static SortedMap<String, String> createSubMap() {
		SortedMap<String, String> subMap = new TreeMap<String, String>();
		for (int n = 0; n < SUBMAP_SIZE; n++) {
			subMap.put("subkey-" + n, "value-" + n);
		}
		return subMap;
	}

	private static SortedMap<String, String> mergeSamples(
			SortedMap<String, SortedMap<String, String>> map) {

		SortedMap<String, String> mergedMap = new TreeMap<String, String>();
		Iterator<SortedMap<String, String>> sampleIt = map.values().iterator();
		while (sampleIt.hasNext()) {
			SortedMap<String, String> currMap = sampleIt.next();
			mergedMap.putAll(currMap);
		}
		return mergedMap;
	}

	private static SortedMap<String, String> mergeSamples2(
			SortedMap<String, SortedMap<String, String>> map) {

		SortedMap<String, String> mergedMap = new TreeMap<String, String>();
		for (String rootKey : map.keySet()) {
			
			SortedMap<String, String> currMap = map.get(rootKey);
			mergedMap.putAll(currMap);
		}
		return mergedMap;
	}
}
