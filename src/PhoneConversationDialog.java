import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.*;

public class PhoneConversationDialog extends JDialog
{
	private Question question;
	
	public PhoneConversationDialog(JFrame underLayer, Question q)
	{		
		setUndecorated(true);
		setSize(600, 150);
		setLocation(underLayer.getX() + 280, underLayer.getY() + 150);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		
		question = q;
		initComponents();
		
		Thread t = new Thread(new Runnable() 
		{	
			@Override
			public void run() 
			{
				try
				{
					Thread.sleep(8000);
					dispatchEvent(new WindowEvent(PhoneConversationDialog.this, WindowEvent.WINDOW_CLOSING));	
				}
				catch(InterruptedException e)
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
		
		JTextPane jtp = new JTextPane();
		jtp.setEditable(false);
		jtp.setBackground(Color.BLACK);
		jtp.setForeground(Color.WHITE);
		
		ArrayList<String> dialogues = new ArrayList<>();
		
		if(question.getLevel() >= 1 - 1)
		{			
			dialogues.add(String.format("%n%n%nEg�szen biztosan a(z) %s v�lasz a helyes.", question.getSolution()));
			dialogues.add(String.format("%n%n%nA(z) %s-t jel�ld!! 100%%, hogy az a helyes.", question.getSolution()));
			dialogues.add(String.format("%n%n%nBiztosan a(z) %s v�laszt jel�ln�m a helyedben.", question.getSolution()));
		}
		if (question.getLevel() >= 9 - 1)
		{
			switch(question.getSolution())
			{
				case 'A':
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az A vagy a B."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az A vagy a C."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az A vagy a D."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg az A a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg az A a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a D a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					break;
				case 'B':
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az B vagy az A."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az B vagy a C."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az B vagy a D."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a B a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a B a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a C a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					break;
				case 'C':
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az C vagy a B."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az C vagy az A."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy az C vagy a D."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a C a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a C a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg az A a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					break;
				case 'D':
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy a D vagy a B."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy a D vagy a C."));
					dialogues.add(String.format("%n%n%nPontosan nem tudom, de vagy a D vagy az A."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a D a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a D a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					dialogues.add(String.format("%n%n%nCsak tippelni tudok, de val�sz�n�leg a C a helyes. De mondom, csak tippelek, egy�ltal�n nem biztos."));
					break;
			}
		} 
		if(question.getLevel() >= 12)
		{
			dialogues.add(String.format("%n%n%nG�z�m sincs. Nem akarok h�lyes�get mondani. :("));
			switch(question.getSolution())
			{
				case 'A':
					dialogues.add(String.format("%n%n%nNem tudom, de a B v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de a C v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de a D v�laszt kiz�rhatod."));
					break;
				case 'B':
					dialogues.add(String.format("%n%n%nNem tudom, de az A v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de a C v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de a D v�laszt kiz�rhatod."));
					break;
				case 'C':
					dialogues.add(String.format("%n%n%nNem tudom, de a B v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de az A v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de a D v�laszt kiz�rhatod."));
					break;
				case 'D':
					dialogues.add(String.format("%n%n%nNem tudom, de a B v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de a C v�laszt kiz�rhatod."));
					dialogues.add(String.format("%n%n%nNem tudom, de az A v�laszt kiz�rhatod."));
					break;
			}			
		}

		jtp.setText(dialogues.get(new Random().nextInt(dialogues.size())));
		
		
		StyledDocument sd = jtp.getStyledDocument();
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		sd.setParagraphAttributes(0, sd.getLength(), sas, false);
		
		MenuButton exit = new MenuButton("OK");
		exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				dispatchEvent(new WindowEvent(PhoneConversationDialog.this, WindowEvent.WINDOW_CLOSING));				
			}
		});
		
		add(jtp, BorderLayout.CENTER);
		add(exit, BorderLayout.SOUTH);
	}
}
