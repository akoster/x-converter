package xcon.stackoverflow.fieldsort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.Assert;

	import org.junit.Before;
	import org.junit.Test;
	
	public class FieldSortTest {
	
		private static final String SMALL = "tiny";
		private static final String LARGE = "Large";
		private static final String MEDIUM = "medium";
	
		private Comparator<Sized> instance;
	
		@Before
		public void setup() {
			instance = new SizedComparator();
		}
	
		@Test
		public void testHappy() {
			List<Product> products = new ArrayList<Product>();
			products.add(new Product(MEDIUM));
			products.add(new Product(LARGE));
			products.add(new Product(SMALL));
	
			Collections.sort(products, instance);
	
			Assert.assertSame(SMALL, products.get(0).getSize());
			Assert.assertSame(MEDIUM, products.get(1).getSize());
			Assert.assertSame(LARGE, products.get(2).getSize());
		}
	}
