import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

class Game extends JFrame
{
	// 16:9 resolution
	private final int WIDTH = 1120;
	private final int HEIGHT = 630;
	private final int NAME_DIALOG_WIDTH = 384;
	private final int NAME_DIALOG_HEIGHT = 216;
	
	private MusicPlayer bgm;	
	private GamePanel gamePanel;	
	private Player player;
	private QuestionList ql;
	
	private Menu main_menu;
	private long gameBeginTime;
	
	public Game(Menu m)
	{		
		main_menu = m;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());	
		setContentPane(new JLabel(new ImageIcon("resources/img/game_bg.png")));
		setLayout(new BorderLayout());
		setSize(WIDTH, HEIGHT);
		
		add(new ExitPanel(true), BorderLayout.NORTH);
		
		InternalDialog id = new InternalDialog("Válasszon játékosnevet!", NAME_DIALOG_WIDTH, NAME_DIALOG_HEIGHT);
		JTextField nameInput = new JTextField(10);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		id.addComponent(nameInput, gbc);
		JButton OK = new JButton("OK");
		gbc.gridy = 2;
		gbc.insets = new Insets(20, 0, 0, 0);
		id.addComponent(OK, gbc);
		
		OK.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{				
				if(!nameInput.getText().isEmpty() && !nameInput.getText().contains("_"))
				{
					player = new Player(nameInput.getText());
					id.setVisible(false);
					gamePanel = new GamePanel(Game.this);
					initGameComponents();
				}
			}
		});	
		
		
		add(id, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
		setVisible(true);
		
	}
	
	Game(Menu m, Player savedPlayer)
	{	
		main_menu = m;
		player = savedPlayer;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());	
		setContentPane(new JLabel(new ImageIcon("resources/img/game_bg.png")));
		setLayout(new BorderLayout());
		setSize(WIDTH, HEIGHT);
		
		add(new ExitPanel(true), BorderLayout.NORTH);
		gamePanel = new GamePanel(this);
		initGameComponents();
		chooseMusic(player.getLevel());
		
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
		setVisible(true);
	}
	
	public void stepGame()
	{
		player.incrementLevel();
		Question q = ql.getQuestion(player.getLevel());
		gamePanel.displayQuestion(q);
		switchMusicIfNeeded(player.getLevel());				
	}
	
	private void initGameComponents()
	{
		gameBeginTime = System.nanoTime();
		ql = new QuestionList();
		ql.fillUpFromFile(new File("resources/questions.txt"));
		
		Question q = ql.getQuestion(player.getLevel());
		gamePanel.displayQuestion(q);
		switchMusicIfNeeded(player.getLevel());		
		
		

		add(gamePanel, BorderLayout.SOUTH);
	}
	
	private void switchMusicIfNeeded(int level)
	{		
		if(level == 0)
		{
			bgm = new MusicPlayer("resources/wav/50k.wav", true);
		}
		
		if(level == 4)
		{
			if(bgm.isPlaying())
			{
				bgm.kill();
			}
			bgm = new MusicPlayer("resources/wav/300k.wav", true);
		}
		
		if(level == 7)
		{
			if(bgm.isPlaying())
			{
				bgm.kill();
			}
			bgm = new MusicPlayer("resources/wav/800k.wav", true);
		}
		
		if(level == 9)
		{
			if(bgm.isPlaying())
			{
				bgm.kill();
			}
			bgm = new MusicPlayer("resources/wav/3m.wav", true);
		}
		
		if(level == 13)
		{
			if(bgm.isPlaying())
			{
				bgm.kill();
			}
			bgm = new MusicPlayer("resources/wav/20m.wav", true);
		}
		
		if(level == 14)
		{
			if(bgm.isPlaying())
			{
				bgm.kill();
			}
			bgm = new MusicPlayer("resources/wav/40m.wav", true);
		}
	}
	
	private void chooseMusic(int level)
	{
		if(level < 4)
		{
			bgm = new MusicPlayer("resources/wav/50k.wav", true);
		}
		
		if(4 <= level && level < 7)
		{
			bgm = new MusicPlayer("resources/wav/300k.wav", true);
		}
		
		if(7 <= level && level < 9)
		{
			bgm = new MusicPlayer("resources/wav/800k.wav", true);
		}
		
		if(9 <= level && level < 13)
		{
			bgm = new MusicPlayer("resources/wav/3m.wav", true);
		}
		
		if(level == 13)
		{
			bgm = new MusicPlayer("resources/wav/20m.wav", true);
		}
		
		if(level == 14)
		{
			bgm = new MusicPlayer("resources/wav/40m.wav", true);
		}
	}
	
	
	public Player getPlayer()
	{		
		return player;
	}
	
	public Menu getMainMenu()
	{
		return main_menu;
	}
	
	public long getElapsedTime()
	{
		return System.nanoTime() - gameBeginTime;
	}
	
	@Override
	public void setVisible(boolean bool)
	{
		super.setVisible(bool);
		if(!bool && bgm != null)
		{
			bgm.kill();
		}
	}
}
