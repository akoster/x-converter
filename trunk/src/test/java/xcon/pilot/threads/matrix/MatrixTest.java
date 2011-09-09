package xcon.pilot.threads.matrix;

import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

	@Test
	public void test() {

		Matrix matrix = new Matrix(3, 2);
		Assert.assertEquals(3, matrix.getRows());
		Assert.assertEquals(2, matrix.getCols());
		
		matrix.setRow(0, 1, 2);
		matrix.setRow(1, 3, 4);
		matrix.setRow(2, 5, 6);
		
		Cell[] row1 = matrix.getRow(1);
		Assert.assertEquals(4, row1[1].getValue());
		
		Cell cel21 = matrix.getCell(2, 1);
		Assert.assertEquals(6, cel21.getValue());
		
		cel21.setValue(18);
		
		Assert.assertEquals(18, matrix.getRow(2)[1].getValue());
		
		matrix.setCell(2, 1, 7);
		Assert.assertEquals(7, matrix.getRow(2)[1].getValue());
	}

}
