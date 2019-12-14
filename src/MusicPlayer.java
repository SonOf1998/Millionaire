import java.io.*;

import javax.sound.sampled.*;

//Kész
public class MusicPlayer 
{
	private Clip clip;
	private boolean loop_flag;
	private static float volume = 0.5f;
	
	
	public MusicPlayer(String filename, boolean loop_flag_)
	{		
		try 
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename));
			clip = AudioSystem.getClip();
			clip.open(ais);		
			
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
		    gainControl.setValue(20f * (float) Math.log10(volume));			
			
		    loop_flag = loop_flag_;
			if(loop_flag)
			{
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			clip.start();
		} 
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public boolean isPlaying()
	{
		return clip != null;
	}
	
	public static void setVolume(float volume_) 
	{	        
	    volume = volume_;
	}
	
	public void saveVolumeSettings()
	{
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volume));	
	}
	
	public void kill()
	{
		if(clip != null)
		{
			clip.stop();
			clip = null;
		}		
	}
}
