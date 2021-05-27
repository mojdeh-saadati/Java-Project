import java.util.*;
import java.lang.*;
import java.io.*;

class FileMe
{
	int b = 0 ;
	public static void main(String[] args)throws Exception
	{
	int a  = 0;
	a = System.in.read();
	b = b + 2;
	a = b*(a+3)/4;
	if(b == 0)		
		System.out.print("b="+b);
		System.out.print("a="+a);

	}	

}
