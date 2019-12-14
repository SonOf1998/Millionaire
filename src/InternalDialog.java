import java.awt.*;

import javax.swing.*;

// Kész.
public class InternalDialog extends JDesktopPane
{
	private final int WIDTH = 1120;
	private final int HEIGHT = 630;
	private int DIALOG_WIDTH;
	private int DIALOG_HEIGHT;	
	
	public JInternalFrame jif;
	
	public InternalDialog(String title, int width, int height)
	{
		DIALOG_WIDTH = width;
		DIALOG_HEIGHT = height;
				
		initComponents(title);
	}
	
	private void initComponents(String title)
	{
		setOpaque(false);
		jif = new JInternalFrame(title);
		jif.setResizable(false);
		jif.setIconifiable(false);
		jif.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		jif.setLocation((WIDTH - DIALOG_WIDTH) / 2, (HEIGHT - DIALOG_HEIGHT) / 2);
		jif.setLayout(new GridBagLayout());
		jif.setVisible(true);
		add(jif);
	}
	
	public void setTitle(String title)
	{
		setTitle(title);
	}
	
	
	public void addComponent(Component c, GridBagConstraints gbc)
	{
		jif.add(c, gbc);
	}
}
