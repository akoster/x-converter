package xcon.pilot.fieldsort;

	public class Product implements Sized {
	
		private final String size;
	
		public Product(String size) {
			this.size = size;
		}
	
		public String getSize() {
			return size;
		}
	
		@Override
		public String toString() {
			return size;
		}
	}
