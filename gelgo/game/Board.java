package game;
import java.io.*;

// status: tested.  in need of revision for effeciency.

// i am thinking of basing Board off of a hashtable instead of a
// multi-dimensional array.  --steve

/**
 * Board simulates a simple, dumb grid board and {@link Stone}s.
 * The board size is specified by the user.  {@link Stone}'s can placed in
 * each space on the grid.  It implements no rules of any game.  However, it 
 * does mimic certain physical limitations of a real grid board.  For instance,
 * only a single {@link Stone} can occupy a grid space.  
 * 
 * <p>Note that Board is not a true cartesian plane.  Location (0,0) is located
 * in the upper left corner.  Value of the x axis increases to the right.  Value
 * of the y axis increases to the bottom.
 *
 * @author Anthony Benjamin, Steve Asher
 */
public class Board implements Serializable
{
	// private attributes
	private Stone gameBoard[][];
	private int bSize = 19;
	
	/**
	* Places a new {@link Stone} on the board.
	* Recieve coordinates and places a new {@link Stone} accordingly.
	* If the specified location is off of the board, then an 
	* {@link OffBoardException} will be thrown.
	*
	* @param x location on x axis.
	* @param y location on y axis.
	* @param c Color of Stone (ie. Color.WHITE, Color.BLACK)
	* 
	* @return void
	*  	
	* @throws OffBoardException If coordinates are out of range.
	*
	* @see Stone
	* @see Color
	* @see OffBoardException
	*/
	public void put(Coordinates coor, Color c) throws OffBoardException, InvalidSpaceException
	{
		Stone newpiece = null;
		
		if(lookAt(coor) == null){ //If space is empty, place
								 //piece there
			newpiece = new Stone(c);
			gameBoard[coor.getX()][coor.getY()] = newpiece;
		}else{
			throw new InvalidSpaceException("Board.put(): Invalid Space");
		}
	}

        /**
        * Removes a {@link Stone} from the board. 
        * Recieve coordinates and removes a {@link Stone} if possible.
        * If the specified location is off of the board, then an
        * {@link OffBoardException} will be thrown.
        *
        * @param x location on x axis.
        * @param y location on y axis.
        *
        * @return void
        *
        * @throws OffBoardException If coordinates are out of range.
	*
	* @see OffBoardException
        */
	public void remove(Coordinates coor) throws OffBoardException, InvalidSpaceException
	{
		if(lookAt(coor) != null){
			gameBoard[coor.getX()][coor.getY()] = null;
		}else{
			throw new InvalidSpaceException("Board.remove(): Invalid Space");
		}
	}

    /**
    * Returns a {@link Stone} from a location if possible.
    * Recieve coordinates and returns a {@link Stone} if one exists there.
    * If the specified location is off of the board, then an
    * {@link OffBoardException} will be thrown.
    *
    * @param x location on x axis.
    * @param y location on y axis.
    *
    * @return The Stone at the location.
    *
    * @throws OffBoardException If coordinates are out of range.
	*
	* @see Stone
	* @see OffBoardException
    */
	public Stone lookAt(Coordinates coor) throws OffBoardException
	{
		if (coor != null && areOnBoard(coor))
				return gameBoard[coor.getX()][coor.getY()];
		
		throw new OffBoardException("Board.lookAt(): Off of Board");
	}
	
	/**
    * Returns a String from a board array.
    * Uses lookAt().  Use this only for testing purposes.
    *
    * @return String
    *
    */
	public String toString() 
	{			
		String bBuffer = "";
		Coordinates coor;
		
		for(int i = 0; i < bSize; i++)
		{
			for(int j = 0; j < bSize; j++) 
			{	
				coor = new Coordinates(j, i);

				try
				{
					if(lookAt(coor) != null){
					bBuffer = bBuffer + lookAt(coor); //this will call Stone.toString
					}else{
					bBuffer = bBuffer + ".";
					}
				}
				catch (OffBoardException e) 
				{
					System.err.println("Board.toString(): threw OffBoardException.  Must be bad \"for\" loop.");
				}
			}
			
			bBuffer = bBuffer + "\n";
		}
		
		return bBuffer;
	}

