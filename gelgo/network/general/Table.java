package network.general;

import java.io.Serializable;

public class Table implements Serializable, Cloneable{

	private double komi;
	private int handicap;
	private int size;
	private int time;
	private String p1;
	private String p2;
	private int id;

	// create a new table
	// if only one player is present, the other name is set to null
	// to indicate that the spot is open
	public Table(double komi, int handicap, int size, int time, String p1, String p2, int id){
		this.komi = komi;
		this.handicap = handicap;
		this.size = size;
		this.time = time;
		this.p1 = p1;
		this.p2 = p2;
		this.id = id;
	}

	public Table(Table t){
		this(t.komi, t.handicap, t.size, t.time, t.p1, t.p2, t.id);
	}

	public Object clone(){
		return new Table(this);
	}

	public double getKomi(){ return komi; }
	public int getHandicap(){ return handicap; }
	public int getSize(){ return size; }
	public int getTime(){ return time; }
	public String getPlayer1(){ return p1; }
	public String getPlayer2(){ return p2; }
	public int getID(){ return id; }

	public void setPlayer1(String p1){ this.p1 = p1; }
	public void setPlayer2(String p2){ this.p2 = p2; }

	public boolean isWaiting(){ return (p1==null || p2==null); }
}
