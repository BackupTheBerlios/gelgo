package network.stevesServer;
import java.io.*;
import java.util.*;
import java.net.*;
import network.general.*;
import core.*;

// this is just a rough draft of the player class  --steve

// the Connection class took over some of these responsibilities
// i have yet to take stuff out of here.  --steve

public class ServerPlayer implements CommandManagable
{
	private String name;
	private GoServer server;
	private Connection out;
	private ArrayList myGameMasters; // facilitates server rules
	private CommandManager cMan;
	
	public ServerPlayer(String in_name, Connection connection, GoServer in_server)
	{
		name = in_name;
		server = in_server;
		out = connection;
		myGameMasters = new ArrayList();
		cMan = new CommandManager(this);
	}

	public String getName()
	{
		return name;
	}

//----------------------------------------------
//events
//----------------------------------------------

        /** joins game */
        //public void joinGame(int tableId);
	public void joinGame(GoServerCommand cmd) {}

	/** start a new game */
	//public void newGame(int in_size, double in_komi, int in_handicap, int amountOfTime);
	public void newGame(GoServerCommand cmd) {}

// normal play
	/** tell server to send a list of people at table (including watchers) */
	//public void requestPlayersAtTableList(int tableId);
	public void requestPlayersAtTableList(GoServerCommand cmd) {}

	/** tells server where player would like to place a stone. */
	//public void placeStone(int tableId, Coordinates coor);
	public void placeStone(GoServerCommand cmd) {}

	/** tells server that player wishes to pass */
	//public void pass(int tableId);
	public void pass(GoServerCommand cmd) {}

	/** tells server that player forfits game */
	//public void surrender(int tableId);
	public void surrender(GoServerCommand cmd) {}

// post game negotiation
	/** toggle stones dead / alive during end-game negotiation */
	//public void toggleDeadStones(int tableId, Coordinates coor); // let's do this on the server
	public void toggleDeadStones(GoServerCommand cmd) {}

	/** notify server that player is done marking dead stones */
	//public void doneMarkingDeadStones(int tableId);
	public void doneMarkingDeadStones(GoServerCommand cmd) {}
        
        /** request score board */
        //public void requestScoreBoard(int tableId);
	public void requestScoreBoard(GoServerCommand cmd) {}

	/** notify server that player does NOT agree to dead stones marked by opponent */
	//public void disputePostGameNegotiation(int tableId, String reason); // goes back to normal-play mode
	public void disputePostGameNegotiation(GoServerCommand cmd) {}

// chat
	/** send chat message to players at specified table.
	 * Note: players can not "hear" watcher chat. */
	//public void tableChat(int tableId, String s);  // table level chat
	public void tableChat(GoServerCommand cmd) {}

	/** send chat message to only to specified player. */
	//public void playerChat(Player who, String s); // instant message
	public void playerChat(GoServerCommand cmd) {}

	/** send chat message to all players on server. */
	//public void serverChat(String s); // server level chat
	public void serverChat(GoServerCommand cmd) {}

// watching games
	/** request current table list from server. */
	//public void requestTableList();
	public void requestTableList(GoServerCommand cmd) {}

// misc
	/** requests board from server. */
	//public void requestBoard(int tableId);
	public void requestBoard(GoServerCommand cmd) {}

	/** requests list of all players on server. */
	//public void requestServerPlayerList();
	public void requestServerPlayerList() {}

	/** request current clock for both user and opponent. */
	//public void requestCurrentClocks(int tableId);
	public void requestCurrentClocks(GoServerCommand cmd) {}

//--------------------------------------------------------------
// CommandManagable interface
// -------------------------------------------------------------
	public void unknownCommand(GoServerCommand cmd)
	{
		server.grabCommand(this, cmd);	
	}
	
	public void grabCommand(CommandManagable fromWho, GoServerCommand cmd)
	{
		cMan.execute(cmd);
	}
}
