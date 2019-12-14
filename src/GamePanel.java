import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel
{
	private GameButton[] gb;
	private GameTextPane gta;
	private PrizeList pl;
	private MenuButton stop;
	
	private JButton lifeline_phone;
	private JButton lifeline_half;
	private JButton lifeline_audience;
	
	
	private Question q;
	private Game g;
	
	
	private boolean any_lifeline_used_at_level;
	
	
	
	public GamePanel(Game g_)
	{
		setOpaque(false);
		setLayout(new GridBagLayout());
		
		g = g_;
		
		initComponents();
	}
	
	private void initComponents()
	{
		gta = new GameTextPane();
		gb = new GameButton[4];
		
		for(int i = 0; i < 4; i++)
		{
			gb[i] = new GameButton();
		}
		
		
		pl = new PrizeList();
		
		lifeline_audience = new JButton();
		lifeline_audience.setOpaque(false);
		lifeline_audience.setContentAreaFilled(false);
		lifeline_audience.setBorderPainted(false);
		lifeline_audience.setFocusPainted(false);
		lifeline_half = new JButton();
		lifeline_half.setOpaque(false);
		lifeline_half.setContentAreaFilled(false);
		lifeline_half.setBorderPainted(false);
		lifeline_half.setFocusPainted(false);
		lifeline_phone = new JButton();
		lifeline_phone.setOpaque(false);
		lifeline_phone.setContentAreaFilled(false);
		lifeline_phone.setBorderPainted(false);
		lifeline_phone.setFocusPainted(false);
		try 
		{
			if(g.getPlayer().hasLifelineAudience())
			{
				lifeline_audience.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/ataon.png"))));
			}
			else
			{
				lifeline_audience.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/ataoff.png"))));
			}
			
			if(g.getPlayer().hasLifelineHalf())
			{
				lifeline_half.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/5050on.png"))));
			}
			else
			{
				lifeline_half.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/5050off.png"))));
			}
			
			if(g.getPlayer().hasLifelinePhone())
			{
				lifeline_phone.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/pafon.png"))));
			}
			else
			{
				lifeline_phone.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/pafoff.png"))));
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 0, 20);
		
		add(gta, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 0, 5, 20);
		add(gb[0], gbc);
		gbc.gridx = 2;
		gbc.gridy = 3;
		add(gb[1], gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(gb[2], gbc);
		gbc.gridx = 2;
		gbc.gridy = 4;
		add(gb[3], gbc);
		gbc.insets = new Insets(0, -130, 0, 130);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 3;
		add(pl, gbc);
		gbc.insets = new Insets(0, -250, 230, 0);
		gbc.gridheight = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(lifeline_audience, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, -550, 230, 0);
		add(lifeline_half, gbc);
		gbc.gridx = 2;
		gbc.insets = new Insets(0, -1120, 230, 0);
		add(lifeline_phone, gbc);
		stop = new MenuButton("Megállás");
		gbc.insets = new Insets(150, -260, 0, 0);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(stop, gbc);
	}
	
	public void displayQuestion(Question q_)
	{
		q = q_;
		any_lifeline_used_at_level = false;
		gta.setText(q.getDescription());
		gb[0].setText("(A)    " + q.getA());
		gb[1].setText("(B)    " + q.getB());
		gb[2].setText("(C)    " + q.getC());
		gb[3].setText("(D)    " + q.getD());
		
		for(int i = 0; i < 4; i++)
		{
			removeClickListeners(gb[i]);
		}
		
		if(q.getLevel() > 1 - 1)
		{
			stop.setVisible(true);
			
			
			for(ActionListener al : stop.getActionListeners())
			{
				stop.removeActionListener(al);
			}
			
			stop.addActionListener(new ActionListener() 
			{				
				@Override
				public void actionPerformed(ActionEvent ae) 
				{					
					JDialog jd = new JDialog();
					jd.setModal(true);
					jd.setUndecorated(true);
					jd.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
					jd.setSize(400, 150);
					jd.getContentPane().setBackground(Color.BLACK);
					jd.setLocation(g.getX() + 350, g.getY() + 250);
					jd.setLayout(new GridBagLayout());
					
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.gridwidth = 2;
					gbc.fill = GridBagConstraints.HORIZONTAL;
					
					JLabel jl = new JLabel("Biztosan megáll?", SwingConstants.CENTER);
					jl.setForeground(Color.WHITE);
					jd.add(jl, gbc);
					
					gbc.fill = GridBagConstraints.NONE;
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = 1;
					gbc.insets = new Insets(30, 20, 0, 20);
					MenuButton mb1 = new MenuButton("Megáll");
					jd.add(mb1, gbc);
					gbc.gridx = 1;
					MenuButton mb2 = new MenuButton("Mégse");
					jd.add(mb2, gbc);

					mb1.addActionListener(new ActionListener() 
					{   
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
							
							JDialog stopPrizeDialog = new JDialog();
							stopPrizeDialog.setModal(true);
							stopPrizeDialog.setUndecorated(true);
							stopPrizeDialog.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
							stopPrizeDialog.setSize(400, 150);
							stopPrizeDialog.getContentPane().setBackground(Color.BLACK);
							stopPrizeDialog.setLocation(g.getX() + 350, g.getY() + 250);
							stopPrizeDialog.setLayout(new GridBagLayout());
							GridBagConstraints gbc = new GridBagConstraints();
							gbc.gridx = 0;
							gbc.gridy = 0;
							gbc.fill = GridBagConstraints.BOTH;
							JLabel jl = new JLabel(String.format("<HTML><div align=\"center\">Megálltál!<br>A nyereményed:<br><font size=\"6\">%s</font></div></HTML>", PrizeList.intGroupedByThreeDigits(PrizeList.prizes[q.getLevel() - 1])));
							jl.setForeground(Color.WHITE);
							stopPrizeDialog.add(jl, gbc);
							gbc.gridy = 1;
							gbc.insets = new Insets(20, 0, 0, 0);
							MenuButton OK = new MenuButton("OK");
							stopPrizeDialog.add(OK, gbc);
							OK.addActionListener(new ActionListener() 
							{					
								@Override
								public void actionPerformed(ActionEvent ae) 
								{
									stopPrizeDialog.dispatchEvent(new WindowEvent(stopPrizeDialog, WindowEvent.WINDOW_CLOSING));
									g.getMainMenu().setVisible(true);
									g.setVisible(false);				
								}
							});
							
							Player p = g.getPlayer();
							p.addTime((int)(g.getElapsedTime() / 1000000000));
							
							HighscoreIO hio = new HighscoreIO(HighscoreIO.STOP_OR_WON);
							hio.save(p);
							
							stopPrizeDialog.setVisible(true);
						}
					});
					
					
					mb2.addActionListener(new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));				
						}
					});
					
					jd.setVisible(true);
				}
			});
		}
		else
		{
			stop.setVisible(false);
		}
		
		setButtonCorrectness(q.getSolution());
		pl.setLevel(q.getLevel());
		
		lifeline_phone.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent ae) 
			{			
				if(!any_lifeline_used_at_level && g.getPlayer().hasLifelinePhone())
				{
					try 
					{
						lifeline_phone.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/pafoff.png"))));
						any_lifeline_used_at_level = true;
						g.getPlayer().disableLifelinePhone();
						
						new MusicPlayer("resources/wav/paf.wav", false);
						PhoneConversationDialog pcd = new PhoneConversationDialog((JFrame)SwingUtilities.getWindowAncestor(GamePanel.this), q);
						pcd.setVisible(true);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		lifeline_audience.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				if(!any_lifeline_used_at_level && g.getPlayer().hasLifelineAudience())
				{
					try 
					{
						lifeline_audience.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/ataoff.png"))));
						any_lifeline_used_at_level = true;
						g.getPlayer().disableLifelineAudience();

						new MusicPlayer("resources/wav/ata.wav", false);
						VoteBarChart vbc = new VoteBarChart((JFrame)SwingUtilities.getWindowAncestor(GamePanel.this), q);
						vbc.setVisible(true);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		lifeline_half.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				if(!any_lifeline_used_at_level && g.getPlayer().hasLifelineHalf())
				{
					try 
					{
						lifeline_half.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/5050off.png"))));
						any_lifeline_used_at_level = true;
						g.getPlayer().disableLifelineHalf();
						
						new MusicPlayer("resources/wav/5050.wav", false);
						
						Random r = new Random();
						int rInt = r.nextInt(4);
						int solInt = q.getSolution() - 'A';
						System.out.println(solInt);
						
						int removed1 = -1;
						int removed2 = -1;
						if(rInt == solInt)
						{
							while(rInt == solInt)
							{
								rInt = r.nextInt(4);
								removed1 = rInt;
							}
						}
						else
						{
							removed1 = rInt;
						}
						
						while(rInt == removed1 || rInt == solInt)
						{
							rInt = r.nextInt(4);
							removed2 = rInt;
						}
						
						gb[removed1].setText("");
						removeClickListeners(gb[removed1]);
						gb[removed2].setText("");
						removeClickListeners(gb[removed2]);
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	private void removeClickListeners(JButton jb)
	{
		for(ActionListener al : jb.getActionListeners())
		{
			jb.removeActionListener(al);
		}
	}
	
	private void setButtonCorrectness(char solution)
	{
		for(int i = 0; i < 4; i++)
		{
			if(solution == (char)('A' + i))
			{
				gb[i].addActionListener(new CorrectnessActionListener(true, gb[i]));
			}
			else
			{
				gb[i].addActionListener(new CorrectnessActionListener(false, gb[i]));
			}
		}
	}
	
	private class CorrectnessActionListener implements ActionListener
	{
		private boolean correct;
		private JButton jb;
		
		public CorrectnessActionListener(boolean correct_, JButton jb_) 
		{
			correct = correct_;
			jb = jb_;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) 
		{			
			if(correct)
			{
				new MusicPlayer("resources/wav/correct.wav", false);
				
				for(Frame f : g.getFrames())
				{
					if(f != g)
					{						
						f.setVisible(false);
					}
				}
				
				if(q.getLevel() == 15 - 1)	// szintek számolása 0-tól indul.
				{
					JDialog topPrizeDialog = new JDialog();
					topPrizeDialog.setModal(true);
					topPrizeDialog.setUndecorated(true);
					topPrizeDialog.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
					topPrizeDialog.setSize(400, 150);
					topPrizeDialog.getContentPane().setBackground(Color.BLACK);
					topPrizeDialog.setLocation(g.getX() + 350, g.getY() + 250);
					topPrizeDialog.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.fill = GridBagConstraints.BOTH;
					JLabel jl = new JLabel(String.format("<HTML><div align=\"center\">Gratulálunk a fõnyereményhez!<br>A nyereményed:<br><font size=\"6\">%s</font></div></HTML>", PrizeList.intGroupedByThreeDigits(PrizeList.prizes[q.getLevel()])));
					jl.setForeground(Color.WHITE);
					topPrizeDialog.add(jl, gbc);
					gbc.gridy = 1;
					gbc.insets = new Insets(20, 0, 0, 0);
					MenuButton OK = new MenuButton("OK");
					topPrizeDialog.add(OK, gbc);
					OK.addActionListener(new ActionListener() 
					{					
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							topPrizeDialog.dispatchEvent(new WindowEvent(topPrizeDialog, WindowEvent.WINDOW_CLOSING));
							g.getMainMenu().setVisible(true);
							g.setVisible(false);				
						}
					});
					
					Player p = g.getPlayer();
					p.incrementLevel();
					p.addTime((int)(g.getElapsedTime() / 1000000000));
					
					HighscoreIO hio = new HighscoreIO(HighscoreIO.STOP_OR_WON);
					hio.save(p);
					
					topPrizeDialog.setVisible(true);
				}
				else
				{	
					jb.setBackground(new Color(0, 100, 0));
					for(int i = 0; i < 4; i++)
					{
						removeClickListeners(gb[i]);
					}
					removeClickListeners(lifeline_audience);
					removeClickListeners(lifeline_half);
					removeClickListeners(lifeline_phone);
					
					////////////////////////////////////////////////////////////////////////////////////////
					////////////////////////////////////////////////////////////////////////////////////////
					JDialog jd = new JDialog();
					jd.setModal(true);
					jd.setUndecorated(true);
					jd.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
					jd.setSize(400, 150);
					jd.getContentPane().setBackground(Color.BLACK);
					jd.setLocation(g.getX() + 350, g.getY() + 250);
					jd.setLayout(new GridBagLayout());
					
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.gridwidth = 2;
					gbc.fill = GridBagConstraints.HORIZONTAL;
					
					JLabel jl = new JLabel("Folytatás?", SwingConstants.CENTER);
					jl.setForeground(Color.WHITE);
					jd.add(jl, gbc);
					
					gbc.fill = GridBagConstraints.NONE;
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = 1;
					gbc.insets = new Insets(30, 20, 0, 20);
					MenuButton mb1 = new MenuButton("Következõ kérdés");
					jd.add(mb1, gbc);
					gbc.gridx = 1;
					MenuButton mb2 = new MenuButton("Mentés és kilépés");
					jd.add(mb2, gbc);
					
					mb1.addActionListener(new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));	
							g.stepGame();
						}
					});
						
					mb2.addActionListener(new ActionListener() 
					{   
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							SavedGamesDialog sgd = new SavedGamesDialog(Menu.SGD_DIALOG_WIDTH, Menu.SGD_DIALOG_HEIGHT, SavedGamesDialog.SAVE, g);
							sgd.setSaveButtonActionListeners(g.getPlayer());
							sgd.setVisible(true);
						}
					});
					
					jd.setVisible(true);
					//////////////////////////////////////////////////////////////////////////////////////////
					//////////////////////////////////////////////////////////////////////////////////////////
				}
			}
			else
			{
				MusicPlayer mp = new MusicPlayer("resources/wav/wrong.wav", false);
				
				for(Frame f : g.getFrames())
				{
					if(f != g)
					{						
						f.setVisible(false);
					}
				}
				
				jb.setBackground(new Color(204, 82, 0));
				gb[q.getSolution() - 'A'].setBackground(new Color(0, 100, 0));
				for(int i = 0; i < 4; i++)
				{
					removeClickListeners(gb[i]);
				}
				removeClickListeners(lifeline_audience);
				removeClickListeners(lifeline_half);
				removeClickListeners(lifeline_phone);
				
				JDialog jd = new JDialog();
				jd.setModal(true);
				jd.setUndecorated(true);
				jd.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
				jd.setSize(400, 150);
				jd.getContentPane().setBackground(Color.BLACK);
				jd.setLocation(g.getX() + 350, g.getY() + 250);
				jd.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.BOTH;
				JLabel jl = new JLabel(String.format("<HTML><div align=\"center\">Ez sajnos rossz válasz volt.<br>A nyereményed:<br><font size=\"6\">%s</font></div></HTML>", PrizeList.intGroupedByThreeDigits(PrizeList.fixed_prizes[q.getLevel()])));
				jl.setForeground(Color.WHITE);
				jd.add(jl, gbc);
				gbc.gridy = 1;
				gbc.insets = new Insets(20, 0, 0, 0);
				MenuButton OK = new MenuButton("OK");
				jd.add(OK, gbc);
				OK.addActionListener(new ActionListener() 
				{					
					@Override
					public void actionPerformed(ActionEvent ae) 
					{
						jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
						g.getMainMenu().setVisible(true);
						mp.kill();
						g.setVisible(false);				
					}
				});
				
				Player p = g.getPlayer();
				p.addTime((int)(g.getElapsedTime() / 1000000000));
				
				HighscoreIO hio = new HighscoreIO(HighscoreIO.LOST);
				hio.save(p);
				
				jd.setVisible(true);
			}			
		}		
	}
}
