package network.general;
import java.util.*;
import java.net.*;
import java.io.*;

// untested

public class NetLoopThread extends Thread
{
	private Socket socket;
        private GoServerCommandQueue outQ;
        private CommandManager cMan;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private boolean stayConnected;
        private GoServerCommand tmpCommand;

	public void run()
        {
        	while (stayConnected) 
                {
			tmpCommand = null;
		
                	// send command from out queue
                        tmpCommand = outQ.pop();
                        if (tmpCommand != null)
                        {
System.out.println("NetLoopThread.run(): sending command.");
                        	try
                                {
                        		out.writeObject(tmpCommand);
					out.flush();
                                }
                                catch (IOException e) 
				{ 
					System.err.println("NetLoopThread.run() 0: " + e);
					stayConnected = false;
				}
                        }
                        
			tmpCommand = null;
			
                        // give any incomming commands to command manager
                        try
			{
				tmpCommand = (GoServerCommand) in.readObject();
System.out.println("NetLoopThread.run(): executing command.");
				cMan.execute(tmpCommand);
			}
			catch (OptionalDataException e) {}
			catch (EOFException e)
			{
				//if (stayConnected)
				//	System.err.println("NetLoopThread.run() 1: " + e);
			}
			catch (Exception e) { System.err.println("NetLoopThread.run() 2: " + e); }
                }
	
		try
		{
			in.close();
			out.close();
		}
		catch (IOException e) { System.err.println("NetLoopThread.run() 3: " + e); }
        }
        
        public void disconnect()
        {
        	stayConnected = false;
        }
        
        public boolean isConnected()
        {
        	return stayConnected;
        }
        
        public NetLoopThread(Socket s, GoServerCommandQueue q, CommandManager c, boolean isServer)
        {
        	socket = s;
                outQ = q;
                cMan = c;
		
		OutputStream outStream;
		InputStream inStream;
		BufferedOutputStream bufOutStream;
		BufferedInputStream bufInStream;
                
                try	// ObjectInputStream() hangs until it recieves header from other side!
                {
			if (isServer)	// order depends on whether this is a server or a client!
			{
				in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				out.flush();
			}
			else
			{
                		out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				out.flush();
                		in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			}
			
                        stayConnected = true;
                }
                catch (IOException e) { System.err.println("NetLoopThread: " + e); }

                if (stayConnected)
                	this.start();
        }
}
