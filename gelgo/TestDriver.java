import core.*;

public class TestDriver
{
	public static void main(String args[]) throws OffBoardException
	{
		TestBoard testBoard = new TestBoard();
		TestGoGame testGoGame = new TestGoGame();

		// call test methods in main
		// tests should be in order of dependency (ie. lookAt, put, remove)

		// Board tests
		System.out.println( testBoard.t_Board() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_areOnBoard() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_lookAt() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_put() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_remove() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_toString() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_fromString() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_clone() ? "passed." : "failed!!!" );
		System.out.println( testBoard.t_equals() ? "passed." : "failed!!!");
		System.out.println("Board tests complete.\n");

		// GoGame tests
		System.out.println( testGoGame.t_GoGame() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_pass() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_isLive() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_whosTurn() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_getTurnCount() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_undo() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_play() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_NoahsBug() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_scoreGame() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_territoryBug() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_superKo() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_lastMove() ? "passed." : "failed!!!" );
		System.out.println( testGoGame.t_hessBug() ? "passed." : "failed!!!" );
		System.out.println("GoGame tests complete.\n");

		System.out.println("All tests complete.");
		System.exit(0);
	}


}

class TestBoard
{
	public boolean t_put()
	{
		boolean testPass = false;
		int bSize = 9;
		Board myBoard = new Board(bSize);

		System.out.print("Testing Board.put(): ");

		// place a stone
		try { myBoard.put(0, 0, Color.BLACK); } catch (GoException e) { System.out.println("\t" + e); }

		try
		{	// make sure that the stone was placed
			if ( myBoard.lookAt(0,0) != null && myBoard.lookAt(0,0).getColor() == Color.BLACK)
				testPass = true;
			else
				System.out.println("\tBasic put failed.");
		}
		catch (OffBoardException e)
		{
			System.out.println("\t" + e);
			testPass = false;
		}

		// try to put a stone on the first stone
		try
		{
			myBoard.put(0, 0, Color.WHITE);
			testPass = false;  // this line should not execute
			System.out.println("\tplaced stone on existing stone.");
		}
		catch (InvalidSpaceException e)
		{
			// should catch this exception
			testPass = testPass && true;
		}
		catch (OffBoardException e)
		{
			System.out.println("\t" + e);
			testPass = false;
		}

		// try to put a stone in off the board
		try
		{
			myBoard.put(bSize, bSize, Color.BLACK);
			testPass = false; // this line should not execute
			System.out.println("\tput stone off of board.");
		}
		catch (InvalidSpaceException e)
		{
			testPass = false;
			System.out.println("\t" + e);
		}
		catch (OffBoardException e)
		{
			// should catch this exception
			testPass = testPass && true;
		}
			return testPass;
	}

	public boolean t_remove()
	{
		boolean testPass = false;
		int bSize = 9;
		Board myBoard = new Board(bSize);

		System.out.print("Testing Board.remove(): ");

		// place a stone
		try { myBoard.put(0, 0, Color.BLACK); } catch (GoException e) { System.out.println("\t" + e); }

		// remove stone
		try
		{
			myBoard.remove(0, 0);

			if (myBoard.lookAt(0, 0) == null)
				testPass = true;
			else
				System.out.println("\tBasic remove failed.");
		}
		catch (OffBoardException e)
		{
			testPass = false;
			System.out.println("\t" + e);
		}
		catch (InvalidSpaceException e)
		{
			testPass = false;
			System.out.println("\t" + e);
		}

		// try to remove a stone from empty space
		try
		{
			myBoard.remove(1, 1);
			testPass = false;  // this line shouldn't execute
			System.out.println("\tremoved non-existant stone");
		}
		catch (OffBoardException e)
		{
			System.out.println("\t" + e);
		}
		catch (InvalidSpaceException e)
		{
			// should catch this exception
			testPass = testPass && true;
		}

		// try to remove off-board stone
		try
		{
			myBoard.remove(bSize, bSize);
			testPass = false; // this line shouldn't execute
			System.out.println("\tremove off-board failed");
		}
		catch (OffBoardException e)
		{
			// should catch this exception
			testPass = testPass && true;
		}
		catch (InvalidSpaceException e)
		{
			System.out.println("\t" + e);
		}

		return testPass;
	}

