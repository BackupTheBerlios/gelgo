package network.general;

import java.lang.reflect.*;

// command string must be name of method to be executed!!!

public class CommandManager
{
	private CommandManagable obj;

	public void execute(GoServerCommand cmd)
	{
		Method meth;
		Class partypes[] = new Class[1];
		partypes[0] = cmd.getClass();
		GoServerCommand argList[] = new GoServerCommand[1];
		argList[0] = cmd;
	
		try
		{	
			meth = obj.getClass().getMethod(cmd.getCommand(), partypes);
			meth.invoke(obj, argList);
		}
		catch (NoSuchMethodException e)
		{
			obj.unknownCommand(cmd);
		}
		catch (Exception e) {System.err.println("CommandManager.execute(): " + e); }
	}

	public CommandManager(CommandManagable yourObj)
	{
		obj = yourObj;	
	}	
}
