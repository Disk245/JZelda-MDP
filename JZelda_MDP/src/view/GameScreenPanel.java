package view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.GameModel;
import model.GameModel.GameState;
import model.GameConfig;

// Deve contenere sia il GamePanel che PausePanel, così non faccio gestire al MainFrame
// Ed è uguale alle altre opzioni
@SuppressWarnings("deprecation")
public class GameScreenPanel extends JLayeredPane implements Observer {

	private GamePanel gamePanel;
	private PausePanel pausePanel;
	private final GameModel model;

	public GameScreenPanel(GamePanel gamePanel, PausePanel pausePanel, GameModel model) {
		this.gamePanel = gamePanel;
		this.pausePanel = pausePanel;
		this.model = model;

		int width = GameConfig.SCREEN_WIDTH;
		int height = GameConfig.SCREEN_HEIGHT;

		setPreferredSize(new Dimension(width, height));
		setLayout(null);

		gamePanel.setBounds(0, 0, width, height);
		pausePanel.setBounds(0, 0, width, height);

		add(gamePanel, JLayeredPane.DEFAULT_LAYER);
		add(pausePanel, JLayeredPane.PALETTE_LAYER);
		
		pausePanel.setVisible(false);
	}

	@Override
	public void update(Observable observable, Object arg) {
		pausePanel.setVisible(model.getGameState() == GameState.PAUSE);
		SwingUtilities.invokeLater(() -> gamePanel.updateVisuals());
	}
	
	public PausePanel getPausePanel() {
	    return pausePanel;
	}

}
