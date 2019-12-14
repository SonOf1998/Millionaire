import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

public class Highscore extends JDialog
{
	public Highscore(int width, int height, JFrame jf)
	{
		setSize(width, height);
		setModal(true);
		setUndecorated(true);
		setLocation(jf.getX() + 360, jf.getY() + 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 2, 1, 2, Color.WHITE));
		
		initComponents();
	}
	
	private void initComponents()
	{
		setLayout(new BorderLayout());
		JTextPane title_bar = new JTextPane();
		title_bar.setText("Ranglista");
		title_bar.setPreferredSize(new Dimension(700, 25));
		title_bar.setEditable(false);
		title_bar.setHighlighter(null);
		title_bar.setBackground(new Color(255, 204, 153));
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
		title_bar.setParagraphAttributes(sas, true);
		
		JTable jt = new JTable();
		JScrollPane jsp = new JScrollPane(jt);
		
		DefaultTableModel dtm = new DefaultTableModel(new Object[]{"#", "Játékos", "Nyeremény", "Eltelt idõ"}, 0)
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{  
		          return false;  
		    }
		};
		
		
		jt.setModel(dtm);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(JLabel.CENTER);
		jt.getColumnModel().getColumn(0).setCellRenderer(dtcr);
		jt.getColumnModel().getColumn(1).setCellRenderer(dtcr);	
		jt.getColumnModel().getColumn(2).setCellRenderer(dtcr);	
		jt.getColumnModel().getColumn(3).setCellRenderer(dtcr);	
		jt.setRowSelectionAllowed(false);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.getColumnModel().getColumn(0).setPreferredWidth(38);
		jt.getColumnModel().getColumn(1).setPreferredWidth(130);
		jt.getColumnModel().getColumn(2).setPreferredWidth(130);
		jt.getColumnModel().getColumn(3).setPreferredWidth(80);
		
		
		MenuButton exit = new MenuButton("OK");
		exit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				dispatchEvent(new WindowEvent(Highscore.this, WindowEvent.WINDOW_CLOSING));				
			}
		});
		
		
		HighscoreIO hio = new HighscoreIO();
		String[] rows = hio.getTop10Array();
		
		for(int i = 0; i < 10; i++)
		{
			if(!rows[i].equals(""))
			{
				String[] row_attr = rows[i].split("\t");
				dtm.addRow(new Object[] {String.valueOf(i + 1), row_attr[1], PrizeList.intGroupedByThreeDigits(Integer.parseInt(row_attr[2])), mmss(Integer.parseInt(row_attr[3]))});				
			}
			else
			{
				dtm.addRow(new Object[] {String.valueOf(i + 1), "", "", ""});
			}
		}
		
		
		
		add(title_bar, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		add(exit, BorderLayout.SOUTH);
	}
	
	public static String mmss(int time)
	{
		int min = time / 60;
		int sec = time % 60;
				
		return String.format("%02d:%02d", min, sec);
	}
}
