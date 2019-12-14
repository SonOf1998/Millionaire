import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

//Kész
public class PrizeList extends JPanel
{
	public static final int[] prizes 		 = {5000, 10000, 25000, 50000, 100000, 200000, 300000, 500000, 800000, 1500000, 3000000, 5000000, 10000000, 20000000, 40000000};
	public static final int[] fixed_prizes   = {0,	  0,     0,		0,	   0,	   100000, 100000, 100000, 100000, 100000,  1500000, 1500000, 1500000,  1500000,  1500000};
	
	ArrayList<JLabel> cells;
	private int level;
	
	public PrizeList()
	{
		level = 0;
		cells = new ArrayList<>();
		
		setPreferredSize(new Dimension(100, 300));
		setOpaque(false);
		setLayout(new FlowLayout());
		
		
		
		
		for(int i = 15 - 1; i >= 0; i--)
		{
			JLabel jl = new JLabel(intGroupedByThreeDigits(prizes[i]), SwingConstants.CENTER);
			jl.setPreferredSize(new Dimension(100, 14));
			jl.setOpaque(false);
			
			if(i == 14 || i == 9 || i == 4)
			{
				Font f = jl.getFont();
				jl.setFont(new Font(f.getFontName(), Font.BOLD, f.getSize()));
				
				jl.setForeground(Color.YELLOW);
			}
			else
			{
				jl.setForeground(Color.WHITE);
			}
			
			cells.add(jl);
		}
		
		for(int i = 0; i < 15; i++)
		{
			add(cells.get(i));
		}
	}
	
	public static String intGroupedByThreeDigits(int num)
	{
		if(num == 0)
		{
			return "0";
		}
		
		int n = (int) (Math.floor(Math.log10(num)) + 1);
		int res_l = n + (n - 1) / 3; 
		
		char resCharArray[] = new char[res_l];
		
	    int j = 0;
	    for(int i = res_l - 1; i >= 0; i--)
	    {
	        if((res_l - i) % 4 == 0)
	        {
	        	resCharArray[i] = ' ';
	        } else {
	        	resCharArray[i] = (char) ((num % Math.pow(10, j + 1)) / Math.pow(10, j) + '0');
	            j++;
	        }
	    }

	    return String.valueOf(resCharArray);
	}
	
	public void setLevel(int level_)
	{
		level = level_;
		
		for(int i = 0; i < 15; i++)
		{
			cells.get(i).setOpaque(false);
			cells.get(i).setBackground(null);
			if(14 - i == level)
			{
				cells.get(i).setOpaque(true);
				cells.get(i).setBackground(Color.BLUE);
			}
		}		
	}
	
	
	
}
