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

public class NicknamePanel extends JPanel {

	private MenuPanel menuPanel;
	private GamePanel gamePanel;

	private JLabel nicknamePrompt = new JLabel("What's your name?");
	private JTextField nicknameField = new JTextField(20);
	private JButton confirmButton = new ImageButton("Confirm", "/resources/hud/ui_button_small.png");
	private JButton backButton = new ImageButton("Back to menu", "/resources/hud/ui_button_small.png");

	private static final Font NICKNAME_FONT = FontManager.getFont(20f);

	public NicknamePanel() {

		// General setup
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		nicknamePrompt.setFont(NICKNAME_FONT);
		nicknameField.setFont(NICKNAME_FONT);

		// Button setup
		Dimension buttonDimension = new Dimension(300, 70);
		confirmButton.setActionCommand("confirm");
		confirmButton.setPreferredSize(buttonDimension);
		confirmButton.setFont(NICKNAME_FONT);

		backButton.setActionCommand("return");
		backButton.setPreferredSize(buttonDimension);
		backButton.setFont(NICKNAME_FONT);

		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 5, 0));
		buttonsPanel.setOpaque(false);
		buttonsPanel.add(confirmButton);
		buttonsPanel.add(backButton);

		// Text area setup
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;

		centerPanel.add(nicknamePrompt, gbc);
		centerPanel.add(nicknameField, gbc);
		centerPanel.add(buttonsPanel, gbc);

		// Adding to nickname panel
		add(centerPanel, BorderLayout.CENTER);
	}

	public void setNicknameListeners(ActionListener listener) {
		confirmButton.addActionListener(listener);
		backButton.addActionListener(listener);
	}

	public String getNickname() {
		return nicknameField.getText();
	}

	public void clearNickname() {
		nicknameField.setText("");
	}
}
