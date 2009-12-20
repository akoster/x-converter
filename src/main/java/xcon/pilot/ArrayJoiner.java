package xcon.pilot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayJoiner {

	public static void main(String args[]) throws IOException {

		String[] array1 = new String[] {"1", "2", "3"};
		String[] array2 = new String[] {"a", "b", "c"};
		
		
		System.out.println(Arrays.toString(join(array1, array2)));
		
	}

	private static String[] join(String[] array1, String[] array2) {
		
		List<String> list = new ArrayList<String>(Arrays.asList(array1));
		list.addAll(Arrays.asList(array2));
		return list.toArray(new String[0]);
	}
	
}
