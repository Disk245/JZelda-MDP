package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class that displays the pause panel
 */
public class PausePanel extends JPanel {

	private JButton returnButton = new ImageButton("Return to Menu", "/resources/hud/ui_button_large.png");

	/**
	 * Creates an instance of the pause panel. Uses a semi-transparent black box to
	 * draw the button inside. Uses a GridBagLayout to correctly sort components in
	 * rows.
	 */
	public PausePanel() {
		setOpaque(false);
		setLayout(new GridBagLayout());

		JPanel menu = new JPanel(new GridBagLayout());
		menu.setPreferredSize(new Dimension(600, 400));
		menu.setBackground(new Color(0, 0, 0, 220));

		JLabel pauseLabel = new JLabel("PAUSE");
		pauseLabel.setForeground(Color.WHITE);
		pauseLabel.setFont(FontManager.getFont(44f));

		returnButton.setActionCommand("return");
		returnButton.setFont(FontManager.getFont(28f));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(20, 0, 40, 0);
		menu.add(pauseLabel, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 20, 0);
		menu.add(returnButton, gbc);

		add(menu);
	}

	/**
	 * Connects the pause menu button to the menu controller
	 * 
	 * @param listener the menu controller
	 */
	public void setPauseListener(ActionListener listener) {
		returnButton.addActionListener(listener);
	}
}
