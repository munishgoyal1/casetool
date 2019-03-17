import java.util.*;
import java.awt.*;
import java.awt.event.*;


public class Board extends Frame
{
	private CardLayout cards;
	private Panel deck;
	public DrawBoard current;
	private TreeMap panels;
	
	public Board(String label)
	{
		super("Drawing Board");
		deck = new Panel();
		deck.setLayout(cards = new CardLayout());
		
		panels = new TreeMap();
		
		current = new DrawBoard(this,null,"-1",label);
		panels.put("-1",current);
		deck.add(current,"-1");
		add(deck);
		
		setSize(600,500);
		setVisible(true);
	}
	
	public void drawBubble(String label)
	{
		current.label = label;
		current.drawBubble = true;
	}
	
	public void drawDataStore(String label)
	{
		current.label = label;
		current.drawDataStore = true;
	}
	
	public void drawExtEntity(String label)
	{
		current.label = label;
		current.drawExtEntity = true;
	}
	
	public void drawOutput(String label)
	{
		current.label = label;
		current.drawOutput = true;
	}
	
	public void drawArrow(String label)
	{
		current.label = label;
		current.selected = new DataArrow(current,label);
		current.addSymbol(current.selected);
		current.drawingArrow = true;
	}
	
	public void delete()
	{
		current.delete();
	}
	
	public void moveDown()
	{
		if (current.selected != null)
		{
			try
			{
				Bubble b = (Bubble)current.selected;
				if (b.child == null)
				{
					DrawBoard db = new DrawBoard(this,b,
												b.getLevel());
					b.child = db;
					deck.add(db,b.getLevel());
					panels.put(b.getLevel(),db);
					current = db;
				}
				else
				{
					current = b.child;
				}
				cards.show(deck,b.getLevel());
			}
			catch(Exception e)
			{
			}
		}
	}
	
	public void moveUp()
	{
		if (current.parent != null) 
		{
			String label = current.parent.getLevel();
			if (!label.equals("0"))
			{
				StringTokenizer st = 
					new StringTokenizer(label,".");

				int c = st.countTokens();
				String l = "0";
				st.nextToken();
				for(int i=0;i<c-2;++i)
					l += "."+st.nextToken();
				current = (DrawBoard)panels.get(l);
				cards.show(deck,l);	
			}
			else
			{
				current = (DrawBoard)panels.get("-1");
				cards.show(deck,"-1");
			}
		}
		else
		{
			System.out.println("NULL "+current);
		}
	}
	
	public void appendDatas(ArrayList m, ArrayList a)
	{
		Iterator ai = a.iterator();
		
		while (ai.hasNext())
		{
			Iterator mi = m.iterator();
			String d_new = ""+ai.next();
			boolean flag = true;
			
			while (mi.hasNext())
			{
				String d = ""+mi.next();
				if (d_new.equals(d))
				{
					flag = false;
					break;
				}
			}
			if(flag)
				m.add(d_new);
		}
	}
		
	
	public ArrayList dataDictionary()
	{
		Iterator it =  panels.entrySet().iterator();
		
		ArrayList al=new ArrayList();
		
		while (it.hasNext())
		{
			DrawBoard d = 
				(DrawBoard)((Map.Entry)it.next()).getValue();
			
			appendDatas(al,d.getDatas());
		}
		
		return al;
	}
	
	public ArrayList checkBalance()
	{
		return current.checkBalance();
	}
}