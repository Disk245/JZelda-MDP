package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation {

	private BufferedImage[] frames;
	private int animationSpeed;
	
	/**
	 * Builds an animation from a sprite sheet. Only works for single line animations.
	 * It cycles through the file specified in the imagePath and takes a series of
	 * sub images separated by the indicated width.
	 * @param imagePath the path to read
	 * @param frameWidth the size of each individual frame
	 * @param frameHeight the height of each individual frame
	 * @param animationSpeed to adjust the speed of the animation to the 60fps loop
	 */
	public Animation(String imagePath, int frameWidth, int frameHeight, int animationSpeed) {
		this.animationSpeed = animationSpeed;
		
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream(imagePath));
			int totalFrames = spriteSheet.getWidth() / frameWidth;
			frames = new BufferedImage[totalFrames];
			
			for (int i = 0; i < frames.length; i++) {
				frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getFrame(int i) { return frames[i]; }
	public int getAnimationLength() { return frames.length; }
	public int getAnimationSpeed() { return animationSpeed; }
	
	public BufferedImage getCurrentFrame(int stateTicks) {
		if (frames == null || frames.length == 0) return null;
		int frameIndex = (stateTicks / animationSpeed) % frames.length;
		return frames[frameIndex];
	}
}
