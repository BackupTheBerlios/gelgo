package game;
import java.io.*;

// status: in testing

/**
 * The coordinates class is meant to make board manipulation easier, its basically
 * a wrapper for navigation of board spaces
 *
 * @author Steve Asher
*/
public class Coordinates implements Serializable 
{
	int x, y;
	
	/**
         * Accessor for x component of a coordinate
         *
         * @return integer value of X
         */
	public int getX()
	{
		return x;
	}

        /**
         * Accessor for y component of a coordinate
         *
         * @return integer value of Y
         */	
	public int getY()
	{
		return y;
	}
	
	/**
         * Mutator for x component of a coordinate
         *
	 * @param i integer value to set X to
         *
         */
	public void setX(int i)
	{
		x = i;
	}
	
	/**        
         * Mutator for y component of a coordinate 
         *
         * @param i integer value to set Y to
         *
         */
	public void setY(int i)
	{
		y = i;
	}
	
	// usefull methods
	/**
	 * Navigation method for coordinates, returns coordinates
	 * with 1 added to the y component
         *
         * @return Coordinate
         */
	public Coordinates up()
	{
		Coordinates coor = new Coordinates(x, y - 1);
		return coor;
	}

        /**                                         
         * Navigation method for coordinates, returns coordinates
         * with 1 subtracted from the y component
         *
         * @return Coordinate
         */
	public Coordinates down()
	{
		Coordinates coor = new Coordinates(x, y + 1);
		return coor;
	}
	
        /**                                         
         * Navigation method for coordinates, returns coordinates
         * with 1 subtracted from the x component
         *
         * @return Coordinate
         */
	public Coordinates left()
	{
		Coordinates coor = new Coordinates(x - 1, y);
		return coor;
	}
	
        /**                                         
         * Navigation method for coordinates, returns coordinates
         * with 1 added to the x component
         *
         * @return Coordinate
         */	
	public Coordinates right()
	{
		Coordinates coor = new Coordinates(x + 1, y);
		return coor;
	}
	
        /**                                         
         * Navigation method for coordinates, returns coordinates
         * with proper value modification based on integer parameter.
         * Written for simplification of loop iteration + navigation.
         *
         * @param i integer value of direction desired
         *
         * @return Coordinate
         */
	public Coordinates direction(int i)
	{
		Coordinates coor = null;
	
		switch (i)
		{
			case 0:
				coor = this.up();
				break;
			case 1:
				coor = this.down();
				break;
			case 2:
				coor = this.left();
				break;
			case 3:
				coor = this.right();
				break;
		}
		
		return coor;
	}
	
        /**                                         
         * Overridden equals method for coordinates, if x && y
         * are equal, coordinates are considered equal
         *
	 * @param coor Coordinates to compare with this coordinate
	 *
         * @return boolean
         */
	public boolean equals(Object obj)
	{
		Coordinates coor;
		boolean passed = false;
		
		if (obj != null && obj instanceof Coordinates)
		{
			coor = (Coordinates) obj;
			
			if (coor.x == this.x && coor.y == this.y)
				passed = true;
		}

		return passed;
	}

	public int hashCode()
	{
		return toString().hashCode();
	}

	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
        /**                                         
         * Constructor, takes in x component, y component
         *
         */
	public Coordinates(int x_in, int y_in)
	{
		x = x_in;
		y = y_in;
	}
} 
