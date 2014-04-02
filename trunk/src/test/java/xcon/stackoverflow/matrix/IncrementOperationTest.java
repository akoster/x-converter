package xcon.stackoverflow.matrix;

import junit.framework.Assert;

import org.junit.Test;

public class IncrementOperationTest {

	@Test
	public void test() {

		Matrix matrix = new Matrix(1, 2);
		matrix.setRow(0, 20, 30);
		
		new IncrementOperation(matrix.getRow(0), 12).execute();

		Assert.assertEquals(32, matrix.getRow(0)[0].getValue());
		Assert.assertEquals(42, matrix.getRow(0)[1].getValue());
	}
}
