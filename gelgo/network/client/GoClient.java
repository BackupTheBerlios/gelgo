package network.client;
import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import core.*;
import network.general.*;

// note: this checks parameters, but if a parameter is an ArrayList
// 	it does not check for the right classes in the list

public class GoClient implements GoClientActions, CommandManagable
{
	boolean loggedIn = false;
	GoClientEvents frontend;
	CommandManager cMan;
	GoServerCommandQueue outQ;
	NetLoopThread netLoop;
	Socket socket = null;
	final static int PORT = 5555;	// what port should we use?

	public GoClient(GoClientEvents in_frontend)
	{
		frontend = in_frontend;
		outQ = new GoServerCommandQueue();
		cMan = new CommandManager(this);
	}
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}

	public void disconnect()
	{
		if (netLoop != null && netLoop.isConnected())
		{
			netLoop.disconnect();

			try
			{
				socket.close();	
			}
			catch (IOException e) { System.err.println("GoClient.disconnect(): " + e); }
		}
	}

	public void unknownCommand(GoServerCommand cmd)
	{
		frontend.recievedMalformedCommand(cmd);	
	}
	
	public void grabCommand(CommandManagable fromWho, GoServerCommand cmd)
	{
		cMan.execute(cmd);
	}
//--------------------------------------------------------------------------
// actions
//--------------------------------------------------------------------------
// login
	/** sends login request. */
	public void login(InetAddress address, String userName)
	{
		try
		{
			socket = new Socket(address, PORT);	
			outQ.push(new GoServerCommand("login", userName, -1));
			netLoop = new NetLoopThread(socket, outQ, cMan, false);
		}
		catch (IOException e)
		{
			System.err.println("GoClient.login(): Couldn't connect to server.");
			frontend.loginFailed("Couldn't connect to server!");
		}
	}

        /** joins game */
        public void joinGame(int tableId)
	{
		outQ.push(new GoServerCommand("joinGame", tableId));
	}

	/** start a new game */
	public void newGame(int in_size, double in_komi, int in_handicap, int amountOfTime)
	{
		ArrayList params = new ArrayList();
		
		params.add(new Integer(in_size));
		params.add(new Double(in_komi));
		params.add(new Integer(in_handicap));
		params.add(new Integer(amountOfTime));
		
		outQ.push(new GoServerCommand("newGame", params, -1));
	}

// normal play
	/** tell server to send a list of people at table (including watchers) */
	public void requestPlayersAtTableList(int tableId)
	{
		outQ.push(new GoServerCommand("requestPlayersAtTableList", tableId));
	}

	/** tells server where player would like to place a stone. */
	public void placeStone(int tableId, Coordinates coor)
	{
		outQ.push(new GoServerCommand("placeStone", coor, tableId));
	}

	/** tells server that player wishes to pass */
	public void pass(int tableId)
	{
		outQ.push(new GoServerCommand("pass", tableId));
	}

	/** tells server that player forfits game */
	public void surrender(int tableId)
	{
		outQ.push(new GoServerCommand("surrender", tableId));
	}

// post game negotiation
	/** toggle stones dead / alive during end-game negotiation */
	public void toggleDeadStones(int tableId, Coordinates coor)
	{
		outQ.push(new GoServerCommand("toggleDeadStones", coor, tableId));
	}

	/** notify server that player is done marking dead stones */
	public void doneMarkingDeadStones(int tableId)
	{
		outQ.push(new GoServerCommand("doneMarkingDeadStones", tableId));
	}

        /** request score board */
        public void requestScoreBoard(int tableId)
	{
		outQ.push(new GoServerCommand("requestScoreBoard", tableId));
	}

	/** notify server that player does NOT agree to dead stones marked by opponent */
	public void disputePostGameNegotiation(int tableId, String reason)
	{
		outQ.push(new GoServerCommand("disputePostGameNegotiation", reason, tableId));
	}

// chat
	/** send chat message to players at specified table.
	 * Note: players can not "hear" watcher chat. */
	public void tableChat(int tableId, String s)// table level chat
	{
		outQ.push(new GoServerCommand("tableChat", s, tableId));
	}

	/** send chat message to only to specified player. */
	public void playerChat(Player who, String s) // instant message
	{
		ArrayList params = new ArrayList();
		
		params.add(who);
		params.add(s);
		
		outQ.push(new GoServerCommand("playerChat", params, -1));
	}

	/** send chat message to all players on server. */
	public void serverChat(String s) // server level chat
	{
		outQ.push(new GoServerCommand("serverChat", s, -1));
	}

// watching games
	/** request current table list from server. */
	public void requestTableList()
	{
		outQ.push(new GoServerCommand("requestTableList", -1));
	}

