package test.game;

import game.*;
import java.util.*;

public class RandomBoardGenerator
{
	public static void main(String args[]) throws NumberFormatException
	{
		if (args.length == 1)
		{
			System.out.println(generate(Integer.parseInt(args[0])));
		}
	}

        // util method, not test
        public static Board generate(int size)
        {
                Board myBoard = new Board(size);
                Random newNumber = new Random();

                try
                {
                        for (int i = 0; i < size; i++)
                        {
                                for (int j = 0; j < size; j++)
                                {
                                        switch (newNumber.nextInt(3))
                                        {
                                                case 1:
                                                        myBoard.put(new Coordinates(j, i), Color.BLACK);
                                                        break;
                                                case 2:
                                                        myBoard.put(new Coordinates(j, i), Color.WHITE);
                                                        break;
                                        }
                                }
                        }
                } catch (GoException e) { System.err.println("BoardTest.randomBoard(): this really shouldn't happen."); }

                return myBoard;
        }
}
