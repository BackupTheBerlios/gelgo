package network.stevesServer;
import java.net.*;
import network.general.*;

public class Connection implements CommandManagable
{
	private ServerPlayer player = null;
	private GoServer server;
	private GoServerCommandQueue outQ;
	private CommandManager cMan;
	private NetLoopThread netLoop;

	public Connection(Socket socket, GoServer in_server)
	{
		server = in_server;
		outQ = new GoServerCommandQueue();
		cMan = new CommandManager(this);
		netLoop = new NetLoopThread(socket, outQ, cMan, true);
	}
	
	public void login(GoServerCommand cmd) // command
	{
		System.out.println("Connection.login(): got login command.");
		outQ.push(new GoServerCommand("loginOk", "wow, it worked!", -1));
	}
	
	public void unknownCommand(GoServerCommand cmd)
	{
		if (player == null)
			server.grabCommand(this, cmd);
		else
			player.grabCommand(this, cmd);
	}
	
	public void grabCommand(CommandManagable fromWho, GoServerCommand cmd)
	{
		
	}
}