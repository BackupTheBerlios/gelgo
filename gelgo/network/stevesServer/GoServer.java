package network.stevesServer;
import java.net.*;
import java.util.*;
import java.io.*;
import network.general.*;

public class GoServer implements CommandManagable
{
	private final static int PORT = 5555;
	private ServerSocket ss;
	private AcceptLoopThread acceptLoop;
	private ArrayList connections;
	private ArrayList gameMasters;
	private CommandManager cMan;
	private boolean interactive = true;
	private BufferedReader stdin = new BufferedReader( new InputStreamReader( System.in ) );
	
	public void newConnection(Socket s)
	{
		System.out.println("Recieved new connection.");
		connections.add(new Connection(s, this));
	}
	
	public void unknownCommand(GoServerCommand cmd)
	{
		System.err.println("invalid command: " + cmd.getCommand());
	}

	public void stop()
	{
		acceptLoop.disconnect(); // warn acceptLoop of disconnect
		
		try
		{
			ss.close();
			stdin.close();
		}
		catch (IOException e) {System.err.println("GoServer.stop(): " + e); }
		
		System.out.println("Thanks for playing Jello Go.");	
	}

	public void go() throws IOException
	{
		cMan = new CommandManager(this);
		ss = new ServerSocket(PORT);
		connections = new ArrayList();
		gameMasters = new ArrayList();
		acceptLoop = new AcceptLoopThread(this, ss);

		System.out.println("Welcome to the Jello Go Server!");
		
		if (interactive)
			grabCommand();
	}
	
	public void grabCommand()
	{
		String input = null;
		do
		{
			System.out.print("Jello > ");
			
			try
			{
				input = stdin.readLine();
				cMan.execute(new GoServerCommand(input, -1));
			} catch (IOException e) { System.err.println("GoServer.grabCommand(): " + e);}
			
		} while (input != null && !input.equals("stop"));
	}
	
	public void grabCommand(CommandManagable fromWho, GoServerCommand cmd)
	{
		cMan.execute(cmd);
	}

	public GoServer(boolean withPrompt) // most everything is setup in go()
	{
		interactive = withPrompt;
	}

//------------------------------------------------
// server commands (one so far)
// -----------------------------------------------
	public void stop(GoServerCommand cmd)
        {
                this.stop();
        }
}
