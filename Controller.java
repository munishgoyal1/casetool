import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Controller extends JFrame implements ActionListener
{
	public static Board current;
	public String label;

	// c_ prefix indicates "create"
	private JButton c_new;
	private JButton c_bubble, c_data_store, c_extr_entity,
					c_output, c_arrow;
	private JButton delete, rename;
	private JButton move_down, move_up;
	private JButton balance, dictionary, print, refresh;
	
	public Controller()
	{
		Container c = getContentPane();
		setSize(150,350);
		c.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));

//************************************************************//

		c_new = new JButton(new ImageIcon("images/New.jpg"));
		c_new.addActionListener(this);
		c.add(c_new);
		
		print = new JButton(new ImageIcon("images/Print.jpg"));
		print.addActionListener(this);
		c.add(print);
		
		c_bubble = new JButton(new ImageIcon("images/Bubble.jpg"));
		c_bubble.addActionListener(this);
		c.add(c_bubble);
		
		c_data_store =
			new JButton(new ImageIcon("images/DataStore.jpg"));
		c_data_store.addActionListener(this);
		c.add(c_data_store);
		
		c_extr_entity =
			new JButton(new ImageIcon("images/ExtrEntity.jpg"));
		c_extr_entity.addActionListener(this);
		c.add(c_extr_entity);
		
		c_output = new JButton(new ImageIcon("images/Output.jpg"));
		c_output.addActionListener(this);
		c.add(c_output);
		
		c_arrow = new JButton(new ImageIcon("images/Arrow.jpg"));
		c_arrow.addActionListener(this);
		c.add(c_arrow);
		
		rename = new JButton(new ImageIcon("images/Rename.jpg"));
		rename.addActionListener(this);
		c.add(rename);
		
		move_down = new JButton(new ImageIcon("images/Move_down.jpg"));
		move_down.addActionListener(this);
		c.add(move_down);
		
		move_up = new JButton(new ImageIcon("images/Move_up.jpg"));
		move_up.addActionListener(this);
		c.add(move_up);
		
		balance = new JButton(new ImageIcon("images/Balance.jpg"));
		balance.addActionListener(this);
		c.add(balance);
		
		dictionary =
			new JButton(new ImageIcon("images/Dictionary.jpg"));
		dictionary.addActionListener(this);
		c.add(dictionary);
		
		delete = new JButton(new ImageIcon("images/Delete.jpg"));
		delete.addActionListener(this);
		c.add(delete);
		
		refresh = new JButton(new ImageIcon("images/Refresh.jpg"));
		refresh.addActionListener(this);
		c.add(refresh);
		
//***********************************************************//

		addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent event) {
					System.exit(0);
				}
			});
		
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object s = ae.getSource();
		
	  if (s == c_new)
	  {
		  new RequestLabel(this,"Context Diagram");
		  current = new Board(label);
	  }
	  else if (current != null)
	  {
	  	String level = current.current.getLevel();
		if (s == print)
		{
			new PrintUtilities(current.current).print();
		}
		else if (s == c_bubble)
		{
			if (!level.equals("-1"))
			{
				new RequestLabel(this,"Bubble");
				current.drawBubble(label);
			}
			else
			{
				new Error(this,
					"Cannot draw Bubble in Context Diagram");
			}		
		}
		else if (s == c_data_store)
		{
			new RequestLabel(this,"Data Store");
			current.drawDataStore(label);
		}
		else if (s == c_arrow)
		{
			new RequestLabel(this,"Data Arrow");
			current.drawArrow(label);
		}
		else if (s == rename)
		{
			Symbol sym=current.current.selected;
			if (sym != null)
			{
				new RequestLabel(this,"Symbol");
				sym.reLabel(label);
				current.current.repaint();
			}
		}
		else if (s == move_down)
		{
			current.moveDown();
		}
		else if (s == move_up)
		{
			current.moveUp();
		}
		else if (s == delete)
		{
			current.delete();
		}
		else if (s == refresh)
		{
			current.current.repaint();
		}
		else if (s == dictionary)
		{
			ArrayList al = current.dataDictionary();
			String obj[][] = new String[al.size()][2];
			int size = al.size();
			
			Object o[] = al.toArray();
			for(int i=0;i<size;++i)
			{
				obj[i][0] = ""+o[i];
				obj[i][1] = "";
			}
			
			new DataDictionary(obj);
		}
		else if (s == balance)
		{
			new Error(this,current.checkBalance());
		}
		else if (level.equals("-1"))
		{
			if (s == c_extr_entity)
			{
				new RequestLabel(this,"External Entity");
				current.drawExtEntity(label);
			}
			else if (s == c_output)
			{
				new RequestLabel(this,"Output");
				current.drawOutput(label);
			}
		}
		else
		{
			new Error(this,"Cannot draw in non-context diagram");
		}
	  }
	}
	
	public static void main(String arg[])
	{
		new Controller();
	}
}