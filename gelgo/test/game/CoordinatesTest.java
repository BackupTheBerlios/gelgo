package test.game;

import junit.framework.*;
import game.*;

public class CoordinatesTest extends TestCase
{
	private Coordinates coor32x16y;
	private Coordinates coor19x47y;
	
	protected void setUp()
	{
		coor32x16y = new Coordinates(32, 16);
		coor19x47y = new Coordinates(19, 47);
	}
	
	public void testEquals()
	{
		Assert.assertTrue(!coor32x16y.equals(null));
		Assert.assertEquals(coor32x16y, coor32x16y);
		Assert.assertEquals(coor32x16y, new Coordinates(32, 16));
		Assert.assertTrue(!coor32x16y.equals(coor19x47y));
	}

	public static Test suite()
	{
		return new TestSuite(CoordinatesTest.class);
	}

	public static void main(String args[])
	{
		junit.textui.TestRunner.run(suite());
	}
}
