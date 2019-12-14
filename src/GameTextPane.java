import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GameTextPane extends JTextPane
{
	public GameTextPane()
	{
		setOpaque(false);
		setEditable(false);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setPreferredSize(new Dimension(720, 100));
		
		StyledDocument sd = getStyledDocument();
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		sd.setParagraphAttributes(0, sd.getLength(), sas, false);
		
		repaint();
	}	
	
	
	@Override
	public void paint(Graphics g) 
	{       
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
	}
	
	@Override
	public void setText(String s)
	{
		super.setText("\n\n" + s);
	}
}
