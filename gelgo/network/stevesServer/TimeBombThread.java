package network.stevesServer;

public class TimeBombThread extends Thread
{
	int secondsLeft;
	boolean active = false;
	boolean defused = false;
	private Timed caller;
	private final static long ONE_SECOND = 1000;

	public int getTime()
	{
		return secondsLeft;
	}
	
	public void startClock()
	{
		active = true;	
	}

	public void stopClock()
	{
		active = false;
	}

	public void defuse()
	{
		defused = true;	
	}

	public void run()
	{
		while (secondsLeft > 0 && !defused)
		{
			try
			{
				this.sleep(ONE_SECOND);
			} catch (InterruptedException e) { }

			if (active)
				secondsLeft--;	
		}

		if (!defused)
			caller.timeIsUp();
	}
	
	public TimeBombThread(Timed job, int initTime)
	{
		secondsLeft = initTime;
		caller = job;

		this.start();
	}
}
