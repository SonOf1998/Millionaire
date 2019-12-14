import java.io.*;

public class Player implements Serializable
{
	private static final long serialVersionUID = 1L;

	private boolean phone_on;
	private boolean half_on;
	private boolean audience_on;
	
	private String name;
	private int level;
	private int time;
	
	
	public Player(String name_)
	{		
		name = name_;
		level = 0;
		time = 0;
		
		phone_on = true;
		half_on = true;
		audience_on = true;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void incrementLevel()
	{
		level += 1;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getTime()
	{
		return time;
	}
	
	public void addTime(int delta_t)
	{
		time += delta_t;
	}
	
	public boolean hasLifelinePhone()
	{
		return phone_on;
	}
	
	public void disableLifelinePhone()
	{
		phone_on = false;
	}
	
	public boolean hasLifelineHalf()
	{
		return half_on;
	}
	
	public void disableLifelineHalf()
	{
		half_on = false;
	}
	
	public boolean hasLifelineAudience()
	{
		return audience_on;
	}
	
	public void disableLifelineAudience()
	{
		audience_on = false;
	}
}
