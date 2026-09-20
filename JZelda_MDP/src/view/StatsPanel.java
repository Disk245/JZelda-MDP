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

/**
 * The panel displaying the player's global stats.
 */
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

	/**
	 * Creates an instance of the StatsPanel It uses a BorderLayout to correctly
	 * place components on the map. The top area contains the panel's title
	 * ("STATS") The center area contains a label for each recorded stat. It uses a
	 * GridBagLayout to correctly sort components vertically The bottom area
	 * contains the reset button and the return button, contained in a panel to
	 * allow better placement.
	 * 
	 * @param statsManager the class to retrieve information from
	 */
	public StatsPanel(StatsManager statsManager) {

		this.statsManager = statsManager;

		setLayout(new BorderLayout());
		setBackground(Color.GRAY);

		// Setup area

		returnButton.setActionCommand("return");
		resetStatsButton.setActionCommand("reset");

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

		resetStatsButton.setPreferredSize(dimension);
		resetStatsButton.setFont(buttonFont);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(returnButton);
		bottomPanel.add(resetStatsButton);
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 100, 0));

		add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Connects button press to the actions in the menu controller
	 * 
	 * @param listener the menu controller
	 */
	public void setStatsListener(ActionListener listener) {
		returnButton.addActionListener(listener);
		resetStatsButton.addActionListener(listener);
	}

	/**
	 * Updates the stats with the values from the StatsManager.
	 */
	public void refreshStats() {
		totalWinsLabel.setText("Total victories: " + statsManager.getValue("totalWins"));
		totalKillsLabel.setText("Total kills: " + statsManager.getValue("totalKills"));
		totalDeathsLabel.setText("Total deaths: " + statsManager.getValue("totalDeaths"));
		highestScoreLabel.setText("Highest score: " + statsManager.getValue("highScore"));
		fastestRunLabel.setText("Fastest run: " + formatTime(statsManager.getValue("fastestRun")));
	}

	/**
	 * Returns a readable time format for the completion time.
	 * 
	 * @param totalSeconds elapsed seconds
	 * @return the formatted time as MM:SS
	 */
	private String formatTime(int totalSeconds) {
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;

		return String.format("%02d:%02d", minutes, seconds);
	}

}
