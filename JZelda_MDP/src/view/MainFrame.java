package view;

import javax.swing.*;

import model.GameModel;
import model.GameModel.GameState;
import model.RunStats;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MainFrame extends JFrame implements Observer {

	private CardLayout cardLayout;
	private JPanel mainPanel;

	private MenuPanel menuPanel;
	private NicknamePanel nicknamePanel;
	private OptionsPanel optionsPanel;
	private CreditsPanel creditsPanel;
	private GameScreenPanel gameScreenPanel;
	private GameEndPanel gameEndPanel;

	private GameModel model;

	public MainFrame(GameModel model) {
		super("JZelda");

		this.model = model;
		this.menuPanel = new MenuPanel();
		this.nicknamePanel = new NicknamePanel();
		this.gameScreenPanel = new GameScreenPanel(new GamePanel(model), new PausePanel(), model);
		this.optionsPanel = new OptionsPanel();
		this.creditsPanel = new CreditsPanel();
		this.gameEndPanel = new GameEndPanel();

		this.cardLayout = new CardLayout();
		this.mainPanel = createBGPanel();
		
		mainPanel.setOpaque(false);

		mainPanel.add(menuPanel, "MENU");
		mainPanel.add(nicknamePanel, "NICKNAME");
		mainPanel.add(gameScreenPanel, "GAME");
		mainPanel.add(optionsPanel, "OPTIONS");
		mainPanel.add(creditsPanel, "CREDITS");
		mainPanel.add(gameEndPanel, "DEFEAT");
		
		menuPanel.setOpaque(false);
        nicknamePanel.setOpaque(false);
        optionsPanel.setOpaque(false);
        creditsPanel.setOpaque(false);
        gameEndPanel.setOpaque(false);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private JPanel createBGPanel() {
		Image menuBackground = new ImageIcon(getClass().getResource("/resources/hud/panelbg.png")).getImage();

		JPanel bgPanel = new JPanel(cardLayout) {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(menuBackground, 0, 0, getWidth(), getHeight(), this);
		    }
		};

		return bgPanel;
	}

	public GameScreenPanel getGameScreenPanel() {
		return gameScreenPanel;
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public NicknamePanel getNicknamePanel() {
		return nicknamePanel;
	}

	public OptionsPanel getOptionsPanel() {
		return optionsPanel;
	}

	public GameEndPanel getDefeatPanel() {
		return gameEndPanel;
	}

	public CreditsPanel getCreditsPanel() {
		return creditsPanel;
	}

	public void showMenu() {
		cardLayout.show(mainPanel, "MENU");
	}

	public void showNickname() {
		cardLayout.show(mainPanel, "NICKNAME");
	}

	public void showGame() {
		cardLayout.show(mainPanel, "GAME");
		SwingUtilities.invokeLater(() -> gameScreenPanel.requestFocusInWindow());
	}

	public void showOptions() {
		cardLayout.show(mainPanel, "OPTIONS");
	}

	public void showGameEnd() {
		cardLayout.show(mainPanel, "DEFEAT");
	}

	public void showCredits() {
		cardLayout.show(mainPanel, "CREDITS");
	}

	/**
	 * Checks into the RunStats class to retrieve the information about score
	 */
	private void updateFinalScore() {
		RunStats run = model.getCurrentRun();

		gameEndPanel.setScores(run.getHeartScore(), run.getItemScore(), run.getKillScore(), run.getTimeBonus(),
				run.getTotalScore());
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof GameState))
			return;

		// public enum GameState {MENU,NICKNAME,CREDITS,OPTIONS,PLAY,PAUSE,DEFEAT}
		GameState state = (GameState) arg;
		switch (state) {
		case MENU:
			showMenu();
			break;
		case NICKNAME:
			showNickname();
			break;
		case CREDITS:
			showCredits();
			break;
		case OPTIONS:
			showOptions();
			break;
		case PLAY:
			showGame();
			break;
		case GAME_OVER:
			gameEndPanel.setTitle("GAME OVER");
			updateFinalScore();
			showGameEnd();
			break;
		case WIN:
			gameEndPanel.setTitle("VICTORY!");
			updateFinalScore();
			showGameEnd();
			break;
		default:
			break;
		}

	}
}
