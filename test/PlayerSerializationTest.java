import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class PlayerSerializationTest {

	@Test
	public void testSerialization() throws Exception
	{
		Player p = new Player("Játékos");
		p.addTime(123456789);
		
		FileOutputStream fos = new FileOutputStream("testPlayer.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(p);
		oos.close();
		
		p.addTime(1);
		
		FileInputStream fis = new FileInputStream("testPlayer.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		p = (Player)ois.readObject();
		ois.close();
		
		assertEquals(123456789, p.getTime());
	}
}