	public boolean t_toString()
	{
		boolean testPass = false;
		int bSize = 3;
		Board myBoard = new Board(bSize);
		String should_look_like =	"#.*" + "\n" +
								".*." + "\n" +
								"#.#" + "\n";

		System.out.print("Testing Board.toString(): ");

		try { myBoard.put(0, 0, Color.BLACK); } catch (GoException e) { System.out.println("\t" + e); }
		try { myBoard.put(1, 1, Color.WHITE); } catch (GoException e) { System.out.println("\t" + e); }
		try { myBoard.put(2, 2, Color.BLACK); } catch (GoException e) { System.out.println("\t" + e); }
		try { myBoard.put(2, 0, Color.WHITE); } catch (GoException e) { System.out.println("\t" + e); }
		try { myBoard.put(0, 2, Color.BLACK); } catch (GoException e) { System.out.println("\t" + e); }

		if (should_look_like.equals(myBoard.toString()))
			testPass = true;

		return testPass;
	}

	public boolean t_fromString()
	{
		boolean testPass = false;
		Board myBoard = null;
		int bSize = 3;
		String board_string = 	"#.*" + "\n" +
							"*#." + "\n" +
							".*#" + "\n";

		System.out.print("Testing Board.fromString(): ");

		myBoard = Board.fromString(board_string);

		if (myBoard != null)
			if (board_string.equals(myBoard.toString()))
				testPass = true;

		return testPass;
	}

	public boolean t_lookAt()
	{
		boolean testPass = false;
		int bSize = 9;
		Board myBoard = new Board(bSize);
		Stone testStone;

		System.out.print("Testing Board.lookAt(): ");
		// place an initial stone
		try { myBoard.put(0, 0, Color.BLACK); } catch (GoException e) { System.out.println("\t" + e); }

		// look at the stone
		try
		{
			testStone = myBoard.lookAt(0, 0);

			if (testStone != null && testStone.getColor() == Color.BLACK)
				testPass = true;
		}
		catch (OffBoardException e)
		{
			testPass = false;
			System.out.println("\t" + e);
		}

		// look at empty space
		try
		{
			testStone = myBoard.lookAt(1, 1);

			if (testStone == null)
				testPass = testPass && true;
			else
				testPass = false;
		}
		catch (OffBoardException e)
		{
			testPass = false;
			System.out.println("\t" + e);
		}

		// look off of board
		try
		{
			myBoard.lookAt(bSize, bSize);
			testPass = false;	// this line shouldn't execute
			System.out.println("\tlook off board failed.");
		}
		catch (OffBoardException e)
		{
			// should catch this exception
			testPass = testPass && true;
		}

		return testPass;
	}

	public boolean t_areOnBoard()
	{
		boolean testPass = false;
		int bSize = 9;
		Board testBoard = new Board(bSize);

		System.out.print("Testing Board.areOnBoard(): ");

		// test bad coordinates
		if (!testBoard.areOnBoard(bSize, bSize - 1) || !testBoard.areOnBoard(bSize -1, bSize))
			testPass = true;

		// test good coordinates
		for (int i = 0; i < bSize; i++)
			for (int j = 0; j < bSize; j++)
				if (testBoard.areOnBoard(j, i))
					testPass = testPass && true;
                                else
                                        testPass = false;

		return testPass;
	}

	public boolean t_clone()
	{
		boolean testPass = true;
		Board testBoard = null;
		Board origBoard = null;

		 origBoard = Board.fromString(	"#.*\n" +
									".*.\n" +
									"*#.\n");

		System.out.print("Testing Board.clone(): ");

		try
		{
			testBoard = (Board)origBoard.clone();

			for (int i = 0; i < origBoard.getSize(); i++)
			{
				for (int j = 0; j< origBoard.getSize(); j++)
				{
					if (origBoard.lookAt(j, i) == testBoard.lookAt(j, i))
						testPass = testPass && true;
					else
						testPass = false;
				}
			}
		}
		catch (GoException e)
		{
			testPass = false;
		}

		return testPass;
	}

