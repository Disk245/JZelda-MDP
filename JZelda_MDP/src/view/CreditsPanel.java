package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class representing the credits panel. It displays the credits screen with
 * the material used to aid in learning how to create the game. Labels and
 * buttons are already initialized when declared.
 */
public class CreditsPanel extends JPanel {

	private JLabel creditsLabel = new JLabel("Credits");
	private LinkButton swingTutorialButton = new LinkButton(
			"<html><a href=''>Tutorial Java Swing - GB Factory Code</a></html>");
	private LinkButton tutorial2dGameButton = new LinkButton(
			"<html><a href=''>How to make a 2D game in Java - RyiSnow</a></html>");
	private LinkButton githubCredits = new LinkButton("<html><a href=''>MDP Guide - IonutCicio</a></html>");
	private LinkButton soundEffectsButton = new LinkButton("<html><a href=''>Sound effects - Pixabay</a></html>");
	private LinkButton fontButton = new LinkButton("<html><a href=''>Font - dafont.com</a></html>");
	private JButton returnButton = new ImageButton("Return to Menu", "/resources/hud/ui_button_large.png");

	/**
	 * Creates the panel. It uses a BorderLayout to distribute the components on the
	 * screen. The top area contains the panel's name ("CREDITS"). The center area
	 * contains the links to the resources used, with a GridBagLayout used to
	 * correctly position the components in rows. In the bottom is placed the return
	 * button
	 */
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
		fontButton.setActionCommand("font");
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
		centerPanel.add(fontButton, gbc);

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

	/**
	 * Links the button press to the actions to perform from the controller.
	 * 
	 * @param listener the button press listener, in this case the MenuController
	 */
	public void setCreditsListener(ActionListener listener) {
		swingTutorialButton.addActionListener(listener);
		tutorial2dGameButton.addActionListener(listener);
		githubCredits.addActionListener(listener);
		soundEffectsButton.addActionListener(listener);
		returnButton.addActionListener(listener);
		fontButton.addActionListener(listener);
	}
}
