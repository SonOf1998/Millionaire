import java.security.*;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.*;
// Kész.
public class Cryptor 
{
	private final String KEY_128BIT = "cryptokulcs12345";
	
	private Cipher cipher;
	private Key key;
	
	public Cryptor()
	{
		try 
		{
			cipher = Cipher.getInstance("AES");
			key = new SecretKeySpec(KEY_128BIT.getBytes(), "AES");
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String encrypt(String s)
	{
		String res = null;
		
		try 
		{
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encrypted = cipher.doFinal(s.getBytes());
			res = new String(Base64.getEncoder().encode(encrypted));
		} 
		catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{
			e.printStackTrace();
		}
		
		return res;
	}
	
	public String decrypt(String s)
	{
		String res = null;
		
		try 
		{
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decoded = Base64.getDecoder().decode(s.getBytes());
			byte[] decrypted = cipher.doFinal(decoded);
			res = new String(decrypted);
		} 
		catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{
			e.printStackTrace();
		}
		
		return res;
	}
	
}
