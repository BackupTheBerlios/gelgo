package game;
import java.io.*;

/** A stone with a {@link Color}. 
 * @see Color
 */
public class Stone implements Serializable 
{
	private Color myColor;

	/** Returns {@link Color} of Stone. */
	public Color getColor()
	{
		return myColor;					  
	}
	
	/** Converts Stone to a string based on {@link Color}. */
	public String toString()
	{
		if(myColor == Color.BLACK){
			return "#";
		}
		return "*";
	}

	public boolean equals(Object obj)
	{
		Stone s;
		boolean passed = false;
		
		if (obj instanceof Stone)
		{
			s = (Stone) obj;
			if (s.myColor == this.myColor)
				passed = true;
		}

		return passed;
	}

	/** Just a constructor.
	* @param s {@link Color} of Stone
	*/
	public Stone(Color s)
	{
			myColor = s;
	}
}
