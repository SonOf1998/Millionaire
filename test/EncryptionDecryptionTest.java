import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import org.junit.Test;

public class EncryptionDecryptionTest 
{
	Cryptor c;
	
	@Before
	public void setUp()
	{
		c = new Cryptor();
	}
	
	@Test
	public void EncryptThenDecryptEnglishCharacters()
	{
		String original = "Regular english text.	1234567";
		String encrypted = c.encrypt(original);
		String decrypted = c.decrypt(encrypted);
		
		assertEquals(original, decrypted);
	}
	
	@Test
	public void EncryptThenDecryptSpecialCharacters()
	{
		String original = "~ˇ^˘°˛`˙´˝˝¨¸đĐ@łŁß¤×÷+*-/űúáéíóüö";
		String encrypted = c.encrypt(original);
		String decrypted = c.decrypt(encrypted);
		
		assertEquals(original, decrypted);
	}

}
