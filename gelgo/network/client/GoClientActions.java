package network.client;
import network.general.*;
import java.net.*;
import java.util.*;
import core.*;

/** Minimal client actions
*	Will be implemented by GoClient.
*/
public interface GoClientActions
{
// login
	/** sends login request. */
	public void login(InetAddress address, String userName);

        /** joins game */
        public void joinGame(int tableId);

	/** start a new game */
	public void newGame(int in_size, double in_komi, int in_handicap, int amountOfTime);

	/** disconnect */
	public void disconnect();
// normal play
	/** tell server to send a list of people at table (including watchers) */
	public void requestPlayersAtTableList(int tableId);

	/** tells server where player would like to place a stone. */
	public void placeStone(int tableId, Coordinates coor);

	/** tells server that player wishes to pass */
	public void pass(int tableId);

	/** tells server that player forfits game */
	public void surrender(int tableId);

// post game negotiation
	/** toggle stones dead / alive during end-game negotiation */
	public void toggleDeadStones(int tableId, Coordinates coor); // let's do this on the server

	/** notify server that player is done marking dead stones */
	public void doneMarkingDeadStones(int tableId);
        
        /** request score board */
        public void requestScoreBoard(int tableId);

	/** notify server that player does NOT agree to dead stones marked by opponent */
	public void disputePostGameNegotiation(int tableId, String reason); // goes back to normal-play mode

// chat
	/** send chat message to players at specified table.
	 * Note: players can not "hear" watcher chat. */
	public void tableChat(int tableId, String s);  // table level chat

	/** send chat message to only to specified player. */
	public void playerChat(Player who, String s); // instant message

	/** send chat message to all players on server. */
	public void serverChat(String s); // server level chat

// watching games
	/** request current table list from server. */
	public void requestTableList();

// misc
	/** requests board from server. */
	public void requestBoard(int tableId);

	/** requests list of all players on server. */
	public void requestServerPlayerList();

	/** request current clock for both user and opponent. */
	public void requestCurrentClocks(int tableId);
}
