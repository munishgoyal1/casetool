import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Error extends JDialog implements ActionListener
{
	private JButton ok;

	public Error(Controller owner,String msg)
	{
		super(owner,"ERROR",true);
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		setSize(300,150);
		
		c.add(new JLabel(msg));
				
		ok = new JButton("OK");
		c.add(ok);
		ok.addActionListener(this);
		
		setVisible(true);
	}
	
	public Error(JDialog owner)
	{
		super(owner,"ERROR",true);
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		setSize(200,150);
		
		c.add(new JLabel("Please give a label"));
		
		ok = new JButton("OK");
		c.add(ok);
		ok.addActionListener(this);
		
		setVisible(true);
	}
	
	public Error(Controller owner,ArrayList err)
	{
		super(owner,"ERROR",true);
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		setSize(150,200);
		
		String msg = "Number of errors: " + err.size();
		
		Iterator it = err.iterator();
		while (it.hasNext())
		{
			msg += "\n" + ((ExtArrow)it.next()).arrow;
		}
		
		c.add(new JTextArea(msg,5,10));
				
		ok = new JButton("OK");
		c.add(ok);
		ok.addActionListener(this);
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == ok)
		{
				dispose();
		}
	}
}