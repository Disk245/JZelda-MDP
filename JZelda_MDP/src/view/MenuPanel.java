package view;

import javax.swing.*;
import model.StatsManager;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

	JButton startButton = new ImageButton("Start Game", "/resources/hud/ui_button_large.png");
	JButton optionsButton = new ImageButton("Options", "/resources/hud/ui_button_large.png");
	JButton statsButton = new ImageButton("Stats", "/resources/hud/ui_button_large.png");
	JButton creditsButton = new ImageButton("Credits", "/resources/hud/ui_button_large.png");
	JButton exitButton = new ImageButton("Exit Game", "/resources/hud/ui_button_large.png");
	JLabel titleLabel = new JLabel("JZelda");
	private final JLabel highScoreLabel = new JLabel();
	private final JLabel fastestClearLabel = new JLabel();

	public MenuPanel(StatsManager statsManager) {

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

		highScoreLabel.setFont(FontManager.getFont(16f));
		fastestClearLabel.setFont(FontManager.getFont(16f));

		scorePanel.add(highScoreLabel);
		scorePanel.add(fastestClearLabel);

		bottomPanel.add(versionLabel, BorderLayout.WEST);
		bottomPanel.add(scorePanel, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);
		refreshRecords(statsManager);
	}

	/**
	 * Refreshes the records in the bottom right part of the menu
	 * 
	 * @param statsManager the instance of the stats manager
	 */
	public void refreshRecords(StatsManager statsManager) {
		String scoreNickname = statsManager.getHighScoreNickname();
		String fastestNickname = statsManager.getFastestRunNickname();

		if (scoreNickname.isEmpty()) {
			scoreNickname = "none";
		}
		if (fastestNickname.isEmpty()) {
			fastestNickname = "none";
		}

		String score = "" + statsManager.getValue("highScore");
		while (score.length() < 5) {
			score = "0" + score;
		}
		highScoreLabel.setText("High Score - " + scoreNickname + ": " + score);

		int totalSeconds = statsManager.getValue("fastestRun");
		int hours = totalSeconds / 3600;
		int minutes = totalSeconds / 60 % 60;
		int seconds = totalSeconds % 60;

		String minutesText = "" + minutes;
		if (minutes < 10) {
			minutesText = "0" + minutes;
		}

		String secondsText = "" + seconds;
		if (seconds < 10) {
			secondsText = "0" + seconds;
		}

		fastestClearLabel
				.setText("Fastest clear - " + fastestNickname + ": " + hours + ":" + minutesText + ":" + secondsText);
	}

	public void setMenuListeners(ActionListener listener) {
		startButton.addActionListener(listener);
		optionsButton.addActionListener(listener);
		statsButton.addActionListener(listener);
		creditsButton.addActionListener(listener);
		exitButton.addActionListener(listener);
	}

}
