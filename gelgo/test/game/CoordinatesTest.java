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
	
	public void testCoordinatesEquals()
	{
		Assert.assertTrue(!coor32x16y.equals(null));
		Assert.assertEquals(coor32x16y, coor32x16y);
		Assert.assertEquals(coor32x16y, new Coordinates(32, 16));
		Assert.assertTrue(!coor32x16y.equals(coor19x47y));
	}

	public void testCoordinatesUp()
	{
		Assert.assertEquals(coor32x16y.up(), new Coordinates(32, 15));
	}

	public void testCoordinatesDown()
	{
		Assert.assertEquals(coor32x16y.down(), new Coordinates(32, 17));
	}

	public void testCoordinatesRight()
	{
		Assert.assertEquals(coor32x16y.right(), new Coordinates(33, 16));
	}

	public void testCoordinatesLeft()
	{
		Assert.assertEquals(coor32x16y.left(), new Coordinates(31, 16));
	}

	public void testCoordinatesDirection()
	{
		Assert.assertEquals(coor32x16y.direction(0), coor32x16y.up());
		Assert.assertEquals(coor32x16y.direction(1), coor32x16y.down());
		Assert.assertEquals(coor32x16y.direction(2), coor32x16y.left());
		Assert.assertEquals(coor32x16y.direction(3), coor32x16y.right());
	}

	public void testCoordinatesToString()
	{
		Assert.assertEquals(coor32x16y.toString(), "(32, 16)");
	}

	public void testCoordinatesHashCode()
	{
		Assert.assertEquals(coor32x16y.hashCode(), (new Coordinates(32, 16)).hashCode());
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
