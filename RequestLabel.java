import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RequestLabel extends JDialog implements ActionListener
{
	private Controller contr;
	private JTextField label;
	private JButton ok;
	
	public RequestLabel(Frame owner,String title)
	{
		super(owner,"Label of "+title,true);
		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		setSize(200,150);
		
		contr = (Controller)owner;
		
		label = new JTextField("",15);
		c.add(label);
		label.addActionListener(this);
		
		ok = new JButton("OK");
		c.add(ok);
		ok.addActionListener(this);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == ok)
		{
			if (label == null)
			{
				dispose();
			}
			else
			{
				contr.label = label.getText();
				if (contr.label.equals(""))
				{
					new Error(this);
				}
				else
					dispose();
			}
		}
	}
}
