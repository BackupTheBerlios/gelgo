package network;
import java.io.*;
import java.net.*;
import network.client.*;
import network.stevesServer.*;

public class TestNetwork
{
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		ServerThread server;
		DumbUserInterface client;
		System.out.println("Testing Jello Go Network...");

		server = new ServerThread(); // starting server
		client = new DumbUserInterface();
		
		System.out.println("Attempting to connect to localhost...");
		client.client.login(InetAddress.getLocalHost(), "steve");
	
		while (!((GoClient)client.client).isLoggedIn())
		{
			// wait for connection
		}
		
		System.out.println("Log in was successful!");
		client.client.disconnect();
		server.shutdown();
		
		System.out.println("Ending test.");
	}
}

class ServerThread extends Thread 
{
	GoServer server;
	private static final boolean NON_INTERACTIVE = false;

	public ServerThread()
	{
		server = new GoServer(NON_INTERACTIVE);

		this.start();
	}
	
	public void run()
	{
		System.out.println("\tStarting server.");

		try
		{
			server.go();
		} catch (IOException e) {System.out.println("ServerThread.run(): " + e); }
	}

	public void shutdown()
	{
		server.stop();
	}
}
