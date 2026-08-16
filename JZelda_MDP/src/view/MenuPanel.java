package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class MenuPanel extends JPanel{
	
	JButton startButton = new JButton("Start Game");
	JButton optionsButton = new JButton("Options");
	JButton statsButton = new JButton("Stats");
	JButton creditsButton = new JButton("Credits");
	JButton exitButton = new JButton("Exit Game");
	JLabel titleLabel = new JLabel("JZelda");

	public MenuPanel() {
		
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		
		// Alto
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		titleLabel.setFont(new Font("Calibri", Font.PLAIN, 48));
		
		topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		topPanel.add(titleLabel);
		
		add(topPanel, BorderLayout.NORTH);
		
		// Centro
		
		Dimension buttonDimension = new Dimension(540,90);
		Font buttonFont = new Font("Calibri", Font.PLAIN, 48);
		startButton.setActionCommand("start");
		startButton.setPreferredSize(buttonDimension);
		startButton.setFont(buttonFont);
		optionsButton.setActionCommand("options");
		optionsButton.setPreferredSize(buttonDimension);
		optionsButton.setFont(buttonFont);
		statsButton.setActionCommand("stats");
		statsButton.setPreferredSize(buttonDimension);
		statsButton.setFont(buttonFont);
		creditsButton.setActionCommand("credits");
		creditsButton.setPreferredSize(buttonDimension);
		creditsButton.setFont(buttonFont);
		exitButton.setActionCommand("exit");
		exitButton.setPreferredSize(buttonDimension);
		exitButton.setFont(buttonFont);
		
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		// gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10,0,10,0);
		
		centerPanel.add(startButton, gbc);
		centerPanel.add(optionsButton, gbc);
		centerPanel.add(statsButton, gbc);
		centerPanel.add(creditsButton, gbc);
		centerPanel.add(exitButton, gbc);
		
		add(centerPanel, BorderLayout.CENTER);
		
		// Basso
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		bottomPanel.setOpaque(false);
		
		JLabel versionLabel = new JLabel("0.0");
		versionLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
		
		JPanel scorePanel = new JPanel(new GridLayout(2,1,0,5));
		scorePanel.setOpaque(false);
		
		JLabel highScoreLabel = new JLabel("none: 00000");
		highScoreLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
		
		JLabel fastestClearLabel = new JLabel("Fastest clear: 0:00:00");
		fastestClearLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
		
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