    /**
    * Recieves a string and returns the equivilent board.
	* Requires that the string conform to the format produced
	* by toString().
	*<p>Columns are delimited by newline characters.  Also
	* there must be an equal number of rows and columns.
	* <p>Use this only for testing purposes.
	*
        * @param s {@link String} that represents board
        *
        * @return Board generated from {@link String}
        *
        * @see Board#toString
        */
	public static Board fromString(String s)
	{
		Board myBoard;
		int start_sub_index, end_sub_index, newSize;
		char curChar;
		String new_string = "";
		Stone newpiece = null;

		// determine size
		newSize = s.indexOf('\n');

		if (newSize > 0)
			myBoard = new Board(newSize);
		else
			return null;

		// remove new-line chars
		for (int i = 0; i < newSize; i++)
		{
			start_sub_index = (i * newSize) + i;
			end_sub_index = start_sub_index + newSize;

			new_string = new_string + s.substring(start_sub_index, end_sub_index);
		}
		
		// generate board from new_string
		for (int y = 0; y < newSize; y++)
		{
			for (int x = 0; x < newSize; x++)
			{
				curChar = new_string.charAt(x + (y * newSize));

				switch (curChar)
				{
					case '#':
						newpiece = new Stone(Color.BLACK);
						break;
					case '*':
						newpiece = new Stone(Color.WHITE);
						break;
					case '.':
						newpiece = null;
						break;
					default:
						return null;  // invalid character
				}

				myBoard.gameBoard[x][y] = newpiece;
			}
		}
		
		return myBoard;
	}	
 	/**
	* Returns true if co-ords are on game board, returns false
	* if out of bounds.
	* 
	* @param x co-ordinate on x axis
	* @param y co-ordinate on y axis
	* 
	* @return boolean
	* 
	*/	
	public boolean areOnBoard(Coordinates coor)
	{
		int x;
		int y;
		boolean passed = false;

		if (coor != null)
		{
			x = coor.getX();
			y = coor.getY();
			
			if (x >= 0 && x < getSize() && y >= 0 && y < getSize())
				passed = true;
		}

		return passed;
	}
	
	/**
	 * Accessor for current board size.
	 * 
	 * @return int
	 * 
	 */
	public int getSize()
	{
		return bSize;
	}
	/**
	 * Overridden clone method for the Board. Just need to clone
	 * the board itself because stones are immutable
	 * 
	 * @return Object
	 * 
	 */
	public Object clone()
	{
		Board newBoard = new Board(getSize());

		for (int i = 0; i < getSize(); i++)
			for (int j = 0; j < getSize(); j++)
				newBoard.gameBoard[j][i] = this.gameBoard[j][i];

		return newBoard;
	}

	/**
	 * Overridden method for equals. Just compare toString
	 * output from both boards, using the String's equals
	 * method.
         *
	 * @return boolean
         *
         */
	public boolean equals(Object obj)
	{
		if(obj instanceof Board && obj != null && this.toString().equals(obj.toString()))
		{
			return true;
		}
		return false;
	}

        /**
         * Overridden method for hashcode. Just use toString's
         * hashcode.
         *
         * @return int
         *
         */
	public int hashCode()
	{
		return this.toString().hashCode();
	}

	/**
	 * This method is for overlaying two boards, one on top of the other. It throws
	 * an InvalidBoardException if the boards don't match in dimensions.
         *
         * @param Board bOne, first board to use
         * @param Board bTwo, board to overlay
	 *
	 * @throws InvalidBoardException if boards do not match in dimensions
 	 *
	 * @return Board
         *
         */
	public static Board addStones(Board bOne, Board bTwo) throws InvalidBoardException // i am going to get rid of this method
	{
		Coordinates coor;
		
		if (bOne.getSize() != bTwo.getSize())
			throw new InvalidBoardException("Boards not the same size!");
		
		Board newBoard = new Board(bOne.getSize());

		try
		{
			for (int i = 0; i < bOne.getSize(); i++)
			{
				for (int j = 0; j < bOne.getSize(); j++)
				{
					coor = new Coordinates(j, i);
					
					if (bTwo.lookAt(coor) != null && bOne.lookAt(coor) == null)
						bOne.put(coor, bTwo.lookAt(coor).getColor());
				}
			}
		} catch (GoException e) { System.err.println("Board.catBoard(): " + e); }

		return newBoard;
	}

	/** Just a constructor.
	* @param size Size of board
	*/	
	public Board(int size)
	{
		bSize = size;
		gameBoard = new Stone [size][size];	
	}
}