	public boolean t_equals()
	{
		int bSize = 3;
		Board testBoard = new Board(bSize);

		System.out.print("Testing Board.equals(): ");

		if (testBoard.equals((Board)testBoard.clone()))
			return true;
		else
			return false;
	}

	public boolean t_Board()
	{
		boolean testPass = false;
		int bSize = 3;
		Board testBoard = null;
		String stringBoard =	"#*#\n" +
							"*#*\n" +
							"#*#\n";

		System.out.print("Testing Board.Board(): ");

		testBoard = new Board(bSize);
		if (testBoard.getSize() == bSize)
			testPass = true;

		/*  // not currently implemented in Board
		testBoard = new Board(stringBoard);

		if (testBoard != null && stringBoard.equals(testBoard.toString()))
			testPass = testPass && true;
		else
			testPass = false;
		*/

		return testPass;
	}

}

class TestGoGame
{
	public boolean t_isLive() throws OffBoardException
	{
		boolean testPass = false;
		GoGame myGame, myGame2;
		String board_string =	"*****." + "\n" +
							"*###*." + "\n" +
							"*#*#*." + "\n" +
							"*###*." + "\n" +
							"******" + "\n" +
							"....*." + "\n";

                String board_string2 =	"#*." + "\n" +
							"**." + "\n" +
							"..." + "\n";

		String dead_string = 	"......" + "\n" +
							".###.." + "\n" +
							".#.#.." + "\n" +
							".###.." + "\n" +
							"......" + "\n" +
							"......" + "\n";

		String dead_string2 = 	"#.." + "\n" +
							"..." + "\n" +
							"..." + "\n";

		System.out.print("Testing GoGame.isLive(): ");

		myGame = new GoGame(board_string);
		myGame2 = new GoGame(board_string2);

		// test for liberties of (dead) circular black stones
		if (!myGame.isLive(1, 1))
			testPass = true;

                // test tracking of dead stones
                if (dead_string.equals(myGame.deadString()))
                        testPass = testPass && true;
                else
                        testPass = false;

		// test for liberties of (live) circular white stones
		if (myGame.isLive(0, 0))
			testPass = testPass && true;
		else
			testPass = false;

		// test for liberties of empty space
		if (myGame.isLive(5, 5))
			testPass = testPass && true;
		else
			testPass = false;

		// scenario 2: white should be alive
		if (myGame2.isLive(1, 1))
			testPass = testPass && true;
		else
			testPass = false;

		// scenario 2: black should be dead
		if (!myGame2.isLive(0, 0))
			testPass = testPass && true;
		else
			testPass = false;

		// scenario 2: test tracking of dead stones
		if (dead_string2.equals(myGame2.deadString()))
			testPass = testPass && true;
		else
			testPass = false;

		return testPass;
	}

	public boolean t_play()
	{
		boolean testPass = false;
		int bSize = 4;
		GoGame myGame = new GoGame(bSize);
		String firstMove =  "....\n" +
                                    ".#..\n" +
                                    "....\n" +
                                    "....\n";

		String stringEndGame =	"..*.\n" +
					"*...\n" +
					".**.\n" +
					".*.*\n";

		System.out.print("Testing GoGame.play(): ");

		if (myGame.play(6, 6))  // try to move off Board
		{
			System.out.print("allowed off Board move. ");
			return false;
		}

		myGame.play(1, 1);
		myGame.undo();

		if (myGame.toString().equals(firstMove))
		{
			System.out.print("didn't undo move. ");
			return false;
		}

		myGame.play(1, 1);

		if (!myGame.toString().equals(firstMove))
		{
			System.out.print("probably Super-Ko confusion. ");
			return false;
		}

		myGame.play(2, 2);
		myGame.play(2, 1);
		myGame.play(1, 2);
		myGame.play(3, 2);
		myGame.play(0, 1);
		myGame.play(2, 3);
		myGame.play(1, 3);
		myGame.play(3, 1);

		if (!myGame.play(3, 3))
		{
			testPass = false;
			System.out.print("didn't allow white capture. ");
		}
		else
		{
			myGame.play(1, 0);
			myGame.play(2, 0);
			myGame.play(3, 0);

			if (myGame.play(2, 0))	// white suicide, shouldn't happen
			{
				testPass = false;
				System.out.print("allowed white suicide. ");
			}
			else
			{
				myGame.pass();
				myGame.play(0, 0);
				myGame.play(2, 0);

				if (stringEndGame.equals(myGame.toString()))
					testPass = true;
			}
		}

		return testPass;
	}

