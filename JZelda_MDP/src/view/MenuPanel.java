package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

	JButton startButton = new ImageButton("Start Game", "/resources/hud/ui_button_large.png");
	JButton optionsButton = new ImageButton("Options", "/resources/hud/ui_button_large.png");
	JButton statsButton = new ImageButton("Stats", "/resources/hud/ui_button_large.png");
	JButton creditsButton = new ImageButton("Credits", "/resources/hud/ui_button_large.png");
	JButton exitButton = new ImageButton("Exit Game", "/resources/hud/ui_button_large.png");
	JLabel titleLabel = new JLabel("JZelda");

	public MenuPanel() {

		setLayout(new BorderLayout());
		setBackground(Color.GRAY);

		// TOP

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));
		topPanel.add(titleLabel);
		add(topPanel, BorderLayout.NORTH);

		// MIDDLE

		Dimension buttonDimension = new Dimension(540, 90);
		startButton.setActionCommand("start");
		startButton.setPreferredSize(buttonDimension);

		optionsButton.setActionCommand("options");
		optionsButton.setPreferredSize(buttonDimension);

		statsButton.setActionCommand("stats");
		statsButton.setPreferredSize(buttonDimension);

		creditsButton.setActionCommand("credits");
		creditsButton.setPreferredSize(buttonDimension);

		exitButton.setActionCommand("exit");
		exitButton.setPreferredSize(buttonDimension);

		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(10, 0, 10, 0);

		centerPanel.add(startButton, gbc);
		centerPanel.add(optionsButton, gbc);
		centerPanel.add(statsButton, gbc);
		centerPanel.add(creditsButton, gbc);
		centerPanel.add(exitButton, gbc);

		add(centerPanel, BorderLayout.CENTER);

		// BOTTOM

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));
		bottomPanel.setOpaque(false);

		JLabel versionLabel = new JLabel("V 0.8");
		versionLabel.setFont(FontManager.getFont(20f));

		JPanel scorePanel = new JPanel(new GridLayout(2, 1, 0, 5));
		scorePanel.setOpaque(false);

		JLabel highScoreLabel = new JLabel("none: 00000");
		highScoreLabel.setFont(FontManager.getFont(20f));

		JLabel fastestClearLabel = new JLabel("Fastest clear: 0:00:00");
		fastestClearLabel.setFont(FontManager.getFont(20f));

		scorePanel.add(highScoreLabel);
		scorePanel.add(fastestClearLabel);

		bottomPanel.add(versionLabel, BorderLayout.WEST);
		bottomPanel.add(scorePanel, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void setMenuListeners(ActionListener listener) {
		startButton.addActionListener(listener);
		optionsButton.addActionListener(listener);
		statsButton.addActionListener(listener);
		creditsButton.addActionListener(listener);
		exitButton.addActionListener(listener);
	}

}
