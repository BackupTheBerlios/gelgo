package test.game;

import game.*;
import java.util.*;
import junit.framework.*;

public class AssociatedCoordinatesTest extends TestCase
{
	Board myBoard;
	ArrayList groupAt12x7;
	ArrayList groupAt0x11;
	AssociatedCoordinates assCo12x7;
	AssociatedCoordinates assCo0x11;
	
	protected void setUp()
	{
		myBoard = Board.fromString(		"*#*#*###*.#.##*##.*\n" + // 0
                                                        "#**#.*##.#.#..###.*\n" + // 1
                                                        "#*.***####..*...###\n" + // 2
                                                        "#..#*...*..#..##.*#\n" + // 3
                                                        ".*.#.***###*#*#.#*#\n" + // 4
                                                        "#.#.**#**#.#.****.#\n" + // 5
                                                        "#**.##*#*##....*..#\n" + // 6
                                                        "*#..#*##.*....*#**.\n" + // 7
                                                        ".*#*#.*#*.#...*#...\n" + // 8
                                                        "****.*##.*#*.....#.\n" + // 9
                                                        "*....**#***..**.*#.\n" + // 10
                                                        "##*.**#.#*.*****.*#\n" + // 11
                                                        "####.***.*#*####.#*\n" + // 12
                                                        "#.*####*.*....*.#*.\n" + // 13
                                                        "*#**###**.*#####**#\n" + // 14
                                                        "###.*#*#....**#..*#\n" + // 15
                                                        "..##.######*.#.#**.\n" + // 16
                                                        "*...*.#.#.#.****..*\n" + // 17
                                                        ".*.*#**#***.#.....#\n"); // 18
						//	 0123456789012345678
		
		groupAt12x7 = new ArrayList();
		groupAt12x7.add(new Coordinates(12, 5));
		groupAt12x7.add(new Coordinates(11, 6));
		groupAt12x7.add(new Coordinates(12, 6));
		groupAt12x7.add(new Coordinates(13, 6));
		groupAt12x7.add(new Coordinates(14, 6));
		groupAt12x7.add(new Coordinates(10, 7));
		groupAt12x7.add(new Coordinates(11, 7));
		groupAt12x7.add(new Coordinates(12, 7));
		groupAt12x7.add(new Coordinates(13, 7));
		groupAt12x7.add(new Coordinates(18, 7));
		groupAt12x7.add(new Coordinates(11, 8));
		groupAt12x7.add(new Coordinates(12, 8));
		groupAt12x7.add(new Coordinates(13, 8));
		groupAt12x7.add(new Coordinates(16, 8));
		groupAt12x7.add(new Coordinates(17, 8));
		groupAt12x7.add(new Coordinates(18, 8));
		groupAt12x7.add(new Coordinates(12, 9));
		groupAt12x7.add(new Coordinates(13, 9));
		groupAt12x7.add(new Coordinates(14, 9));
		groupAt12x7.add(new Coordinates(15, 9));
		groupAt12x7.add(new Coordinates(16, 9));
		groupAt12x7.add(new Coordinates(18, 9));
		groupAt12x7.add(new Coordinates(11, 10));
		groupAt12x7.add(new Coordinates(12, 10));
		groupAt12x7.add(new Coordinates(15, 10));
		groupAt12x7.add(new Coordinates(18, 10));

		groupAt0x11 = new ArrayList();
		groupAt0x11.add(new Coordinates(0, 11));
		groupAt0x11.add(new Coordinates(0, 12));
		groupAt0x11.add(new Coordinates(0, 13));
		groupAt0x11.add(new Coordinates(1, 11));
		groupAt0x11.add(new Coordinates(1, 12));
		groupAt0x11.add(new Coordinates(2, 12));
		groupAt0x11.add(new Coordinates(3, 12));
		groupAt0x11.add(new Coordinates(3, 13));
		groupAt0x11.add(new Coordinates(4, 13));
		groupAt0x11.add(new Coordinates(4, 14));
		groupAt0x11.add(new Coordinates(5, 13));
		groupAt0x11.add(new Coordinates(5, 14));
		groupAt0x11.add(new Coordinates(5, 15));
		groupAt0x11.add(new Coordinates(5, 16));
		groupAt0x11.add(new Coordinates(6, 13));
		groupAt0x11.add(new Coordinates(6, 14));
		groupAt0x11.add(new Coordinates(6, 16));
		groupAt0x11.add(new Coordinates(6, 17));
		groupAt0x11.add(new Coordinates(7, 15));
		groupAt0x11.add(new Coordinates(7, 16));
		groupAt0x11.add(new Coordinates(8, 16));
		groupAt0x11.add(new Coordinates(8, 17));
		groupAt0x11.add(new Coordinates(9, 16));
		groupAt0x11.add(new Coordinates(10, 16));
		groupAt0x11.add(new Coordinates(10, 17));
	}

	public void testAssociatedCoordinatesConstructor()
	{
		Coordinates coor;

		assCo12x7 = new AssociatedCoordinates(myBoard, new Coordinates(12, 7));
		assCo0x11 = new AssociatedCoordinates(myBoard, new Coordinates(0, 11));
	
		for (int i = 0; i < myBoard.getSize(); i++)
		{
			for (int j = 0; j < myBoard.getSize(); j++)
			{
				coor = new Coordinates(j, i);
			
				if (groupAt12x7.contains(coor))
					Assert.assertTrue(assCo12x7.contains(coor));
				else
					Assert.assertFalse(assCo12x7.contains(coor));

				if (groupAt0x11.contains(coor))
					Assert.assertTrue(assCo0x11.contains(coor));
				else
					Assert.assertFalse(assCo0x11.contains(coor));
			}
		} 
	}
	
	public static Test suite()
	{
		return new TestSuite(AssociatedCoordinatesTest.class);
	}

	public static void main(String args[])
	{
		junit.textui.TestRunner.run(suite());
	}
}
