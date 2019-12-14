import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

public class HighscoreIOSortingTest {

	@Test
	public void SortByPointTest() 
	{
		//k�dr�szlet
		ArrayList<String> al = new ArrayList<>();
		
		al.add("J�t�kos 1	50000	76	49024");
		al.add("J�t�kos 2	50000	75	49025");
		al.add("J�t�kos 3	0	76	-76");
		al.add("J�t�kos 4	40000000	300	39999700");
		
		Collections.sort(al, Comparator.comparing(s -> -Integer.parseInt(s.split("\t")[3])));
		
		assertEquals(al.get(0), "J�t�kos 4	40000000	300	39999700");
		assertEquals(al.get(1), "J�t�kos 2	50000	75	49025");
		assertEquals(al.get(2), "J�t�kos 1	50000	76	49024");
		assertEquals(al.get(3), "J�t�kos 3	0	76	-76");
	}

}
