import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DataDictionary extends JFrame implements ActionListener
{
	private JTable table;
	private String values[][];
	private JButton ok;
	private JButton save;
	
	public DataDictionary(String v[][])
	{
		super("Data Dictionary");
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		values = v;
		String colHeads[] = {"Data Item","Comment"};
		table = new JTable(values,colHeads);
		JScrollPane jsp = new JScrollPane(table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		c.add(jsp);		
		
		ok = new JButton(" OK ");
		ok.addActionListener(this);
//		c.add(ok);
		
		save = new JButton("SAVE");
		save.addActionListener(this);
//		c.add(save);
		
		setSize(300,500);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
	}
}