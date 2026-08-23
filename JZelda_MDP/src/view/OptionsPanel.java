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

import audio.AudioManager;

public class OptionsPanel extends JPanel{
	
	private JCheckBox audioCheckBox = new JCheckBox("Toggle audio", true);
	private JButton resetStatsButton = new JButton("Reset stats");
	private JButton backButton = new JButton("Back to menu");
	private final Font BUTTON_FONT_SIZE = FontManager.getFont(28f);
	
	
	
	
	
	
	public OptionsPanel() {
		
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		Dimension dimension = new Dimension(540,70);
		audioCheckBox.setActionCommand("audio");
		audioCheckBox.setPreferredSize(dimension);
		audioCheckBox.setFont(BUTTON_FONT_SIZE);
		
		
		resetStatsButton.setActionCommand("reset");
		resetStatsButton.setPreferredSize(dimension);
		resetStatsButton.setFont(BUTTON_FONT_SIZE);
		
		backButton.setActionCommand("return");
		backButton.setPreferredSize(dimension);
		backButton.setFont(BUTTON_FONT_SIZE);
		
		JPanel centerPanel = new JPanel(new GridBagLayout());
		centerPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.insets = new Insets(10,0,10,0);
		
		centerPanel.add(audioCheckBox, gbc);
		centerPanel.add(resetStatsButton, gbc);
		centerPanel.add(backButton, gbc);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	public boolean isAudioOn() { return this.audioCheckBox.isSelected(); }
	
	public void setOptionsListeners(ActionListener listener) {
		audioCheckBox.addActionListener(listener);
		resetStatsButton.addActionListener(listener);
		backButton.addActionListener(listener);
	}
}
