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
	private String loopingFilename;
	private boolean audioEnabled = true;

	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}

	private AudioManager() {
	}

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

	public void stopLoop() {
		loopingFilename = null;
		closeLoopingClip();
	}

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
