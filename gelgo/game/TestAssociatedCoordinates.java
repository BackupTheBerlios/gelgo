package game;

import java.util.*;

public class TestAssociatedCoordinates
{
	public static void main(String args[])
	{
		Board testBoard = Board.fromString(".#.\n.#.\n...\n");
		AssociatedCoordinates group = new AssociatedCoordinates(testBoard, new Coordinates(1, 1));

		if (group.contains(new Coordinates(1, 1)))
			System.out.println("This worked.");
		else
			System.out.println("Didn't work.");
	}
}
