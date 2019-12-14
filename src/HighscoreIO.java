import java.io.*;
import java.util.*;

public class HighscoreIO 
{
	private File f;
	private int FLAG;
	
	public static final int LOST = 0;			// játék elvesztésekor kerülünk ide
	public static final int STOP_OR_WON = 1;	// megálláskor vagy fõnyeremény esetén kerülünk ide
	
	public HighscoreIO()
	{
		f = new File("resources/millionaire.hs");		
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public HighscoreIO(int FLAG_)
	{
		f = new File("resources/millionaire.hs");		
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		FLAG = FLAG_;
	}
	
	public void save(Player p)
	{
		try
		{			
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Cryptor c = new Cryptor();
			String line;
			
			int playerPoint;
			
			
			
			ArrayList<String> list = new ArrayList<>();
			while((line = br.readLine()) != null)
			{
				list.add(c.decrypt(line));
			}
			br.close();
			
			if(FLAG == LOST)
			{
				playerPoint = PrizeList.fixed_prizes[p.getLevel()] - p.getTime();
				list.add(p.getName() + "\t" + PrizeList.fixed_prizes[p.getLevel()] + "\t" + p.getTime() + "\t" + playerPoint);
			}
			else if(FLAG == STOP_OR_WON)
			{
				playerPoint = PrizeList.prizes[p.getLevel() - 1] - p.getTime();
				list.add(p.getName() + "\t" + PrizeList.prizes[p.getLevel() - 1] + "\t" + p.getTime() + "\t" + playerPoint);
			}
			
			Collections.sort(list, Comparator.comparing(s -> -Integer.parseInt(s.split("\t")[3])));
			
			
			FileOutputStream fos = new FileOutputStream(f);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for(int i = 0; i <  Math.min(list.size(), 10); i++)
			{
				bw.write(c.encrypt(list.get(i)));
				bw.newLine();
			}
			
			bw.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public String[] getTop10Array()
	{
		String[] res = new String[10];
		try
		{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			Cryptor c = new Cryptor();
			String line;
			int i = 0;
			
			while((line = br.readLine()) != null)
			{
				String[] attrs = c.decrypt(line).split("\t");
				res[i++] = i + "\t" + attrs[0] + "\t" + attrs[1] + "\t" + attrs[2];
			}
			br.close();
			
			for(int j = i; j < 10; j++)
			{
				res[j] = "";
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}	
		
		return res;
	}
}
