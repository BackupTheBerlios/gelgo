package network.general;
import java.io.*;
/* This will hold all of the command objects for the server */

public class GoServerCommand implements Serializable {

private String command = "";
private Object cData = null;
private int gID = 0; //Game ID

public GoServerCommand(String theCommand, int game){

	command = theCommand;
	cData = null;
	gID = game;

}

public GoServerCommand(String theCommand, Object theData, int game){

	command = theCommand;
	cData = theData;
	gID = game;

}

public String getCommand(){

	return command;
}

public Object getcData(){
	
	return cData;
}

public int getgID(){

	return gID;
}

public String toString()
{
	return command;
}

}
