package xcon.pilot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCleaner {

	@SuppressWarnings("serial")
	public static void main(String[] args) {

		final List<Integer> oldList = Arrays.asList(new Integer[] { 23, 4, 5,
				657 });
		System.out.println(oldList);

		List<Integer> newList = new ArrayList<Integer>() {
			{
				for (Integer element : oldList) {
					if (element > 5) {
						this.add(element);
					}
				}
			}

		};
		System.out.println(newList);
	}
}
