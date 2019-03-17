class test
{
	static void fun(int a[])
	{
		a[0] = 100;
	}
	public static void main(String a[])
	{
		int abc[] = {0,1,2,3};
		fun(abc);
		System.out.println(""+abc[0]);
	}
}