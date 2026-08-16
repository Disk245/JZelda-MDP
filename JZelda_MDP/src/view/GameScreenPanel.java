package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

// Deve contenere sia il GamePanel che PausePanel, così non faccio gestire al MainFrame
// Ed è uguale alle altre opzioni
@SuppressWarnings("deprecation")
public class GameScreenPanel extends JPanel implements Observer{
	
	private GamePanel gamePanel;
	private PausePanel pausePanel;
	
	public GameScreenPanel(GamePanel gamePanel, PausePanel pausePanel) {
		this.gamePanel = gamePanel;
		this.pausePanel = pausePanel;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		
	}

}
