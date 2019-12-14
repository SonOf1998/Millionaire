import static org.junit.Assert.*;

import java.io.*;

import javax.sound.sampled.*;

import org.junit.Test;

public class MusicPlayerTest 
{
	@Test
	public void testONOFF() 
	{
		MusicPlayer mp = new MusicPlayer("resources/wav/theme.wav", false);		
		assertEquals(true, mp.isPlaying());
		mp.kill();
		assertEquals(false, mp.isPlaying());
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testMissingFile() throws Exception
	{
		// kódrészlet
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File("NOFILE.wav"));
	}
	
	@Test(expected = UnsupportedAudioFileException.class)
	public void testWrongExtension() throws Exception
	{
		// kódrészlet
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File("resources/wav/test.mp3"));
		Clip clip = AudioSystem.getClip();
		clip.open(ais);	
		clip.start();
	}
}