	public boolean t_pass()
	{
		boolean testPass = false;
                GoGame myGame = new GoGame(9);
                int initialCount = myGame.getTurnCount();

                System.out.print("Testing GoGame.pass(): ");

                myGame.pass();
                if (myGame.getTurnCount() == initialCount + 1)
                        testPass = true;

		return testPass;
	}

	public boolean t_whosTurn()
	{
		boolean testPass = false;
                int bSize = 9;
                GoGame myGame = new GoGame(bSize);

		System.out.print("Testing GoGame.whosTurn(): ");

                // test one (should be black)
                if (myGame.whosTurn() == Color.BLACK)
                        testPass = true;

                // test two (should be white)
                myGame.pass();
                if (myGame.whosTurn() == Color.WHITE)
                        testPass = testPass && true;
                else
                        testPass = false;

		return testPass;
	}

	public boolean t_getTurnCount()
	{
		boolean testPass = false;
                int bSize = 9;
                GoGame myGame = new GoGame(bSize);

		System.out.print("Testing GoGame.getTurnCount(): ");

                // test zero
                if (myGame.getTurnCount() == 0)
                        testPass = true;

                // test one
                myGame.pass();
                if (myGame.getTurnCount() == 1)
                        testPass = testPass && true;
                else
                        testPass = false;

		return testPass;
	}

	public boolean t_undo()
	{
		int bSize = 3;
		GoGame myGame = new GoGame(bSize);
		GoGame testGame = new GoGame(bSize);

		System.out.print("Testing GoGame.undo(): ");

		myGame.play(0, 0);
		myGame.undo();

		if (myGame.toString().equals(testGame.toString()))
			return true;
		else
			return false;

	}

	public boolean t_GoGame()
	{
		boolean testPass = false;
                int bSize = 9;
                GoGame myGame = null;

                System.out.print("Testing GoGame.GoGame(): ");

                myGame = new GoGame(bSize);

                // test size
                if (myGame.getSize() == bSize)
                        testPass = true;

                // test turn counter
                if (myGame.getTurnCount() == 0)
                        testPass = testPass && true;
                else
                        testPass = false;

		return testPass;
	}

	public boolean t_NoahsBug()
	{
		GoGame myGame = new GoGame(	"*#." + "\n" +
						"..." + "\n" +
						"*#." + "\n"	);

		String endString =	".#." + "\n" +
					"#.." + "\n" +
					".#." + "\n";

		System.out.print("Testing Noah's multiple group capture bug: ");

		myGame.play(0, 1);

		if (endString.equals(myGame.toString()))
			return true;
		else
			return false;
	}

