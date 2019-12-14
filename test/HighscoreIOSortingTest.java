import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class HighscoreIOSortingTest {

	@Test
	public void SortByPointTest() 
	{
		//kódrészlet
		ArrayList<String> al = new ArrayList<>();
		
		al.add("Játékos 1	50000	76	49024");
		al.add("Játékos 2	50000	75	49025");
		al.add("Játékos 3	0	76	-76");
		al.add("Játékos 4	40000000	300	39999700");
		
		Collections.sort(al, Comparator.comparing(s -> -Integer.parseInt(s.split("\t")[3])));
		
		assertEquals(al.get(0), "Játékos 4	40000000	300	39999700");
		assertEquals(al.get(1), "Játékos 2	50000	75	49025");
		assertEquals(al.get(2), "Játékos 1	50000	76	49024");
		assertEquals(al.get(3), "Játékos 3	0	76	-76");
	}

}