// misc
	/** requests board from server. */
	public void requestBoard(int tableId)
	{
		outQ.push(new GoServerCommand("requestBoard", tableId));
	}

	/** requests list of all players on server. */
	public void requestServerPlayerList()
	{
		outQ.push(new GoServerCommand("requestServerPlayerList", -1));
	}

	/** request current clock for both user and opponent. */
	public void requestCurrentClocks(int tableId)
	{
		outQ.push(new GoServerCommand("requestCurrentClocks", tableId));
	}

//-----------------------------------------------------------------------
// events
//-----------------------------------------------------------------------
// login
	/** player is now logged in to server */
	public void loginOk(GoServerCommand cmd)
	{
		String motd;
System.out.println("GoClient.loginOk(): recieved loginOk command.");
		try
		{
			motd = (String) getParam(cmd, "java.lang.String");
			
			frontend.loginOk(motd);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.loginOk(): " + e); }
		
		loggedIn = true;
	}

	/** player is denied login, possibly bad password */	
	public void loginFailed(GoServerCommand cmd)
	{
		try
		{
			frontend.loginFailed((String) getParam(cmd, "java.lang.String"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.loginFailed(): " + e); }
	}

// normal play
	/** opponent has agreed to options, game has begun. */
	public void startNewGame(GoServerCommand cmd)
	{
		Color param0;
		double param1; 
		int param2; 
		int param3; 
		
		try	// this is terrible
		{
		param0 = (Color) getParam(cmd, "core.Color", 0);
		param1 = ((Double) getParam(cmd, "java.math.Double", 1)).doubleValue();
		param2 = ((Integer) getParam(cmd, "java.math.Integer", 2)).intValue();
		param3 = ((Integer) getParam(cmd, "java.math.Integer", 3)).intValue();
		
			frontend.startNewGame(cmd.getgID(), param0, param1, param2, param3);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.startNewGame(): " + e); }
	}

	/** returns from post-game negotiation to normal play (in case of dispute). */
	public void resumeNormalPlay(GoServerCommand cmd)
	{
		frontend.resumeNormalPlay(cmd.getgID());
	}

	/** opponent joined game */
	public void opponentJoinedGame(GoServerCommand cmd) 
	{
		try
		{
			frontend.opponentJoinedGame(cmd.getgID(), (Player) getParam(cmd, "network.general.Player"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.opponentJoinedGame(): " + e); }
	}
	
	/** signals that the last move is illegal, and that it is still the user's turn. */
	public void illegalLastMove(GoServerCommand cmd)
	{
		try
		{
			frontend.illegalLastMove(cmd.getgID(), (Coordinates) getParam(cmd, "core.Coordinates"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.illegalLastMovr(): " + e); }
	}

	/** server sends a new list of players at table to client. */
	public void refreshPlayersAtTableList(GoServerCommand cmd)
	{
		try
		{
			frontend.refreshPlayersAtTableList(cmd.getgID(), (ArrayList) getParam(cmd, "java.util.ArrayList"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.refreshPlayersAtTableList(): " + e); }
	}

	/** signals that the opponent has completed their move. */
	public void opponentPlacedStone(GoServerCommand cmd)
	{
		Board param0;
		Coordinates param1; 
		
		try	// this is terrible
		{
		param0 = (Board) getParam(cmd, "core.Board", 0);
		param1 = (Coordinates) getParam(cmd, "core.Coordinates", 1);
		
			frontend.opponentPlacedStone(cmd.getgID(), param0, param1);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.opponentPlacedStone: " + e); }
	}

	/** signals that opponent passed on last move. */ 
	public void opponentPassed(GoServerCommand cmd)
	{
		frontend.opponentPassed(cmd.getgID());
	}

	/** signals that opponent has surrendered. */
	public void opponentSurrenders(GoServerCommand cmd)
	{
		frontend.opponentSurrenders(cmd.getgID());
	}

// post game negotiation
	/** server recieved to passes in a row; begin post-game negotiation. */
	public void startPostGameNegotiation(GoServerCommand cmd)
	{
		frontend.startPostGameNegotiation(cmd.getgID());
	}
        
        /** opponent toggled stones dead */
        public void opponentToggledStones(GoServerCommand cmd)
	{
		try
		{
			frontend.opponentToggledStones(cmd.getgID(), (Board) getParam(cmd, "core.Board"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.opponentToggledStones(): " + e); }
	}

// Game Over / scoring
	/** game is over. (well, except for the post-negotiation negotiation ;) */
	public void gameOver(GoServerCommand cmd)
	{
		Board param0;
		Board param1; 
		int param2; 
		int param3; 
		
		try
		{
		param0 = (Board) getParam(cmd, "core.Board", 0);
		param1 = (Board) getParam(cmd, "core.Board", 1);
		param2 = ((Integer) getParam(cmd, "java.math.Integer", 2)).intValue();
		param3 = ((Integer) getParam(cmd, "java.math.Integer", 3)).intValue();
		
			frontend.gameOver(cmd.getgID(), param0, param1, param2, param3);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.gameOver(): " + e); }
	}
	
// chat
	/** other player has sent a chat message to user's table */
	public void inTableChat(GoServerCommand cmd)
	{
		Player param0;
		String param1; 
		
		try
		{
		param0 = (Player) getParam(cmd, "network.general.Player", 0);
		param1 = (String) getParam(cmd, "java.lang.String", 1);
		
			frontend.inTableChat(cmd.getgID(), param0, param1);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.inTableChat(): " + e); }
	}

	/** other player has sent a private message. */
	public void inPlayerChat(GoServerCommand cmd)
	{
		Player param0;
		String param1; 
		
		try
		{
		param0 = (Player) getParam(cmd, "network.general.Player", 0);
		param1 = (String) getParam(cmd, "java.lang.String", 1);
		
			frontend.inPlayerChat(param0, param1);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.inPlayerChat(): " + e); }
	}

	/** other player has sent chat message to everyone on server. */
	public void inServerChat(GoServerCommand cmd)
	{
		Player param0;
		String param1; 
		
		try
		{
		param0 = (Player) getParam(cmd, "network.general.Player", 0);
		param1 = (String) getParam(cmd, "java.lang.String", 1);
		
			frontend.inServerChat(param0, param1);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.inServerChat(): " + e); }
	}
	
// watching games
	/** server sent a new table list. */
	public void refreshTableList(GoServerCommand cmd)
	{
		try
		{
			frontend.refreshTableList((ArrayList) getParam(cmd, "java.util.ArrayList"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.refreshTableList(): " + e); }
	}
        
// misc
	/** server has sent a current board. */
	public void refreshBoard(GoServerCommand cmd)
	{
		try
		{
			frontend.refreshBoard(cmd.getgID(), (Board) getParam(cmd, "core.Board"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.refreshBoard(): " + e); }
	}

	/** server has sent a new list of players. */
	public void refreshServerPlayerList(GoServerCommand cmd)
	{
		try
		{
			frontend.refreshServerPlayerList((ArrayList) getParam(cmd, "java.util.ArrayList"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.refreshServerPlayerList(): " + e); }
	}

	/** server has sent current opponent clock. */
	public void refreshClocks(GoServerCommand cmd)
	{
		int param0;
		int param1;
	
		try
		{
			param0 = ((Integer) getParam(cmd, "java.math.Integer", 0)).intValue();
			param1 = ((Integer) getParam(cmd, "java.math.Integer", 1)).intValue();
			
			frontend.refreshClocks(cmd.getgID(), param0, param1);
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.refreshClocks(): " + e); }
	}
        
        /** refresh score board */
        public void refreshScoreBoard(GoServerCommand cmd)
	{
		try
		{
			frontend.refreshScoreBoard(cmd.getgID(), (Board) getParam(cmd, "core.Board"));
		}
		catch (InvalidCommandException e) { System.err.println("GoClient.refreshScoreBoard(): " + e); }
	}
	
//-----------------------------------------------------------------
// private util methods
//-----------------------------------------------------------------
	
	private Object getParam(GoServerCommand cmd, String classString) throws InvalidCommandException
	{
		if (!cmd.getcData().getClass().getName().equals(classString))
		{
System.out.println("GoClient.getParam(): class is " + cmd.getcData().getClass().getName() );
			frontend.recievedMalformedCommand(cmd);
			throw new InvalidCommandException("Invalid Command Parameter");
		}
		
		return cmd.getcData();
	}
	
	private Object getParam(GoServerCommand cmd, String classString, int element) throws InvalidCommandException
	{
		ArrayList params = null;
		boolean passed = false;
		
		if ( cmd.getcData().getClass().getName().equals("java.util.ArrayList") )
		{
			params = (ArrayList) cmd.getcData();
			
			if (params.get(element).getClass().getName().equals(classString))
			{
				passed = true;
			}
		}
		
		if (!passed)
		{
			frontend.recievedMalformedCommand(cmd);
			throw new InvalidCommandException("Invalid Command Parameter");
		}	
		
		return params.get(element);
	}
}

class InvalidCommandException extends GoException
{
	InvalidCommandException(String error)
	{
		super(error);
	}
}
