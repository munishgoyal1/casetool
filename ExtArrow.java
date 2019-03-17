public class ExtArrow
{
	public final static int NONE = 0;
	public final static int TAIL = 1;
	public final static int HEAD = 2;
	public DataArrow arrow;
	public int type;
	
	public ExtArrow(DataArrow a,int type)
	{
		this.arrow = a;
		this.type = type;
	}
}