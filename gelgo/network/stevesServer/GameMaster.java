package network.stevesServer;
import core.*;
import java.util.*;
import network.general.*;

        // keep track of:
        //      game
        //      clocks
        //      players
        //      table chat
        //      post game
        //      scoring

public class GameMaster implements Timed
{
	GoGame game;
	GoGameOptions options;
	ArrayList watchers;
	ServerPlayer white, black; // player to create table is white
	TimeBombThread whiteTime, blackTime;

	public GameMaster(ServerPlayer who, int in_size, double in_komi, int in_handicap, int amountOfTime)
	{
		options = new GoGameOptions(in_size, in_komi, in_handicap);
		game = new GoGame(in_size, in_komi, in_handicap);
		whiteTime = new TimeBombThread(this, amountOfTime);
		blackTime = new TimeBombThread(this, amountOfTime);
		white = who;
		black = null;
	}

	public void timeIsUp()
	{

	}

	// these should all return boolean
        /** joins game */
        //public void joinGame(int tableId);
	public void joinGame(ServerPlayer who) {}

// normal play
	/** tell server to send a list of people at table (including watchers) */
	//public void requestServerPlayersAtTableList(int tableId);
	public void requestServerPlayersAtTableList(ServerPlayer who) { }
	
	/** tells server where player would like to place a stone. */
	//public void placeStone(int tableId, Coordinates coor);
	public void placeStone(ServerPlayer who, Coordinates coor) {}

	/** tells server that player wishes to pass */
	//public void pass(int tableId);
	public void pass(ServerPlayer who) {}

	/** tells server that player forfits game */
	//public void surrender(int tableId);
	public void surrender(ServerPlayer who) {}

// post game negotiation
	/** toggle stones dead / alive during end-game negotiation */
	//public void toggleDeadStones(int tableId, Coordinates coor); // let's do this on the server
	public void toggleDeadStones(ServerPlayer who, Coordinates coor) {}

	/** notify server that player is done marking dead stones */
	//public void doneMarkingDeadStones(int tableId);
	public void  doneMarkingDeadStones(ServerPlayer who) {}
        
        /** request score board */
        //public void requestScoreBoard(int tableId);
	public void requestScoreBoard(ServerPlayer who) {}

	/** notify server that player does NOT agree to dead stones marked by opponent */
	//public void disputePostGameNegotiation(int tableId, String reason); // goes back to normal-play mode
	public void disputePostGameNegotiation(ServerPlayer who, String reason) {}

// chat
	/** send chat message to players at specified table.
	 * Note: players can not "hear" watcher chat. */
	//public void tableChat(int tableId, String s);  // table level chat
	public void tableChat(ServerPlayer from, String s) {}

// misc
	/** requests board from server. */
	//public void requestBoard(int tableId);
	public void requestBoard(ServerPlayer who) {}

	/** request current clock for both user and opponent. */
	//public void requestCurrentClocks(int tableId);
	public void requestCurrentClocks(ServerPlayer who) {}
}
