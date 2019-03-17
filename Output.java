import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class Output implements Symbol, MouseListener
{
	private int left, top, width, height;
	private String label;
	private Color color;
	
	private DrawBoard dboard;
	
	private boolean isSelected, resize, dragged;
	private boolean resizeLeft, resizeTop;
	private boolean resizeBottom, resizeRight;
	
	public Output(DrawBoard db,int l,int t, int w,
							int h, String lab)
	{
		dboard = db;
		dboard.addMouseListener(this);
		
		left = l;
		top = t;
		width = w;
		height = h;
		label = lab;
		color = Color.blue;
		
		isSelected = false;
		resize = false;
		dragged = false;
		
		resizeLeft = resizeTop = resizeBottom = 
									resizeRight = false;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setNotSelected()
	{
		color = Color.blue;
		isSelected = false;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.drawLine(left,top,left+width-width/6,top);
		g.drawLine(left,top,left,top+height);
		g.drawLine(left,top+height,left+width,top+height);
		g.drawLine(left+width,top+height/6,left+width,top+height);
		g.drawLine(left+width-width/6,top,left+width,
										top+height/6);
		g.setColor(Color.red);
		g.drawString(label,left+width/2-label.length()*3,
						top+height/2);
	}
	
	public void reLabel(String label)
	{
		this.label = label;
	}
	
	private boolean isResize(int x, int y)
	{
		resize = true;
		if(y>=top-5 && y<=top+5)
			resizeTop = true;
		else if(x>=left-5 && x<=left+5)
			resizeLeft = true;
		else if(y>=top+height-5 && y<=top+height+5)
			resizeBottom = true;
		else if(x>=left+width-5 && x<=left+width+5)
			resizeRight = true;
		else
			resize = false;	
		return resize;	
	}
	
	private boolean checkArrowPoint(Point p)
	{
		boolean ret = true;
		if (p.y>=top-5 && p.y<=top+5)
			p.y = top;
		else if(p.x>=left-5 && p.x<=left+5)
			p.x = left;
		else if(p.y>=top+height-5 && p.y<=top+height+5)
			p.y = top+height;
		else if(p.x>=left+width-5 && p.x<=left+width+5)
			p.x = left+width;
		else
			ret = false;	
		return ret;	

	}
	
	public void mouseClicked(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		
		if ((x>=left-5 && x<=left+width+5) &&
						(y>=top-5 && y<=top+height+5))
		{
			if (dboard.drawingArrow)
			{
				Point p = new Point(x,y);
				if (checkArrowPoint(p))
				{
					if(((DataArrow)dboard.selected).setPoint(this,
														p.x,p.y))
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
	
	public void mousePressed(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		if ((x>=left-5 && x<=left+width+5) &&
						(y>=top-5 && y<=top+height+5))
			if(!isResize(x,y))
				dragged = true;
	}
	
	public void mouseReleased(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		if (dragged)
		{
			Enumeration e = arrows.elements();
			while (e.hasMoreElements())
			{
				DataArrow a=(DataArrow)e.nextElement();
				a.translate(this,x-width/2-left,y-height/2-top);
			}
			left = x - width/2;
			top = y - height/2;
			dboard.repaint();
			dragged = false;
		}
		else if (resize)
		{
			Enumeration e=arrows.elements();
			if (resizeLeft)
			{
				while (e.hasMoreElements())
				{
					DataArrow da=(DataArrow)e.nextElement();
					if(da.getX(this)<=left+2&&da.getX(this)>=left-2)
						da.setX(this,x);
				}
				width = left+width - x;
				left = x;
				resizeLeft = false;
			}
			else if (resizeTop)
			{
				while (e.hasMoreElements())
				{
					DataArrow da=(DataArrow)e.nextElement();
					if(da.getY(this)<=top+2&&da.getY(this)>=top-2)
						da.setY(this,y);
				}
				height = top+height - y;
				top = y;
				resizeTop = false;
			}
			else if (resizeRight)
			{
				while (e.hasMoreElements())
				{
					DataArrow da=(DataArrow)e.nextElement();
					if(da.getX(this)<=left+width+2&&
									da.getX(this)>=left+width-2)
						da.setX(this,x);
				}
				width = x - left;
				resizeRight = false;
			}
			else if (resizeBottom)
			{
				while (e.hasMoreElements())
				{
					DataArrow da=(DataArrow)e.nextElement();
					if(da.getY(this)<=top+height+2&&
								da.getY(this)>=top+height-2)
						da.setY(this,y);
				}
				height = y-top;
				resizeBottom = false;
			}
			
			resize = false;
			dboard.repaint();
		}
	}
	
	public void mouseEntered(MouseEvent me)
	{
	}
	
	public void mouseExited(MouseEvent me)
	{
	}	
}
	
