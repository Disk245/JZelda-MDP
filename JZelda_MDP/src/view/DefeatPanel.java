package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DefeatPanel extends JPanel {
	
	private JButton returnButton = new JButton("Return to Menu");
	private JLabel gameOverLabel = new JLabel("GAME OVER");
	private JLabel scoreLabel = new JLabel("Score: ");
	
	public DefeatPanel() {
		
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		Dimension buttonDimension = new Dimension(540,90);
		returnButton.setFont(FontManager.getFont(28f));
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
	    topPanel.setBorder( BorderFactory.createEmptyBorder(50, 0, 30, 0));
	    topPanel.add(gameOverLabel);
	    add(topPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(20,0,20,0);
		
		centerPanel.add(scoreLabel, gbc);
		centerPanel.add(returnButton, gbc);
		returnButton.setActionCommand("return");
		returnButton.setPreferredSize(buttonDimension);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	public void setDefeatListener(ActionListener listener) {
	    returnButton.addActionListener(listener);
	}
	
	public void setScore(int score) {
	    scoreLabel.setText("Score: " + score);
	}

}
