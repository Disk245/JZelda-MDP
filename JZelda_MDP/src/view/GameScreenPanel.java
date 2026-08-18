package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.GameModel;
import model.Player;
import model.Character.CharacterState;
import model.Character.Direction;

// Deve contenere sia il GamePanel che PausePanel, così non faccio gestire al MainFrame
// Ed è uguale alle altre opzioni
@SuppressWarnings("deprecation")
public class GameScreenPanel extends JPanel implements Observer{
	
	private GamePanel gamePanel;
	private PausePanel pausePanel;
	private final GameModel model;
	
	public GameScreenPanel(GamePanel gamePanel, PausePanel pausePanel, GameModel model) {
		this.gamePanel = gamePanel;
		this.pausePanel = pausePanel;
		this.model = model;
		
		setLayout(new GridBagLayout());
		setBackground(Color.BLACK);
		add(gamePanel);
	}
	
	@Override
	public void update(Observable observable, Object arg) {		

        SwingUtilities.invokeLater(() ->
	          gamePanel.updateVisuals()
	        );
	    
	}

}
