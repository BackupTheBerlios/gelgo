package network.general;
import java.io.*;
import java.util.*;
import java.net.*;
import core.*;

// this is just a rough draft of the player class  --steve
// added socket to know how to talk to this player, make
// sure socket is not serialized when player is saved and
// restored --anthony

public class Player implements Serializable
{
	private String name;
	private Color playerColor;
	private int rank;
	private boolean acceptOptions = false;
	private Socket theSocket;
	
	private ArrayList savedGames; // Should hold SavedGame objects

	public Player(String in_name, Color in_color, int in_rank, Socket s)
	{
		name = in_name;
		playerColor = in_color;
		rank = in_rank;
		savedGames = new ArrayList();
		theSocket = s;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String s)
	{
		name = s;
	}

	public Color getColor()
	{
		return playerColor;
	}

	public void setColor(Color c)
	{	
		playerColor = c;
	}

	public int getRank()
	{
		return rank;
	}

	public void setRank(int i)
	{
		rank = i;
	}

	public void saveGame(GoGame g, String s)
	{
		savedGames.add(new SavedGame(g, s));
	}

	public ArrayList getGameList()
	{
		ArrayList newList;
		
		if (savedGames.isEmpty())
			return null;
		
		newList = new ArrayList();

		for (int i = 0; i < savedGames.size(); i++)
			newList.add(i, ((SavedGame)savedGames.get(i)).getDescription());

		return newList;
	}

	public void removeGame(int i)
	{
		savedGames.remove(i);
	}

	public GoGame getGame(int i)
	{
		return ((SavedGame)savedGames.get(i)).getGame();
	}

	public void acceptNegotiationOptions()
	{
		acceptOptions = true;
	}

	public Socket getSocket()
	{
		return theSocket;
	}
}
