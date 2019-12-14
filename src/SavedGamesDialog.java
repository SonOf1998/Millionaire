import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class SavedGamesDialog extends JDialog
{
	public static final int LOAD = 0;
    public static final int SAVE = 1;
	private int FLAG;
	
	private MenuButton[] slot;	
	private JFrame underLayer;
	
	private File workspace = new File(System.getProperty("user.dir")); // eclipse-workspace/Millionaire
	
	public SavedGamesDialog(int width, int height, int dialogType_, JFrame jf)
	{
		setSize(width, height);
		setModal(true);
		setUndecorated(true);
		setLocation(jf.getX() + 400, jf.getY() + 80);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 2, 1, 2, Color.WHITE));
		slot = new MenuButton[5];
		FLAG = dialogType_;
		underLayer = jf;
		
		initComponents();
	}
	
	private void initComponents()
	{
		//
		setLayout(new BorderLayout());
		JTextPane title_bar = new JTextPane();
		if(FLAG == LOAD)
		{
			title_bar.setText("Játék folytatása");			
		} 
		else
		{
			title_bar.setText("Játék mentése");
		}
		
		title_bar.setPreferredSize(new Dimension(300, 25));
		title_bar.setEditable(false);
		title_bar.setHighlighter(null);
		title_bar.setBackground(new Color(255, 204, 153));
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		title_bar.setParagraphAttributes(sas, true);
		
		JPanel slots = new JPanel();
		slots.setBackground(Color.BLACK);
		slots.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 0, 20, 0);
		
		for(int i = 0; i < 5; i++)
		{
			for(File file : workspace.listFiles())
			{
				if(file.isFile() && file.getName().substring(0, 2).equals("S" + i))
				{	
					String[] tmp = file.getName().split("_");
					slot[i].setText(String.format("%s : %d. kérdés", tmp[1], Integer.parseInt(tmp[2].replace(".ser", "")) + 1));
					if(FLAG == LOAD)
					{
						slot[i].addActionListener(new LoadButtonActionListener(file));
					}
					break;
				}
				else
				{
					slot[i] = new MenuButton("ÜRES");
				}
			}
			
			slot[i].setPreferredSize(new Dimension(200, 50));
			gbc.gridy = i;
			
			slots.add(slot[i], gbc);
		}		
		
		
		MenuButton exit = new MenuButton("Mégse");
		exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				dispatchEvent(new WindowEvent(SavedGamesDialog.this, WindowEvent.WINDOW_CLOSING));				
			}
		});
		
		add(title_bar, BorderLayout.NORTH);
		add(slots, BorderLayout.CENTER);
		add(exit, BorderLayout.SOUTH);
	}
	
	public void setSaveButtonActionListeners(Player p)
	{
		if(FLAG == SAVE)
		{
			for(int i = 0; i < 5; i++)
			{
				String txt = slot[i].getText();
				int index = i;
				
				slot[i].addActionListener(new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent ae) 
					{
						if(txt.equals("ÜRES"))
						{
							try
							{
								Game g = (Game)underLayer;
								p.addTime((int)(g.getElapsedTime() / 1000000000));
								FileOutputStream fos = new FileOutputStream("S" + index + "_" + p.getName() + "_" + (p.getLevel() + 1) + ".ser");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								p.incrementLevel(); // nem ugrunk a következõ kérdésre most rögtön, de ott szeeretnénk folytatni
								oos.writeObject(p);
								oos.close();
								
								for(Frame f : g.getFrames())
								{
									f.dispose();
								}
								g.setVisible(false);
								g.getMainMenu().setVisible(true);
							} 
							catch(IOException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							JDialog jd = new JDialog();
							jd.setModal(true);
							jd.setUndecorated(true);
							jd.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
							jd.setSize(400, 150);
							jd.getContentPane().setBackground(Color.BLACK);
							jd.setLocation(underLayer.getX() + 350, underLayer.getY() + 250);
							jd.setLayout(new GridBagLayout());
							
							GridBagConstraints gbc = new GridBagConstraints();
							gbc.gridx = 0;
							gbc.gridy = 0;
							gbc.gridwidth = 2;
							gbc.fill = GridBagConstraints.HORIZONTAL;
							
							JLabel jl = new JLabel("A bejegyzés nem üres. Szeretné felülírni?", SwingConstants.CENTER);
							jl.setForeground(Color.WHITE);
							jd.add(jl, gbc);
							
							gbc.fill = GridBagConstraints.NONE;
							gbc.gridwidth = 1;
							gbc.gridx = 0;
							gbc.gridy = 1;
							gbc.insets = new Insets(30, 20, 0, 20);
							MenuButton mb1 = new MenuButton("Mentés");
							jd.add(mb1, gbc);
							gbc.gridx = 1;
							MenuButton mb2 = new MenuButton("Mégse");
							jd.add(mb2, gbc);
							
							mb1.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent ae) 
								{
									try
									{
										for(File file : workspace.listFiles())
										{
											if(file.isFile() && file.getName().substring(0, 2).equals("S" + index))
											{
												file.delete();
											}
										}	
										
										Game g = (Game)underLayer;
										p.addTime((int)(g.getElapsedTime() / 1000000000));
										FileOutputStream fos = new FileOutputStream("S" + index + "_" + p.getName() + "_" + (p.getLevel() + 1) + ".ser");
										ObjectOutputStream oos = new ObjectOutputStream(fos);
										p.incrementLevel(); // nem ugrunk a következõ kérdésre most rögtön, de ott szeeretnénk folytatni
										oos.writeObject(p);
										oos.close();
										
										for(Frame f : g.getFrames())
										{
											f.dispose();
										}
										g.setVisible(false);
										g.getMainMenu().setVisible(true);
									} 
									catch(IOException e)
									{
										e.printStackTrace();
									}
								}
							});
							
							mb2.addActionListener(new ActionListener() 
							{
								@Override
								public void actionPerformed(ActionEvent ae) 
								{
									jd.setVisible(false);
								}
							});
							
							jd.setVisible(true);
						}
					}
				});
				
				
				
			}
		}
	}
	
	private class LoadButtonActionListener implements ActionListener
	{
		File serializedFile;

		public LoadButtonActionListener(File serializedFile_) 
		{
			serializedFile = serializedFile_;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			try
			{
				FileInputStream fis = new FileInputStream(serializedFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Player p = (Player)ois.readObject();
				ois.close();
				
				Menu m = (Menu) underLayer;
				m.setVisible(false);
				m.getMusicPlayer().kill();
				dispatchEvent(new WindowEvent(SavedGamesDialog.this, WindowEvent.WINDOW_CLOSING));	
				new Game((Menu)underLayer, p);				
			}
			catch(IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
		
	}
}
