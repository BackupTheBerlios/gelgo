package network.general;

public interface CommandManagable
{
	public void unknownCommand(GoServerCommand cmd);
	
	public void grabCommand(CommandManagable fromWho, GoServerCommand cmd);
}
