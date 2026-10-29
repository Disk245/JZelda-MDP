package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class that handles the game's font. The font is initialised as static to
 * allow glboal access.
 */
public class FontManager {
	private static final Font GAME_FONT = loadFont();

	/**
	 * The FontManager constructor. It is private because there is no need to
	 * instantiate it, but its methods are used by being static
	 */
	private FontManager() {
	}

	/**
	 * Loads the game font. It looks for the font's path, then transforms the file
	 * into an InputStream. Based on the loaded file, it creates a Font. If that
	 * somehow fails, uses Calibri as a backup Font.
	 * 
	 * @return the game Font
	 */
	private static Font loadFont() {
		String path = "/resources/font/prstartk.ttf";

		InputStream stream = FontManager.class.getResourceAsStream(path);

		try {
			return Font.createFont(Font.TRUETYPE_FONT, stream);
		} catch (FontFormatException e) {
			e.printStackTrace();
			return new Font("Calibri", Font.PLAIN, 16);
		} catch (IOException e) {
			e.printStackTrace();
			return new Font("Calibri", Font.PLAIN, 16);
		}

	}

	public static Font getFont(float size) {
		return GAME_FONT.deriveFont(Font.PLAIN, size);
	}
}
