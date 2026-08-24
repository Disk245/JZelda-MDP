package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageButton extends JButton {

	private BufferedImage backgroundImage;

	public ImageButton(String text, String path) {
		super(text);
		backgroundImage = loadImage(path);
		setIcon(new ImageIcon(backgroundImage));

		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setOpaque(false);

		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		
		// Needed to move the text positions inside of the buttons up a few pixels
		setUI(new BasicButtonUI() {
			@Override
			protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
				int offsetY = -4;
				textRect.y += offsetY;

				super.paintText(g, b, textRect, text);
			}
		});
	}
	
	/**
	 * Loads the custom image for buttons
	 * @param imagePath
	 * @return
	 */
	private BufferedImage loadImage(String imagePath) {
		try (InputStream stream = ImageButton.class.getResourceAsStream(imagePath)) {

			if (stream == null) {
				throw new ImageLoadingException("Image path not found: " + imagePath);
			}

			BufferedImage image = ImageIO.read(stream);

			if (image == null) {
				throw new ImageLoadingException("Image not valid: " + imagePath);
			}

			return image;

		} catch (IOException exception) {
			throw new ImageLoadingException("Error loading image: " + imagePath);
		}
	}
}
