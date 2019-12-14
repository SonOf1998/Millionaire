import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameButton extends JButton 
{	
	public GameButton()
	{		
		setPreferredSize(new Dimension(350, 80));
		setFocusPainted(false);
		setOpaque(false);
		setBackground(new Color(0, 0, 0));
		setForeground(new Color(255, 255, 255));
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent me) {
				setBackground(new Color(0, 100, 0));
		    }

			@Override
		    public void mouseExited(MouseEvent me) {
				setBackground(new Color(0, 0, 0));
		    }
		});

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
}
