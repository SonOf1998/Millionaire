import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


//Kész.
public class MenuButton extends JButton
{
	public MenuButton(String title)
	{
		super(title);
		setFocusPainted(false);
		setOpaque(false);
		setBackground(new Color(100, 100, 100));
		setForeground(new Color(255, 255, 255));
		
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				setBackground(new Color(100, 140, 120));
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				setBackground(new Color(100, 100, 100));
		    }
		});

		this.repaint();
		
	}
	
	@Override
	public void paint(Graphics g) 
	{       
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
	}
}


