package view;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

public class LinkButton extends JButton {

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
