import java.awt.*;

import javax.swing.*;

//Kész
public class Question 
{
	private int level;
	private String desc;
	private String A, B, C, D;
	private char solution;
	
	public Question(int level_, String desc_, String A_, String B_, String C_, String D_, char solution_)
	{
		level = level_;
		desc = desc_;
		A = A_;
		B = B_;
		C = C_;
		D = D_;
		solution = solution_;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public String getDescription()
	{
		return desc;
	}
	
	public String getA()
	{
		return A;
	}
	
	public String getB()
	{
		return B;
	}
	
	public String getC()
	{
		return C;
	}
	
	public String getD()
	{
		return D;
	}
	
	public char getSolution()
	{
		return solution;
	}
	
	
}
