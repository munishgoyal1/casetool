import java.awt.*;
import java.awt.event.*;

public class DataArrow implements Symbol, MouseListener
{
	private int tailx, taily, headx, heady;
	private Color color;
	private String label;
	
	private DrawBoard dboard;
	
	private boolean gotTail, gotHead;
	private Symbol tailS, headS;
	private boolean isSelected;
	
	public DataArrow(DrawBoard db,String lab)
	{
		dboard = db;
		dboard.addMouseListener(this);
		
		color = Color.black;
		label = lab;
		
		dboard.selected = this;
		dboard.drawingArrow = true;
		gotTail = gotHead = false;
	}
	
	private void drawArrow(Graphics g)
	{
		int l=30;
		double b=Math.atan(((double)(heady-taily))/(headx-tailx));
		if (headx-tailx<0)
				b = 3.142 + b;
		g.setColor(color);
		g.drawLine(tailx,taily,headx,heady);
		
		// alpha = 30 degree = 0.5233
		g.drawLine(headx,heady,	(int)(headx-l*Math.cos(b-.3491)),
								(int)(heady-l*Math.sin(b-.3491)));
		g.drawLine(headx,heady,	(int)(headx-l*Math.cos(b+.3491)),
								(int)(heady-l*Math.sin(b+.3491)));
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String toString()
	{
		return label;
	}
	
	public int getAssociation(Symbol s)
	{
		if(tailS.equals(s))
			return ExtArrow.TAIL;
		if(headS.equals(s))
			return ExtArrow.HEAD;
		
		return ExtArrow.NONE;
	}
	
	public int externalArrow()
	{
		return getAssociation(dboard.ext);
	}
	
	public int getX(Symbol s)
	{
		if(s.equals(tailS))
			return tailx;
		else if(s.equals(headS))
			return headx;
		return 0;
	}
	
	public int getY(Symbol s)
	{
		if(s.equals(tailS))
			return taily;
		else if(s.equals(headS))
			return heady;
		return 0;
	}
	
	public void setX(Symbol s,int x)
	{
		if(s.equals(tailS))
			tailx = x;
		else if(s.equals(headS))
			headx = x;
	}
	
	public void setY(Symbol s,int y)
	{
		if(s.equals(tailS))
			taily = y;
		else if(s.equals(headS))
			heady = y;
	}	
	
	public void translate(Symbol s,int tx,int ty)
	{
		if (s.equals(tailS))
		{
			try
			{
				External e = (External)headS;
				tailx += tx;
				taily += ty;
			}
			catch(Exception e)
			{
				tailx += tx/2;
				taily += ty/2;
			}
		}
		else if (s.equals(headS))
		{
			try
			{
				External e=(External)tailS;
				headx += tx;
				heady += ty;
			}
			catch(Exception e)
			{
				headx += tx/2;
				heady += ty/2;
			}
		}
	}
	
	public boolean connected(Symbol s)
	{
		return (s.equals(tailS) || s.equals(headS));
	}
	
	public void setNotSelected()
	{
		color = Color.black;
		isSelected = false;
	}
	
	public void draw(Graphics g)
	{
		if (gotHead && gotTail)
		{
			drawArrow(g);
			
			g.setColor(Color.red);
			int len = label.length() * 3;
			g.drawString(label,(headx+tailx)/2-len/2,
										(heady+taily)/2);
		}
	}
	
	public void reLabel(String label)
	{
		this.label = label;
	}
	
	public boolean setPoint(Symbol s,int x, int y)
	{
		if (!gotTail)
		{
			tailS = s;
			tailx = x;
			taily = y;
			gotTail = true;
			return true;
		}
		else if (!gotHead)
		{
			if (!s.equals(tailS))
			{
				headS = s;
				headx = x;
				heady = y;
				gotHead = true;
				dboard.drawingArrow = false;
				return true;
			}
		}
		return false;
	}
	
	public void mouseClicked(MouseEvent me)
	{
		if (gotHead && gotTail)
		{
			int x=me.getX(), y=me.getY();
			int len = label.length()*3;
			int left=(headx+tailx)/2-len/2;
			int top = (heady+taily)/2;
			
			if ((x>=left-5 && x<=left+len+5) && 
							(y>=top-5 && y<=top+5+5))
			{
				if(dboard.selected != null)
					dboard.selected.setNotSelected();
				isSelected = true;
				color = Color.red;
				dboard.selected = this;
				dboard.repaint();
			}
		}
	}
	
	public void mousePressed(MouseEvent me)
	{
	}
	
	public void mouseReleased(MouseEvent me)
	{
	}
	
	public void mouseEntered(MouseEvent me)
	{
	}
	
	public void mouseExited(MouseEvent me)
	{
	}
		
	
/*
	public void mouseDragged(MouseEvent me)
	{
	}
	
	public void mouseMoved(MouseEvent me)
	{
		if (!gotHead && gotTail)
		{
				dboard.repaint();
		}
	}
*/
}

	