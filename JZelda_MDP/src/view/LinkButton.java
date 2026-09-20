package view;

import java.awt.Cursor;

import javax.swing.JButton;

/**
 * A Link Button. The use of the custom class allows not to repeat the same
 * setup logic for each of the buttons containing a link.
 */
public class LinkButton extends JButton {

	/**
	 * Creates the link button.
	 * 
	 * @param text the text to display
	 */
	public LinkButton(String text) {
		super(text);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setFont(FontManager.getFont(32f));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
