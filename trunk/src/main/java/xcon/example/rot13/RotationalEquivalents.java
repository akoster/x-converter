package xcon.example.rot13;

import java.util.*;

public class RotationalEquivalents {

	private static Map<String, List<String>> rotationGroups = new HashMap<String, List<String>>();

	public static void main(String[] args) {
		findEquivalents(Arrays.asList("ab", "a", "b", "no", "op", "za", "test", "yes", "aaa", "bbb"));
	}

	private static void findEquivalents(List<String> inputs) {
		for (String input : inputs) {
			String key = calculateKey(input);
			// check for group and add
			List<String> group = rotationGroups.get(key);
			if (group == null) {
				group = new ArrayList<String>();
				rotationGroups.put(key, group);
			}
			group.add(input);
		}
		for (Map.Entry<String, List<String>> entry : rotationGroups.entrySet()) {
			String sep = "";
			for (String input : entry.getValue()) {
				String equivalentSet = String.format("%s\"%s\"", sep, input);
				System.out.printf(equivalentSet);
				sep = ",";
			}
			System.out.println();
		}
	}

	private static String calculateKey(String input) {
		StringBuilder sb = new StringBuilder();
		Character previous = null;
		for (int i = 0; i < input.length(); i++) {
			char current = input.charAt(i);
			if (previous == null) {
				previous = current;
			} else {
				int difference = current - previous;
				if (difference < 0) {
					difference += 26;
				}
				sb.append(Character.toString((char) difference));
			}
		}
		return sb.toString();
	}

}