	public boolean t_scoreGame () {

		boolean testPass = false;
		int bSize = 6;

		GoGame myGame = null;

		System.out.print("Testing GoGame.scoreGame(): ");

		// steve's relatively complex score situation
		// black and white each have only one space as territory 
		String boardString = 			"#########***\n" +
							"#.......#*.*\n" +
							"#.#####.#*.*\n" +
							"#.#.....#*#*\n" +
							"#.#.#####*.*\n" +
							"#.#.##..#*.*\n" +
							"#.#.#.#.#*.*\n" +
							"#.#.###.#*.*\n" +
							"#.#.....#*.*\n" +
							"#.#######***\n" +
							"#.*......*.*\n" +
							"##**********\n";


		myGame = new GoGame(boardString);

		String scoreString = 			"#########***\n" +
                                                        "#.......#*.*\n" +
                                                        "#.#####.#*.*\n" +
                                                        "#.#.....#*#*\n" +
                                                        "#.#.#####*.*\n" +
                                                        "#.#.##..#*.*\n" +
                                                        "#.#.###.#*.*\n" +
                                                        "#.#.###.#*.*\n" +
                                                        "#.#.....#*.*\n" +
                                                        "#.#######***\n" +
                                                        "#.*......***\n" +
                                                        "##**********\n";
		
		Board scoreBoard = myGame.scoreGame();

 //            System.out.println("Scored Board:\n"+scoreBoard.toString());

		if (scoreString.equals(scoreBoard.toString()))
			testPass = true;

		double blackScore = myGame.getBlackScore();
		double whiteScore = myGame.getWhiteScore();

		if (blackScore == 60.0)
		   testPass = testPass && true;

		if (whiteScore == 36.0)
			testPass = testPass && true;

		return testPass;
	}
	
	public boolean t_territoryBug()
	{
		String boardString1 =	"........#....#.....\n" +
					"........#.....#....\n" +
					"........#.....#....\n" +
					"........#......#...\n" +
					"......###.......#..\n" +
					"######...........##\n" +
					"...................\n" +
					"...................\n" +
					".......###.........\n" +
					"......#...#........\n" +
					"......#...#........\n" +
					".......###.........\n" +
					"...................\n" +
					"...................\n" +
					"##...............##\n" +
					"..#.............#..\n" +
					"...#...........#...\n" +
					"...##..........#...\n" +
					"....#.........#....\n";
					
		String boardString2 =	"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n" +
					"###################\n";
		
		System.out.print("Testing GoGame.scoreGame() territory bug: ");
					
		GoGame myGame = new GoGame(boardString1);
		
		if (boardString2.equals(myGame.scoreGame().toString()))
			return true;
		else 
			return false;			
	}

	public boolean t_superKo()
	{
		boolean testPass = false;
		GoGame myGame;
		String boardString =	"..#.." + "\n" +
					".#*#." + "\n" +
					".*.*." + "\n" +
					"..*.." + "\n" +
					"....." + "\n";
		System.out.print("Testing SuperKo rule: ");
		
		myGame = new GoGame(boardString);

		myGame.play(2, 2);

		if(myGame.play(2, 1))
			testPass = false;
		else
			testPass = true;

		return testPass;
	}

	public boolean t_lastMove()
	{
		String boardString1 =	"*..\n" +
					"#..\n" +
					"...\n";
		
		GoGame myGame = new GoGame(boardString1);
		Coordinates myMove = new Coordinates(1, 0);
		
		System.out.print("Testing GoGame.lastMove(): ");
		
		myGame.play(myMove);
		
		if (myMove.equals(myGame.lastMove()))
			return true;
		else
			return false;
	}
	
	public boolean t_hessBug()
	{
		int bSize = 9;
		GoGame myGame = new GoGame(bSize);
		String endBoard = 	"*#*#.....\n" +
					"*#*#.....\n" +
					".#*#.....\n" +
					"#*.......\n" +
					"*........\n" +
					".........\n" +
					".........\n" +
					".........\n" +
					".........\n";

		System.out.print("Testing Hess's disapearing stone bug: ");

		myGame.play(1, 0);
		myGame.play(2, 0);
		myGame.play(1, 1);
		myGame.play(2, 1);
		myGame.play(1, 2);
		myGame.play(2, 2);
		myGame.play(0, 3);
		myGame.play(0, 4);
		myGame.play(3, 0);
		myGame.play(1, 3);
		myGame.play(3, 1);
		myGame.play(0, 0);
		myGame.play(3, 2);
		myGame.play(0, 1);

		if (myGame.toString().equals(endBoard))
			return true;
		else
			return false;
	}
}

