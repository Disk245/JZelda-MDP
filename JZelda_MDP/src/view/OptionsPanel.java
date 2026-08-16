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

public class OptionsPanel extends JPanel{
	
	private static final Font OPTIONS_FONT = new Font("Calibri", Font.PLAIN, 48);
	
	private JCheckBox audioCheckBox = new JCheckBox("Toggle audio", true);
	private JButton resetStatsButton = new JButton("Reset stats");
	private JButton backButton = new JButton("Back to menu");
	
	
	
	
	
	
	public OptionsPanel() {
		
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		Dimension dimension = new Dimension(300,70);
		
		audioCheckBox.setFont(OPTIONS_FONT);
		audioCheckBox.setActionCommand("audio");
		audioCheckBox.setPreferredSize(dimension);
		
		resetStatsButton.setFont(OPTIONS_FONT);
		resetStatsButton.setActionCommand("reset");
		resetStatsButton.setPreferredSize(dimension);
		
		backButton.setFont(OPTIONS_FONT);
		backButton.setActionCommand("return");
		backButton.setPreferredSize(dimension);
		
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
