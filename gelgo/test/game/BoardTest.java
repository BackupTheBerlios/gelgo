package test.game;

import game.*;
import java.util.*;
import junit.framework.*;

public class BoardTest extends TestCase
{
	private Board board1;
	private Board board2;
	private static final int BSIZE = 3;
	
	protected void setUp()
	{
		board1 = new Board(BSIZE);
		board2 = new Board(BSIZE);
	}

	public void testBoardEquals()
	{
		Assert.assertEquals(board1, board1);
		Assert.assertEquals(board1, board2);
		Assert.assertTrue(!board1.equals(null));
		Assert.assertTrue(!board1.equals("...\n...\n...\n"));

		// requires put() to work correctly
		try
		{
			board1.put(new Coordinates(0, 0), Color.BLACK);
			Assert.assertTrue(!board1.equals(board2));
			board2.put(new Coordinates(0, 0), Color.BLACK);
			Assert.assertEquals(board1, board2);
		} catch (GoException e) { Assert.fail(e.toString()); }
	}

	public void testBoardPut()
	{
		try
		{
			board1.put(new Coordinates(0, 0), Color.BLACK);
			board1.put(new Coordinates(2, 2), Color.WHITE);
			board2 = board1;
		} 
		catch (GoException e) 
		{ 
			Assert.fail("BoardTest.testBoardPut(): caught unexpected exception: " + e);
		}

		Assert.assertEquals(board1, Board.fromString(new Stone(Color.BLACK) + "..\n...\n.." + new Stone(Color.WHITE) + "\n"));
			
		try
		{
			board1.put(new Coordinates(3, 3), Color.WHITE);
		}
		catch (OffBoardException e)
		{
			Assert.assertEquals(board1, board2);	
		}
		catch (InvalidSpaceException e)
		{
			Assert.fail("Should have thrown an OffBoardException!" + e);
		}
	}

	public void testBoardRemove()
	{
		try
		{
			board1.put(new Coordinates(0, 0), Color.BLACK);
			board1.put(new Coordinates(2, 2), Color.BLACK);
		} catch (GoException e) { Assert.fail(); }

		try
		{
			board1.remove(new Coordinates(2, 2));
			board1.remove(new Coordinates(0, 0));	
		} catch (GoException e) { Assert.fail(); }

		Assert.assertEquals(board1, board2);

		try
		{
			board1.remove(new Coordinates(3, 3));
		} 
		catch (OffBoardException e)
		{
			Assert.assertEquals(board1, board2);
		}
		catch (GoException e) { Assert.fail("Should have thrown OffBoardException!" + e); }

		try
		{
			board1.remove(new Coordinates(0, 0));
		}
		catch (InvalidSpaceException e)
		{
			Assert.assertEquals(board1, board2);
		}
		catch (GoException e) {	Assert.fail("Should have thrown InvalidSpaceException!" + e); }
	}

	public void testBoardLookAt()
	{
		Stone myStone = new Stone(Color.BLACK);
		
		try
		{
			board1.put(new Coordinates(1, 1), Color.BLACK);
			Assert.assertEquals(board1.lookAt(new Coordinates(1, 1)), myStone);
			
		} catch (GoException e) { Assert.fail(e.toString()); }

		try
		{
			myStone = board1.lookAt(null);	
		}
		catch (GoException e)
		{
			Assert.assertTrue(myStone != null);
		}

		try
		{
			myStone = null;
			myStone = board1.lookAt(new Coordinates(3, 3));
			Assert.fail("looked off of the board.");
		}
		catch (OffBoardException e)
		{
			Assert.assertNull(myStone);
		}
	}

	public void testBoardAreOnBoard()
	{
		for (int i = 0; i < BSIZE; i++)
			for (int j = 0; j < BSIZE; j++)
				Assert.assertTrue(board1.areOnBoard(new Coordinates(j, i)));

		Assert.assertFalse(board1.areOnBoard(new Coordinates(3, 3)));
		Assert.assertFalse(board1.areOnBoard(null));
	}

	public void testBoardClone()
	{
		try
		{
			board1.put(new Coordinates(1, 1), Color.BLACK);
		} catch (GoException e) { Assert.fail(); }
		
		Board cloneBoard = (Board) board1.clone();

		Assert.assertEquals(cloneBoard, board1);
		Assert.assertNotSame(cloneBoard, board1);
	}

	public void testBoardHashCode()
	{
		final int bigSize = 19;
		board1 = RandomBoardGenerator.generate(bigSize); 
		Coordinates lastStone = null;
		
		Assert.assertEquals(board1.hashCode(), board1.hashCode());
		Assert.assertTrue(board1.hashCode() != board2.hashCode());
		board2 = (Board) board1.clone();
		Assert.assertEquals(board1.hashCode(), board2.hashCode());
		
		try
		{
			for (int i = 0; i < bigSize; i++)
			{
				for (int j = 0; j < bigSize; j++)
				{
					if (board1.lookAt(new Coordinates(j, i)) != null)
						lastStone = new Coordinates(j, i);
				}
			}

			board1.remove(lastStone);
		}
		catch (GoException e) { Assert.fail("This is an error in the test."); }

		Assert.assertTrue(board1.hashCode() != board2.hashCode());
	}

	public void testBoardToString()
	{
		Assert.assertEquals(board1.toString(), "...\n...\n...\n");
		
		try
		{
			board1.put(new Coordinates(0, 0), Color.BLACK);
			board1.put(new Coordinates(2, 2), Color.BLACK);
		} catch (GoException e) { Assert.fail(); }
		
		Assert.assertEquals(board1.toString(), "#..\n...\n..#\n");
	}

	public void testBoardFromString()
	{
		board2 = Board.fromString("#..\n...\n..#\n");

		try
		{
			board1.put(new Coordinates(0, 0), Color.BLACK);
			board1.put(new Coordinates(2, 2), Color.BLACK);
		} catch (GoException e) { Assert.fail(); }

		Assert.assertEquals(board1, board2);
	}
	
	public static Test suite()
	{
		return new TestSuite(BoardTest.class);
	}

	public static void main(String args[])
	{
		junit.textui.TestRunner.run(suite());
	}
}
