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

/**
 * Manages sound effects and background music. Based on the AudioManager
 * provided with the course project, extended with looping playback, track
 * switching, and audio enabling/disabling.
 */
public class AudioManager {
	private static AudioManager instance;
	private Clip loopingClip;
	private String loopingFilename;
	private boolean audioEnabled = true;

	/**
	 * Gets the instance of the audio manager. If it doesn't exist yet, it creates
	 * one.
	 * 
	 * @return the audio manager
	 */
	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}

	/**
	 * The constructor is private to protect its access and resort only to the
	 * getInstance method to access it.
	 */
	private AudioManager() {
	}

	/**
	 * Plays a .wav audio file once. If the audio is disabled, the method ends. If
	 * not, it loads the audio file from the path and plays it.
	 * 
	 * @param filename the audio file path
	 */
	public void play(String filename) {
		if (!audioEnabled)
			return;
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

	/**
	 * Loops a .wav audio file, allowing for background music to be played in a
	 * loop. First, it checks if the file path is the same of an already looping
	 * one, and if it's open. In that case, the method ends. Otherwise, stores the
	 * requested path, to be able to play the file even if audio is turned off, once
	 * restored.
	 * 
	 * @param filename the audio file path
	 */
	public void playLoop(String filename) {
		if (filename.equals(loopingFilename) && loopingClip != null && loopingClip.isOpen()) {
			return;
		}

		loopingFilename = filename;
		if (!audioEnabled)
			return;
		try {
			closeLoopingClip();
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
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

	/**
	 * Stops the currently looping music, also removing it from being tracked.
	 */
	public void stopLoop() {
		loopingFilename = null;
		closeLoopingClip();
	}

	/**
	 * Stops a looping clip, but does not delete the track's name from the objcet.
	 * This allows to restart the track if audio is turned back on.
	 */
	private void closeLoopingClip() {
		if (loopingClip != null) {
			loopingClip.stop();
			loopingClip.close();
			loopingClip = null;
		}
	}

	public boolean isAudioEnabled() {
		return audioEnabled;
	}

	/**
	 * Toggles the audio. If toggled off, stops the playing music. If toggled on,
	 * resumes playing music.
	 * 
	 * @param audioEnabled the state to put the audio in.
	 */
	public void setAudioEnabled(boolean audioEnabled) {
		if (this.audioEnabled == audioEnabled)
			return;

		this.audioEnabled = audioEnabled;

		if (!audioEnabled) {
			closeLoopingClip();
		} else if (loopingFilename != null) {
			playLoop(loopingFilename);
		}
	}
}
