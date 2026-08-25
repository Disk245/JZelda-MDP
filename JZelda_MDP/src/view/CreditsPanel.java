package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CreditsPanel extends JPanel {

	private JLabel creditsLabel = new JLabel("Credits");
	// CORRETTO: Nessun \" e nessun + superfluo
	private LinkButton swingTutorialButton = new LinkButton(
			"<html><a href=''>Tutorial Java Swing - GB Factory Code</a></html>");
	private LinkButton tutorial2dGameButton = new LinkButton(
			"<html><a href=''>How to make a 2D game in Java - RyiSnow</a></html>");
	private LinkButton githubCredits = new LinkButton("<html><a href=''>MDP Guide - IonutCicio</a></html>");
	private LinkButton soundEffectsButton = new LinkButton("<html><a href=''>Sound effects - Pixabay</a></html>");
	private JButton returnButton = new ImageButton("Return to Menu", "/resources/hud/ui_button_large.png");

	public CreditsPanel() {

		setLayout(new BorderLayout());
		setBackground(Color.GRAY);

		// TITLE AREA

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 30, 0));
		topPanel.add(creditsLabel);
		add(topPanel, BorderLayout.NORTH);

		// LINKS AREA
		
		swingTutorialButton.setActionCommand("link_swing");
        tutorial2dGameButton.setActionCommand("link_2dgame");
        githubCredits.setActionCommand("link_github");
        soundEffectsButton.setActionCommand("link_sounds");
        returnButton.setActionCommand("return");
		
		
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(20, 0, 20, 0);

		centerPanel.add(swingTutorialButton, gbc);
		centerPanel.add(tutorial2dGameButton, gbc);
		centerPanel.add(githubCredits, gbc);
		centerPanel.add(soundEffectsButton, gbc);

		add(centerPanel, BorderLayout.CENTER);

		// RETURN AREA
		
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

	public void setCreditsListener(ActionListener listener) {
		swingTutorialButton.addActionListener(listener);
		tutorial2dGameButton.addActionListener(listener);
		githubCredits.addActionListener(listener);
		soundEffectsButton.addActionListener(listener);
		returnButton.addActionListener(listener);
	}
}
