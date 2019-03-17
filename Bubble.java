import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Bubble implements Symbol, MouseListener
{
	private int cx, cy, radius;
	private String label;
	private Color color;
	
	private String level;
	private DrawBoard dboard;
	public DrawBoard child;
	
	private boolean isSelected, resize, dragged;
	private boolean resizeL, resizeR, resizeT, resizeB;
	
	public Bubble(DrawBoard db,String level,
					int x,int y, int r, String lab)
	{
		this.level = level;
		dboard = db;
		dboard.addMouseListener(this);
		
		child = null;
		this.cx = x;
		this.cy = y;
		radius = r;
		label = lab;
		color = Color.blue;
		
		isSelected = false;
		resizeL = resizeR = resizeT = resizeB = false;
	}
	
	public void setNotSelected()
	{
		isSelected = false;
		color = Color.blue;
	}

	public void draw(Graphics g)
	{
		g.setColor(color);
		g.drawOval(cx-radius,cy-radius,2*radius,2*radius);
		g.setColor(Color.red);

		g.drawString(level,cx-level.length()*3,cy-radius/4);
		g.drawString(label,cx-label.length()*3,cy+radius/4);
	}
	
	public void reLabel(String label)
	{
		this.label = label;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public String getLevel()
	{
		return level;
	}
	
	public ArrayList checkBalance(ArrayList ext)
	{
		Enumeration e = arrows.elements();
		ArrayList err = new ArrayList();
		
		while (e.hasMoreElements())
		{
			try
			{
				DataArrow da = (DataArrow)e.nextElement();
				Iterator it = ext.iterator();
				while (it.hasNext())
				{
					ExtArrow ea=(ExtArrow)it.next();
					if (!ea.arrow.equals(""+da) ||
						da.getAssociation(this) != ea.type)
					{
						err.add(ea);
					}
				}
			}
			catch(Exception exp)
			{
			}
		}
		return err;
	}
	
	public void mouseClicked(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		int r=(int)Math.sqrt((double)((x-cx)*(x-cx)+(y-cy)*(y-cy)));
		
		if (r <= radius+5)
		{
			if (dboard.drawingArrow)
			{
				if (r>=radius-5)
				{
					if(((DataArrow)dboard.selected).setPoint(this,
										x,y))
						arrows.addElement(dboard.selected);		
				}
			}
			else
			{
				if(dboard.selected != null)
					dboard.selected.setNotSelected();
				isSelected = true;
				color = Color.red;
				dboard.selected = this;
			}
			dboard.repaint();
		}
	}
	
	private boolean isResize(int x, int y)
	{
		resize = true;
		if (x>=cx-5 && x<=cx+5)
		{
			if(y>=cy-radius-5 && y<=cy-radius+5)
				resizeT = true;
			else if(y>=cy+radius-5 && y<=cy+radius+5)
				resizeB = true;
			else
				resize = false;
		}
		else if (y>=cy-5 && y<=cy+5)
		{
			if(x>=cx-radius-5 && x<=cx-radius+5)
				resizeL = true;
			else if(x>=cx+radius-5 && x<=cx+radius+5)
				resizeR = true;
			else
				resize = false;
		}
		else
			resize = false;

		return resize;
	}
	
	public void mousePressed(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		if (!isResize(x,y))
		{
			double sq_r =(double)((x-cx)*(x-cx)+(y-cy)*(y-cy));
			if(radius >= (int)Math.sqrt(sq_r))
				dragged = true;
		}
	}
	
	public void mouseReleased(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		
		if (resize)
		{
			int pr = radius;
			Enumeration e = arrows.elements();
			if(resizeL)
				radius = cx - x;
			else if(resizeR)
				radius = x - cx;
			else if(resizeT)
				radius = cy - y;
			else if(resizeB)
				radius = y - cy;

			while (e.hasMoreElements())
			{
				DataArrow da=(DataArrow)e.nextElement();
				int px = da.getX(this);
				int py = da.getY(this);
				
				double a=Math.atan((double)(py-cy)/(px-cx));
				if(px-cx < 0)
					a += 3.142;
				da.setX(this,px+(int)((radius-pr)*Math.cos(a)/2));
				da.setY(this,py+(int)((radius-pr)*Math.sin(a)/2));	
			}	
				
			resizeL = resizeR = resizeT = resizeB = false;
			resize = false;
			dboard.repaint();
		}
		else if (dragged)
		{
			Enumeration e = arrows.elements();
			while (e.hasMoreElements())
			{
				DataArrow a=(DataArrow)e.nextElement();
				a.translate(this,x-cx,y-cy);
			}
		
			cx = x;
			cy = y;
			dboard.repaint();
			dragged = false;
		}
	}
	
	public void mouseEntered(MouseEvent me)
	{
	}
	
	public void mouseExited(MouseEvent me)
	{
	}	
}
		

