package xcon.stackoverflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * http://stackoverflow.com/questions/3760120/java-oneliner-for-list-cleanup/3760662#3760662
 */
public class ListCleaner {

	public static void main(String[] args) {

		final List<Integer> oldList = Arrays.asList(new Integer[] { 23, 4, 5,
				657 });
		System.out.println(oldList);

		List<Integer> newList = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;

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
