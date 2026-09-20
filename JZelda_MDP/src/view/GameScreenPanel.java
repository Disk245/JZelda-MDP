package view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import model.GameModel;
import model.GameModel.GameState;
import model.GameConfig;

/**
 * The game screen.
 * Observes the game model in order to react to its updates through the Observer interface.
 * It delegates drawing to the game panel or the pause panel.
 * The JLayeredPane class it extends allows to draw the pause menu as a separate layer.
 */
@SuppressWarnings("deprecation")
public class GameScreenPanel extends JLayeredPane implements Observer {

	private GamePanel gamePanel;
	private PausePanel pausePanel;
	private final GameModel model;

	/**
	 * Creates an instance of the game screen panel.
	 * The size is set using the GameConfig values.
	 * By default, the pause panel is set to not visible.
	 * @param gamePanel the game panel
	 * @param pausePanel the pause panel
	 * @param model the game model
	 */
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
		SwingUtilities.invokeLater(() -> gamePanel.repaint());
	}
	
	public PausePanel getPausePanel() {
	    return pausePanel;
	}

}
