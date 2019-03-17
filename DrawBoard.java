import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class DrawBoard extends Panel implements MouseListener
{
	private Board board;
	private Vector symbols;
	
	public Symbol selected;
	public Bubble parent;
	private String level;
	private int b_count;
	
	public External ext;
	
	public boolean drawingArrow;
	public boolean drawBubble, drawDataStore;
	public boolean drawExtEntity, drawOutput;
	
	public String label;

	private int width=0, height=0;
	
	public DrawBoard(Board b, Bubble p,String level)
	{
		board = b;
		parent = p;
		this.level = level;
		b_count = 0;
				
		setLayout(null);
		symbols = new Vector();
		
		drawingArrow = false;
		drawBubble = drawDataStore = false;
		drawExtEntity = drawOutput = false;
		
		selected = null;
		this.addSymbol(ext=new External(this,10,10,570,450));
		this.repaint();
		
		addMouseListener(this);
	}
	
	public DrawBoard(Board b, Bubble p,String level,String label)
	{
		this(b,p,level);
		
		this.addSymbol(new Bubble(this,"0",300,250,50,label));
	}
	
	public String getLevel()
	{
		return level;
	}
	
	public void addSymbol(Symbol s)
	{
		symbols.addElement(s);
	} 

	public void delete()	// deletes the selected symbol
	{
		if (selected != null)
		{
			Enumeration e = selected.arrows.elements();
			while (e.hasMoreElements())
			{
				DataArrow da = (DataArrow)e.nextElement();
				if (da.connected(selected))
				{
					symbols.removeElement(da);
					selected.arrows.removeElement(da);
				}
			}
			symbols.removeElement(selected);
			selected = null;
			
			repaint();
		}
	}

	private boolean failedLookup(ArrayList a,Symbol s)
	{
			Iterator i = a.iterator();
			
			while (i.hasNext())
			{
				if (s.getLabel().equals(""+i.next()))
					return false;
			}

			return true;
	}

	public ArrayList getDatas()
	{
		Enumeration e = symbols.elements();
		ArrayList a = new ArrayList();
		
		while (e.hasMoreElements())
		{
			Object obj = e.nextElement();
			try
			{
				DataArrow da=(DataArrow)obj;
				if (failedLookup(a,da))
				{
					a.add(""+da);
				}
			}
			catch(Exception excp1)
			{
				try
				{
					DataStore ds = (DataStore)obj;
					if (failedLookup(a,ds))
					{
						a.add(""+ds);
					}
				}
				catch(Exception excp2)
				{
				}
			}
		}
		
		return a;
	}

	public void paint(Graphics g)
	{
		Enumeration e = symbols.elements();
		while (e.hasMoreElements())
		{
			Symbol s=(Symbol)e.nextElement();
			s.draw(g);
		}
	}
	
	public void mouseClicked(MouseEvent me)
	{
		if (drawBubble)
		{
			symbols.addElement(new Bubble(this,level+"."+b_count++,
								me.getX(),me.getY(),30,label));
			repaint();		
			drawBubble = false;
		}
		else if (drawDataStore)
		{
			symbols.addElement(new DataStore(this,me.getX()-20,
								me.getY()-20,30,30,label));
			repaint();		
			drawDataStore = false;
		}
		else if (drawExtEntity)
		{
			symbols.addElement(new ExternalEntity(this,me.getX()-20,
								me.getY()-20,30,30,label));
			repaint();		
			drawExtEntity = false;
		}
		else if (drawOutput)
		{
			symbols.addElement(new Output(this,me.getX()-20,
								me.getY()-20,30,30,label));
			repaint();		
			drawOutput = false;
		}
	}
	
	public void mouseEntered(MouseEvent me)
	{
		Controller.current = board;
	}
	
	public void mouseExited(MouseEvent me)
	{
	}
	
	public void mousePressed(MouseEvent me)
	{
	}
	
	public void mouseReleased(MouseEvent me)
	{
	}
	
	public String toString()
	{
		return level;
	}
	
	public ArrayList checkBalance()
	{
		Enumeration e = symbols.elements();
		ArrayList external = new ArrayList();
		int type;
		
		while (e.hasMoreElements())
		{
			try
			{
				DataArrow da = (DataArrow)e.nextElement();
				if ((type=da.externalArrow()) != ExtArrow.NONE)
				{
					external.add(new ExtArrow(da,type));
				}
			}
			catch(Exception exp)
			{
			}
		}
		
		if(this.parent == null)
			return external;

		return parent.checkBalance(external);
	}
}