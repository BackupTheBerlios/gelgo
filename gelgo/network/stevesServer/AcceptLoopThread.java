package network.stevesServer;
import java.io.*;
import java.net.*;

public class AcceptLoopThread extends Thread
{
	ServerSocket ss;
	GoServer server;
	boolean stayConnected = true;
	
	public AcceptLoopThread(GoServer in_server, ServerSocket listenSocket)
	{
		server = in_server;
		ss = listenSocket;
	
		this.setDaemon(true);
		this.start();
	}

	public void disconnect()
	{
		stayConnected = false;
	}

	public void run()
	{
		while (stayConnected)
		{
System.out.println("AcceptLoopThread.run(): inside while loop.");
			try
			{
				server.newConnection(ss.accept());
			}
			catch (IOException error1)
			{
				if (stayConnected)
					System.err.println("AcceptLoopThread.run(): 1 " + error1);
			
				stayConnected = false;
				
				try
				{
					ss.close();
				}
				catch (IOException error2) {System.err.println("AcceptLoopThread.run(): 2 " + error2); }
			}
		}
	}
}
