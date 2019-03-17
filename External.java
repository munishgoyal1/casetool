import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

public class External implements Symbol, MouseListener
{
	private int left, top, width, height;
	
	private DrawBoard dboard;
	
	public External(DrawBoard db,int l,int t, int w,int h)
	{
		dboard = db;
		dboard.addMouseListener(this);
		
		left = l;
		top = t;
		width = w;
		height = h;
	}
	
	public String getLabel()
	{
		return "";
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.black);
		g.drawRect(left,top,width,height);
	}
	
	public void setNotSelected()
	{
	}
	
	public void reLabel(String l)
	{
	}
	
	public void mouseClicked(MouseEvent me)
	{
		int x=me.getX(), y=me.getY();
		
		if ((x < left || x>=left+width) ||
						(y<=top || y>=top+height))
		{
			if (dboard.drawingArrow)
			{
				((DataArrow)dboard.selected).setPoint(this,x,y);
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
}
	
