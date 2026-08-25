package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameEndPanel extends JPanel {

	private JButton returnButton = new ImageButton("Return to Menu", "/resources/hud/ui_button_large.png");
	private JLabel titleLabel = new JLabel("GAME OVER");
	private JLabel heartScoreLabel = new JLabel("Hearts: 0");
	private JLabel itemScoreLabel = new JLabel("Items: 0");
	private JLabel killScoreLabel = new JLabel("Kills: 0");
	private JLabel timeBonusLabel = new JLabel("Time bonus: 0");
	private JLabel totalScoreLabel = new JLabel("Total score: 0");

	public GameEndPanel() {

		setLayout(new BorderLayout());
		setBackground(Color.GRAY);

		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		Dimension buttonDimension = new Dimension(540, 90);
		returnButton.setFont(FontManager.getFont(28f));

		// TOP AREA

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
		topPanel.add(titleLabel);
		add(topPanel, BorderLayout.NORTH);

		// MIDDLE AREA

		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(20, 0, 20, 0);

		centerPanel.add(heartScoreLabel, gbc);
		centerPanel.add(itemScoreLabel, gbc);
		centerPanel.add(killScoreLabel, gbc);
		centerPanel.add(timeBonusLabel, gbc);
		centerPanel.add(totalScoreLabel, gbc);

		add(centerPanel, BorderLayout.CENTER);

		// BOTTOM AREA

		returnButton.setActionCommand("return");
		returnButton.setPreferredSize(buttonDimension);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(returnButton);
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 50, 0));

		add(bottomPanel, BorderLayout.SOUTH);
	}

	public void setDefeatListener(ActionListener listener) {
		returnButton.addActionListener(listener);
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public void setScores(int heartScore, int itemScore, int killScore, int timeBonus, int totalScore) {

		heartScoreLabel.setText("Hearts: " + heartScore);
		itemScoreLabel.setText("Items: " + itemScore);
		killScoreLabel.setText("Kills: " + killScore);
		timeBonusLabel.setText("Time bonus: " + timeBonus);
		totalScoreLabel.setText("Total score: " + totalScore);
	}
}
