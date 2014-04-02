package xcon.stackoverflow.matrix;

import junit.framework.Assert;

import org.junit.Test;

public class ReverseOperationTest {

	@Test
	public void test() {

		Matrix matrix = new Matrix(1, 3);
		matrix.setRow(0, 20, 30, 40);
		
		new ReverseOperation(matrix.getRow(0)).execute();

		Assert.assertEquals(40, matrix.getRow(0)[0].getValue());
		Assert.assertEquals(30, matrix.getRow(0)[1].getValue());
		Assert.assertEquals(20, matrix.getRow(0)[2].getValue());
	}
}
