package network.stevesServer;
import java.io.*;

public class StartServer
{
	public static void main(String args[]) throws IOException
	{
		GoServer server = new GoServer(true);
		server.go();
	}
}
