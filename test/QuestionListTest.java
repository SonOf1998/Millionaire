import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class QuestionListTest 
{
	@Test
	public void listLengthTest()
	{
		QuestionList ql = new QuestionList();
		ql.fillUpFromFile(new File("resources/questions.txt"));
		assertEquals(5000, ql.getSize());
	}
}
