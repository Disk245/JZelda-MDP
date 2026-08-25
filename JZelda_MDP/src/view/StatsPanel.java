package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.StatsManager;

public class StatsPanel extends JPanel {

	private StatsManager statsManager;

	private JLabel titleLabel = new JLabel("STATS");

	private JLabel totalWinsLabel = new JLabel();
	private JLabel totalKillsLabel = new JLabel();
	private JLabel totalDeathsLabel = new JLabel();
	private JLabel highestScoreLabel = new JLabel();
	private JLabel fastestRunLabel = new JLabel();

	private JButton returnButton = new ImageButton("Return to Menu", "/resources/hud/ui_button_large.png");
	private JButton resetStatsButton = new ImageButton("Reset stats", "/resources/hud/ui_button_large.png");

	public StatsPanel(StatsManager statsManager) {

		this.statsManager = statsManager;

		setLayout(new BorderLayout());
		setBackground(Color.GRAY);

		// Setup area

		returnButton.setActionCommand("return");

		// Top area

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 30, 0));
		topPanel.add(titleLabel);
		add(topPanel, BorderLayout.NORTH);

		// Middle area

		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(20, 0, 20, 0);

		centerPanel.add(totalWinsLabel, gbc);
		centerPanel.add(totalKillsLabel, gbc);
		centerPanel.add(totalDeathsLabel, gbc);
		centerPanel.add(highestScoreLabel, gbc);
		centerPanel.add(fastestRunLabel, gbc);

		add(centerPanel, BorderLayout.CENTER);

		// Bottom area

		Font buttonFont = FontManager.getFont(28f);
		Dimension dimension = new Dimension(540, 90);
		returnButton.setPreferredSize(dimension);
		returnButton.setFont(buttonFont);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(returnButton);
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 100, 0));

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void setStatsListener(ActionListener listener) {
		returnButton.addActionListener(listener);
		resetStatsButton.addActionListener(listener);
	}

	public void refreshStats() {
		totalWinsLabel.setText("Total victories: " + statsManager.getValue("totalWins"));
		totalKillsLabel.setText("Total kills: " + statsManager.getValue("totalKills"));
		totalDeathsLabel.setText("Total deaths: " + statsManager.getValue("totalDeaths"));
		highestScoreLabel.setText("Highest score: " + statsManager.getValue("highScore"));
		fastestRunLabel.setText("Fastest run: " + formatTime(statsManager.getValue("fastestRun")));
	}

	private String formatTime(int totalSeconds) {
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;

		return String.format("%02d:%02d", minutes, seconds);
	}
}
