package network.client;
import network.general.*;
import core.*;
import java.util.*;

public class DumbUserInterface implements GoClientEvents 
{
	public GoClientActions client;
	
	public DumbUserInterface()
	{
		client = new GoClient(this);
	}
	
//--------------------------------------------------
// GoClientEvents interface
//--------------------------------------------------
// login
	/** player is now logged in to server */
	public void loginOk(String motd)
	{
		System.out.println("DumbUserInterface: loginOk: " + motd);
	}

	/** player is denied login, possibly bad password */	
	public void loginFailed(String reason)
	{
		System.out.println("DumbUserInterface: loginFailed: " + reason);
	}

// normal play
	/** opponent has agreed to options, game has begun. */
	public void startNewGame(int tableId, Color myColor, double komi, int handicap, int boardSize) {}

	/** returns from post-game negotiation to normal play (in case of dispute). */
	public void resumeNormalPlay(int tableId) {}

	/** opponent joined game */
	public void opponentJoinedGame(int tableId, Player who) {}

	/** signals that the last move is illegal, and that it is still the user's turn. */
	public void illegalLastMove(int tableId, Coordinates coor) {}

	/** server sends a new list of players at table to client. */
	public void refreshPlayersAtTableList(int tableId, ArrayList players) {}

	/** signals that the opponent has completed their move. */
	public void opponentPlacedStone(int tableId, Board newBoard, Coordinates lastMove) {}

	/** signals that opponent passed on last move. */ 
	public void opponentPassed(int tableId) {}

	/** signals that opponent has surrendered. */
	public void opponentSurrenders(int tableId) {}

// post game negotiation
	/** server recieved to passes in a row; begin post-game negotiation. */
	public void startPostGameNegotiation(int tableId) {}
        
        /** opponent toggled stones dead */
        public void opponentToggledStones(int tableId, Board scoreBoard) {}

// Game Over / scoring
	/** game is over. (well, except for the post-negotiation negotiation ;) */
	public void gameOver(int tableId, Board gameBoard, Board scoreBoard, int blackScore, int whiteScore) {}
	
// chat
	/** other player has sent a chat message to user's table */
	public void inTableChat(int tableId, Player from, String message) {}

	/** other player has sent a private message. */
	public void inPlayerChat(Player from, String message) {}

	/** other player has sent chat message to everyone on server. */
	public void inServerChat(Player from, String message) {}
	
// watching games
	/** server sent a new table list. */
	public void refreshTableList(ArrayList tableList) {}
        
// misc
	/** server has sent a current board. */
	public void refreshBoard(int tableId, Board newBoard) {}

	/** server has sent a new list of players. */
	public void refreshServerPlayerList(ArrayList players) {}

	/** server has sent current opponent clock. */
	public void refreshClocks(int tableId, int secondsOnBlack, int secondsOnWhite) {}

	/** client has lost connection to server. */
	public void lostConnectionToServer(String message) {}
        
        /** refresh score board */
        public void refreshScoreBoard(int tableId, Board scoreBoard) {}
	
	/** sent if client recieves a malformed command */
	public void recievedMalformedCommand(GoServerCommand cmd) 
	{
		System.out.println("DumbUserInterface: malformed command " + cmd);
	}
}
