package network.general;
import java.io.*;
import core.*;

// just a rough draft of a SavedGame class

public class SavedGame implements Serializable
{
	GoGame game;
	String description;

	public GoGame getGame()
	{
		return game;
	}

	public String getDescription()
	{
		return description;
	}

	public SavedGame(GoGame g, String s)
	{
		game = g;
		description = s;
	}
}
