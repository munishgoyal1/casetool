import java.awt.*;
import java.util.*;

public interface Symbol
{
	Vector arrows=new Vector();
	void setNotSelected();
	void draw(Graphics g);
	void reLabel(String label);
	String getLabel();
}