package game;

public class GoGameOptions
{
	int size;
	double komi;
	int handicap;

	public GoGameOptions(int in_size, double in_komi, int in_handicap)
	{
		size = in_size;
		komi = in_komi;
		handicap = in_handicap;
	}

	public int getSize()
	{
		return size;
	}

	public double getKomi()
	{
		return komi;
	}

	public int getHandicap()
	{
		return handicap;
	}
}
