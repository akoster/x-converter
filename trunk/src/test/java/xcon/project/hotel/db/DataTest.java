package xcon.project.hotel.db;

import java.io.File;
import org.junit.Before;
import org.junit.Test;

public class DataTest {

	private Data instance;

	@Before
	public void setUp() throws Exception {
		File hotelDBFile = new FileStub("testhotel.db");
		// instance = new Data(hotelDBFile);
	}

	@Test
	public void test() {
		String[] criteria = new String[] { "", "" };
		// long[] roomIds = instance.findByCriteria(criteria);
		// System.out.println(roomIds);
	}

}
