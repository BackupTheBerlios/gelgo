package game;

import java.util.*;
import java.io.*;

// status: in testing. 

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
	private int hash;

	/** constructor: builds collections. */
	public AssociatedCoordinates(Board board, Coordinates coor)
	{
		coordinates = new ArrayList();
		compileList(board, coor);

                int hash = 0;
                for (int i = 0; i < coordinates.size(); i++)
			hash += coordinates.get(i).hashCode();
	}

	public boolean equals(AssociatedCoordinates ac)
	{
		if (coordinates.size() != ac.size())
			return false;
		
		for (int i = 0; i < coordinates.size(); i++)
			if (!coordinates.contains(ac.get(i))) 
				return false;
		
		return true;
	}

	public int hashCode()
	{
		return hash;
	}

	/** returns true if {@link Coordinates} exists in collection. */
	public boolean contains(Coordinates coor)
	{
		if (coordinates.contains(coor))
			return true;
		else
			return false;
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

		// get whatever is at coor
		try 
		{ 
			myStone = grid.lookAt(coor);
			coordinates.add(coor); // add location to list
			myColorCode = stoneColorCode(myStone);
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
					theirColorCode = stoneColorCode(grid.lookAt(coor.direction(i)));

					if (myColorCode - theirColorCode == 0 && !coordinates.contains(coor.direction(i)))
					{
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
						compileList(grid, coor.direction(i));
					}
				}
			}
		} catch (OffBoardException e) { System.err.println("AssociatedCoordinates.compileList(): " + e); }
		
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
