package audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {
private static AudioManager instance;
private Clip loopingClip;

public static AudioManager getInstance() {
	if (instance == null)
		instance = new AudioManager();
		return instance;
	}
	private AudioManager() {}
	
	public void play(String filename) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			
	        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-5.0f);
	        
			clip.start();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		}
	
	public void playLoop(String filename) {
	    try {
	    	stopLoop();
	        InputStream in = new BufferedInputStream(new FileInputStream(filename));
	        AudioInputStream audioIn =AudioSystem.getAudioInputStream(in);
	        loopingClip = AudioSystem.getClip();
	        loopingClip.open(audioIn);
	        
	        FloatControl volume = (FloatControl) loopingClip.getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-15.0f);
	        
	        
	        loopingClip.loop(Clip.LOOP_CONTINUOUSLY);

	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (UnsupportedAudioFileException e) {
	        e.printStackTrace();
	    } catch (LineUnavailableException e) {
	        e.printStackTrace();
	    }
	}
	private void stopLoop() {
		if (loopingClip != null) {
			loopingClip.stop();
		    loopingClip.close();
		    loopingClip = null;
		    }		
	}
}
