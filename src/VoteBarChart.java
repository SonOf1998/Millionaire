import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.*;

import javax.swing.*;
import javax.swing.text.*;


//Kész.
public class VoteBarChart extends JDialog
{
	private Question question;
	
	public VoteBarChart(JFrame underLayer, Question q)
	{
		setSize(220, 200);
		setUndecorated(true);
		setLocation(underLayer.getX() + 43, underLayer.getY() + 110);
		
		question = q;
		
		initComponents();
		

		Thread t = new Thread(new Runnable() 
		{   @Override
			public void run() 
			{
				try 
				{
					Thread.sleep(6000);
					dispatchEvent(new WindowEvent(VoteBarChart.this, WindowEvent.WINDOW_CLOSING));	
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
			}
		});
		t.start();
	}
	
	private void initComponents()
	{
		setLayout(new BorderLayout());
		JTextPane title_bar = new JTextPane();
		title_bar.setText("Szavazás Eredménye");
		title_bar.setPreferredSize(new Dimension(220, 25));
		title_bar.setEditable(false);
		title_bar.setHighlighter(null);
		title_bar.setBackground(new Color(255, 204, 153));
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		title_bar.setParagraphAttributes(sas, true);
		
		MenuButton exit = new MenuButton("OK");
		exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				dispatchEvent(new WindowEvent(VoteBarChart.this, WindowEvent.WINDOW_CLOSING));				
			}
		});

		CenterPanel cp = new CenterPanel();
		
		add(title_bar, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		add(exit, BorderLayout.SOUTH);
	}
	
	private class CenterPanel extends JPanel
	{
		private double[] percentages;
		
		CenterPanel()
		{
			percentages = new double[] {0, 0, 0, 0};
			simulateVote();
			initInnerComponents();
		}
		
		private void simulateVote()
		{
			Random r = new Random();
			
			percentages[question.getSolution() - 'A'] = BigDecimal.valueOf((r.nextInt(9800 - 9200) + 9200 - (question.getLevel() / 10.0) * (r.nextInt(4900 - 4500) + 4500)) / 100.0).setScale(2, RoundingMode.HALF_DOWN).doubleValue(); 
			
			double sum = DoubleStream.of(percentages).sum();
			while(sum != 100.0)
			{
				for(int i = 0; i < 4; i++)
				{					
					if(i != question.getSolution() - 'A')
					{
						percentages[i] = BigDecimal.valueOf((r.nextInt(3333) - ((14 - question.getLevel()) / 10.0) * (r.nextInt(200) + 1555)) / 100.0).setScale(2, RoundingMode.HALF_DOWN).doubleValue(); 
						
						while(percentages[i] < 0)
						{
							percentages[i] = BigDecimal.valueOf((r.nextInt(3333) - ((14 - question.getLevel()) / 10.0) * (r.nextInt(200) + 1555)) / 100.0).setScale(2, RoundingMode.HALF_DOWN).doubleValue(); 
						}
					}
				}
				
				sum = DoubleStream.of(percentages).sum();
			}
		}
		
		private void initInnerComponents()
		{
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridheight = 3;
			gbc.gridwidth = 4;
			gbc.insets = new Insets(-50, 0, 0, 0);
			
			BarChartPanel bcp = new BarChartPanel();
			add(bcp, gbc);
			
			gbc.gridwidth = 1;
			gbc.insets = new Insets(100, 5, 0, 5);
			JLabel jl[] = new JLabel[4];
			for(int i = 0; i < 4; i++)
			{
				gbc.gridx = i;
				jl[i] = new JLabel(String.format("<HTML><div style=\"width: 30px;\" align=\"center\">%s<br>%.2f%%</div></HTML>", (char)('A' + i), percentages[i]));
				add(jl[i], gbc);
			}
		}
		
		
		
		private class BarChartPanel extends JPanel
		{
			BarChartPanel()
			{
				setPreferredSize(new Dimension(180, 180));
				repaint();	
			}
			
			@Override
			public void paintComponent(Graphics g)
			{
				g.setColor(Color.BLUE);
				g.fillRect(3, 105 - (int)percentages[0], 25, (int)percentages[0]);
				g.fillRect(53, 105 - (int)percentages[1], 25, (int)percentages[1]);
				g.fillRect(103, 105 - (int)percentages[2], 25, (int)percentages[2]);
				g.fillRect(153, 105 - (int)percentages[3], 25, (int)percentages[3]);
			}
		}
	}
}
