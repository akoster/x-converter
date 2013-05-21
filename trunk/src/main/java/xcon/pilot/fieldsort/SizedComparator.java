	package xcon.pilot.fieldsort;
	
	import java.util.Comparator;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.ResourceBundle;
	
	public class SizedComparator implements Comparator<Sized> {
	
		// maps size aliases to canonical sizes
		private static final Map<String, String> sizes = new HashMap<String, String>();
	
		static {
			// create the lookup map from a resourcebundle
			ResourceBundle sizesBundle = ResourceBundle
					.getBundle(SizedComparator.class.getName());
			for (String canonicalSize : sizesBundle.keySet()) {
				String[] aliases = sizesBundle.getString(canonicalSize).split(",");
				for (String alias : aliases) {
					sizes.put(alias, canonicalSize);
				}
			}
		}
	
		@Override
		public int compare(Sized s1, Sized s2) {
			int result;
			String c1 = getCanonicalSize(s1);
			String c2 = getCanonicalSize(s2);
			if (c1 == null && c2 == null) {
				result = 0;
			} else if (c1 == null) {
				result = -1;
			} else if (c2 == null) {
				result = 1;
			} else {
				result = c1.compareTo(c2);
			}
			return result;
		}
	
		private String getCanonicalSize(Sized s1) {
			String result = null;
			if (s1 != null && s1.getSize() != null) {
				result = sizes.get(s1.getSize());
			}
			return result;
		}
	
	}
