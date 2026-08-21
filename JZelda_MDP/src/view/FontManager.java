package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontManager {
	private static final Font GAME_FONT = loadFont();
	
	private FontManager() {}

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
	
	public static Font getFont(float size) { return GAME_FONT.deriveFont(Font.PLAIN, size); }
}
