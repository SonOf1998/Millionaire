import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.text.*;

class ExitPanel extends JPanel
{
	private final int WIDTH = 1120;
	private final int HEIGHT = 630;
	
	private boolean inGame;
	
	public ExitPanel(boolean inGame_)
	{		
		inGame = inGame_;
		initComponents();		
	}
	
	private void initComponents()
	{
		setOpaque(false);
		JTextPane title_bar = new JTextPane();
		title_bar.setText("Legyen Ön is Milliomos");
		title_bar.setPreferredSize(new Dimension(1050, 25));
		title_bar.setEditable(false);
		title_bar.setHighlighter(null);
		title_bar.setBackground(getBackground());
		
		
		title_bar.addMouseMotionListener(new MouseMotionListener() 
		{
			int X,Y;
			
			@Override
			public void mouseMoved(MouseEvent e) 
			{
				X = e.getX();
				Y = e.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				JFrame containingFrame = (JFrame) SwingUtilities.getWindowAncestor(ExitPanel.this);
	            containingFrame.setBounds(e.getXOnScreen() - X, e.getYOnScreen() - Y, WIDTH, HEIGHT);
			}
		});
			
		
		
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		title_bar.setParagraphAttributes(sas, true);
		

		JButton minimize_btn = new JButton();
		minimize_btn.setPreferredSize(new Dimension(25,25));	
		minimize_btn.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent ae) 
			{   
				JFrame containingFrame = (JFrame) SwingUtilities.getWindowAncestor(ExitPanel.this);
				containingFrame.setState(Frame.ICONIFIED);
			}
		});
		
		try {
			minimize_btn.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/m.png"))));
		} catch (IOException e) {
			// IMG missing?
			e.printStackTrace();
		}
		
		JButton exit_btn = new JButton();		
		exit_btn.setPreferredSize(new Dimension(25,25));		
		exit_btn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				if(inGame)
				{
					Game g = (Game)SwingUtilities.getWindowAncestor(ExitPanel.this);
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
					gbc.gridwidth = 3;
					gbc.fill = GridBagConstraints.HORIZONTAL;
					
					JLabel jl = new JLabel("<HTML><div align=\"center\">Biztosan kilép?<br>A játékot két kérdés között tudja menteni.</div></HTML>", SwingConstants.CENTER);
					jl.setForeground(Color.WHITE);
					jd.add(jl, gbc);
					
					gbc.fill = GridBagConstraints.NONE;
					gbc.gridwidth = 1;
					gbc.gridx = 0;
					gbc.gridy = 1;
					gbc.insets = new Insets(30, 15, 0, 15);
					MenuButton mb1 = new MenuButton("Kilépés");
					jd.add(mb1, gbc);
					gbc.gridx = 1;
					MenuButton mb2 = new MenuButton("Fõmenü");
					jd.add(mb2, gbc);
					gbc.gridx = 2;
					MenuButton mb3 = new MenuButton("Mégse");
					jd.add(mb3, gbc);
					
					mb1.addActionListener(new ActionListener() 
					{						
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							System.exit(0);					
						}
					});
					
					mb2.addActionListener(new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
							g.getMainMenu().setVisible(true);
							g.setVisible(false);
						}
					});
					
					mb3.addActionListener(new ActionListener() 
					{	
						@Override
						public void actionPerformed(ActionEvent ae) 
						{
							jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
						}
					});
					
					jd.setVisible(true);
				}
				else
				{
					System.exit(0);
				}				
			}
		});
		
		try {
			exit_btn.setIcon(new ImageIcon(ImageIO.read(new File("resources/img/x.png"))));
		} catch (IOException e) {
			// IMG missing?
			e.printStackTrace();
		}
		
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(title_bar);
		add(minimize_btn);
		add(exit_btn);
	}
}