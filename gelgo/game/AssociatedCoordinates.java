package game;

import java.util.*;
import java.io.*;

// status: compileList dumps core! 

/** Creates a collection of associated locations.
 * 	<p>"Associated" means:<br>
 * 		1. connected horizontally or vertically<br>
 * 		2. identical to each other
 * 	<p>This class makes it easy to work with large sets of
 * 	coordinates, and to know what states they are in (ie. liberties, 
 * 	who's territory, etc).
 */
public class AssociatedCoordinates implements Serializable
{
	private ArrayList coordinates;
	private boolean connectsToWhite = false;
	private boolean connectsToBlack = false;
	private boolean connectsToSpace = false;

	/** constructor: builds collections. */
	public AssociatedCoordinates(Board board, Coordinates coor)
	{
		coordinates = new ArrayList();
		compileList(board, coor);	
	}

	/** overridden equals. */
	public boolean equals(AssociatedCoordinates ac)
	{
		if (coordinates.size() != ac.size())
			return false;
		
		for (int i = 0; i < coordinates.size(); i++)
			if (!coordinates.contains(ac.get(i))) // depends on coordinates only appearing once in a list
				return false;
		
		return true;
	}

	/** returns true if {@link Coordinates} exists in collection. */
	public boolean contains(Coordinates coor)
	{
		if (coordinates.contains(coor))
			return true;
		else
			return false;
	}

	public void add(Coordinates coor)
	{
		coordinates.add(coor);
	}

	/** returns the index of a {@link Coordinates} object. */
	public int indexOf(Coordinates coor)
	{
		return coordinates.indexOf(coor);
	}

	/** returns {@link Coordinates} for specified index */
	public Coordinates get(int index)
	{
		return (Coordinates)coordinates.get(index);
	}

	/** returns size of collection. */
	public int size()
	{
		return coordinates.size();
	}

	/** returns true if any of the locations border a white stone; false otherwise. */
	public boolean touchesWhite()
	{
		return connectsToWhite;
	}

	/** returns true if any of the locations border a black stone; false otherwise. */
	public boolean touchesBlack()
	{
		return connectsToBlack;
	}

	/** returns true if any of the locations border an empty space; false otherwise. */
	public boolean touchesSpace()
	{
		return connectsToSpace;
	}

	/** does all the setup work for the class.  recursively locates all associated stones. 
	 * also reports what types of stones and/or spaces are bordering locations.
	 */
	private void compileList(Board grid, Coordinates coor)
	{
		Stone myStone;
		boolean similar;
		Coordinates lookingHere;
		int myColorCode, theirColorCode;

System.out.println("Entered Method.");
		// get whatever is at coor
		try 
		{ 
			myStone = grid.lookAt(coor);
			myColorCode = stoneColorCode(myStone);
System.out.println("Color of stone is " + myColorCode);
		} 
		catch (OffBoardException e) 
		{ 
			System.err.println("AssociatedCoordinates.compileList(): " + e); 
			return;
		}
		
		// report anything we are next to recurse any direction that is similar
		try
		{
			for (int i = 0; i < 4; i++)
			{
				similar = false;
				
				if (grid.areOnBoard(coor.direction(i)))
				{
System.out.println("looking at " + coor.direction(i));
					theirColorCode = stoneColorCode(grid.lookAt(coor.direction(i)));
System.out.println("has color " + theirColorCode);

					if (myColorCode - theirColorCode == 0 && !coordinates.contains(coor.direction(i)))
					{
System.out.println("Colors are the same and coordinates where not found in list.");
						similar = true;
					}
					else
					{
						switch (theirColorCode)
						{
							case 0:
								connectsToSpace = true;
								break;
							case 1:
								connectsToBlack = true;
								break;
							case 2:
								connectsToWhite = true;
								break;
						}
					}
		
					if (similar == true)
					{
System.out.println("About to recurse.");
						compileList(grid, coor.direction(i));
					}
				}
			}
		} catch (OffBoardException e) { System.err.println("AssociatedCoordinates.compileList(): " + e); }
		
		// add location to list
		coordinates.add(coor);
		return;
	}

	protected int stoneColorCode(Stone s)
	{
		if (s == null)
			return 0;
		else if (s.getColor() == Color.BLACK)
			return 1;
		else if (s.getColor() == Color.WHITE)
			return 2;
		else
			return -1;
	}
}
