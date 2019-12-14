import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;


//Kész.
class Menu extends JFrame
{
	// 16:9 resolution
	private final int WIDTH = 1120;
	private final int HEIGHT = 630;
	private final int HS_DIALOG_WIDTH = 400;
	private final int HS_DIALOG_HEIGHT = 225;
	public static final int SGD_DIALOG_WIDTH = 300;
	public static final int SGD_DIALOG_HEIGHT = 500;
	
	private MusicPlayer bgm;
	
	Menu()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		bgm = new MusicPlayer("resources/wav/theme.wav", true);
		initComponents();
	}		
	
	public void initComponents()
	{		
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon("resources/img/bg.png")));
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		
		ButtonPanel button_panel = new ButtonPanel();
		ExitPanel exit_panel = new ExitPanel(false);	
		
		
		add(exit_panel);
		add(button_panel);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
	}
	
	public MusicPlayer getMusicPlayer()
	{
		return bgm;
	}
	
	@Override
	public void setVisible(boolean bool)
	{
		super.setVisible(bool);
		if(!bgm.isPlaying() && bool)
		{
			bgm = new MusicPlayer("resources/wav/theme.wav", true);
			
		}
	}
	
	class ButtonPanel extends JPanel
	{
		public ButtonPanel()
		{
			setOpaque(false);
			setLayout(new GridBagLayout());
			
			
			MenuButton start_btn = new MenuButton("Új játék");
			start_btn.setFont(new Font("Arial", Font.PLAIN, 40));
			start_btn.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent ae) 
				{
					bgm.kill();
					Menu.this.setVisible(false);
					start_btn.setBackground(new Color(100, 100, 100));  // Megjegyzi az utoljára megnyomott gombot..
					new Game(Menu.this);				
				}
			});
			MenuButton load_btn = new MenuButton("Folytatás");
			load_btn.setFont(new Font("Arial", Font.PLAIN, 40));
			load_btn.addActionListener(new ActionListener() 
			{				
				@Override
				public void actionPerformed(ActionEvent ae) 
				{	
					SavedGamesDialog sgd = new SavedGamesDialog(SGD_DIALOG_WIDTH, SGD_DIALOG_HEIGHT, SavedGamesDialog.LOAD, Menu.this);
					sgd.setVisible(true);
				}
			});
			
			
			MenuButton highscore_btn = new MenuButton("Ranglista");	
			highscore_btn.setFont(new Font("Arial", Font.PLAIN, 40));
			highscore_btn.addActionListener(new ActionListener() 
			{	
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					//****
					Highscore hs = new Highscore(HS_DIALOG_WIDTH, HS_DIALOG_HEIGHT, Menu.this);
					hs.setVisible(true);
				}
			});
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.insets = new Insets(100, 30, 0, 0);
			c.gridx = 0;
			c.gridy = 0;
			c.fill = GridBagConstraints.BOTH;

			add(start_btn, c);
			
			c.insets = new Insets(50, 30, 0, 0);
			c.gridy = 1;
			add(load_btn, c);
			
			c.insets = new Insets(50, 30, 0, 0);
			c.gridy = 2;
			add(highscore_btn, c);
			
			JTextPane rules = new JTextPane();
			rules.setEditable(false);
			rules.setHighlighter(null);
			rules.setOpaque(false);
			c.insets = new Insets(20, 310, 0, 0);
			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 4;
			rules.setContentType("text/html");
			
			String rules_description = "";
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("resources/rules.html"), "UTF-8"));
				String line = "";
				while((line = br.readLine()) != null)
				{
					rules_description += line;
				}			
				rules.setText(rules_description);
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			add(rules, c);
			
			JSlider volume_slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
			volume_slider.setPreferredSize(new Dimension(40, 40));
			volume_slider.setMinorTickSpacing(5);
			volume_slider.setMajorTickSpacing(100);
			volume_slider.setPaintTicks(true);
			volume_slider.setPaintLabels(true);
			volume_slider.setOpaque(false);
			UIDefaults defaults = UIManager.getDefaults();    
			defaults.put("Slider.altTrackColor", Color.BLUE);
			
			volume_slider.addChangeListener(new ChangeListener() 
			{				
				@Override
				public void stateChanged(ChangeEvent e) 
				{
					MusicPlayer.setVolume(volume_slider.getValue() / 100.0f);
					bgm.saveVolumeSettings();
				}
			});
		    
			c.insets = new Insets(0, 300, 100, 0);
			c.gridx = 1;
			c.gridy = 5;
			c.gridwidth = 1;
			add(volume_slider, c);
		}	
	}
}
