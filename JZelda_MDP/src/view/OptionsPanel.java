package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.*;

import audio.AudioManager;

public class OptionsPanel extends JPanel {

	private JToggleButton audioToggleButton = new JToggleButton("Toggle audio", true);
	private JLabel controlsLabel = new JLabel();
	private JButton backButton = new ImageButton("Back to menu", "/resources/hud/ui_button_large.png");
	private final Font BUTTON_FONT_SIZE = FontManager.getFont(28f);

	public OptionsPanel() {

		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		Dimension dimension = new Dimension(540, 90);

		// Setting custom background for the audio button

		audioToggleButton.setActionCommand("audio");
		audioToggleButton.setPreferredSize(dimension);
		audioToggleButton.setFont(BUTTON_FONT_SIZE);

		audioToggleButton.setIcon(new ImageIcon(getClass().getResource("/resources/hud/audio_toggle_unchecked.png")));
		audioToggleButton
				.setSelectedIcon(new ImageIcon(getClass().getResource("/resources/hud/audio_toggle_checked.png")));

		audioToggleButton.setHorizontalTextPosition(SwingConstants.CENTER);
		audioToggleButton.setVerticalTextPosition(SwingConstants.CENTER);

		audioToggleButton.setContentAreaFilled(false);
		audioToggleButton.setFocusPainted(false);
		audioToggleButton.setBorderPainted(false);
		audioToggleButton.setMargin(new Insets(0, 0, 0, 0));

		// Controls Label

		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/hud/controlsimage.png"));
		controlsLabel.setIcon(icon);

		// Return button

		backButton.setActionCommand("return");
		backButton.setPreferredSize(dimension);
		backButton.setFont(BUTTON_FONT_SIZE);

		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(40, 0, 40, 0);

		centerPanel.add(controlsLabel, gbc);
		centerPanel.add(audioToggleButton, gbc);

		add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(backButton);
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 100, 0));

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public boolean isAudioOn() {
		return this.audioToggleButton.isSelected();
	}

	public void setOptionsListeners(ActionListener listener) {
		audioToggleButton.addActionListener(listener);
		backButton.addActionListener(listener);
	}
}
