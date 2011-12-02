package xcon.pilot;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class FixedStack extends LinkedHashMap<Long, String> {

	private static final long serialVersionUID = 1L;
	private final int capacity;

	public FixedStack(int capacity) {
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(final Map.Entry<Long, String> eldest) {
		return super.size() > capacity;
	}

	public static void main(String[] args) {

		FixedStack stack = new FixedStack(10);

		long added = 0;
		for (Locale locale : Locale.getAvailableLocales()) {
			if (locale.getDisplayCountry().length() > 0) {
				stack.put(added, locale.getDisplayCountry());
				System.out.println(locale.getDisplayCountry());
				added++;
			}
		}
		System.out.println(String.format(">>>>>>>>> %s added", added));
		Iterator<Entry<Long, String>> iterator = stack.entrySet().iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().getValue());
		}
	}
}
