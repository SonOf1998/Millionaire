import java.io.*;
import java.util.*;


//Finished.
public class QuestionList 
{
	private ArrayList<Question> questions;
	
	public QuestionList()
	{
		questions = new ArrayList<>();
	}
	
	public void fillUpFromFile(File file)
	{		
		try
		{
			FileReader fis = new FileReader(file);
			BufferedReader br = new BufferedReader(fis);
			String line;
			while((line = br.readLine()) != null)
			{
				String[] tmp = line.split("\t");
				Question q = new Question(Integer.parseInt(tmp[0]) - 1, tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6].charAt(0));
				questions.add(q);
			}
			br.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
	
	public Question getQuestion(int level_)
	{
		Random r = new Random();
		Question q = questions.get(r.nextInt(questions.size()));
		while(q.getLevel() != level_)
		{
			q = questions.get(r.nextInt(questions.size()));
		}
		
		return q;
	}
	
	// Teszt miatt..
	public int getSize()
	{
		return questions.size();
	}
}
