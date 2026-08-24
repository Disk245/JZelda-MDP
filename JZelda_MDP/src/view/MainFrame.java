package view;

import javax.swing.*;

import model.GameModel;
import model.GameModel.GameState;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MainFrame extends JFrame implements Observer{
	
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	private MenuPanel menuPanel;
	private NicknamePanel nicknamePanel;
    private OptionsPanel optionsPanel; 
    private CreditsPanel creditsPanel;
    private GameScreenPanel gameScreenPanel;
    private DefeatPanel defeatPanel;
	
	public MainFrame(GameModel model){
		super("JZelda");
		
		this.menuPanel = new MenuPanel();
		this.nicknamePanel = new NicknamePanel();
		this.gameScreenPanel = new GameScreenPanel(new GamePanel(model), new PausePanel(), model);
		this.optionsPanel = new OptionsPanel();
		this.creditsPanel = new CreditsPanel();
		this.defeatPanel = new DefeatPanel();
		
		this.cardLayout = new CardLayout();
		this.mainPanel = new JPanel(cardLayout);
		
		mainPanel.add(menuPanel, "MENU");
		mainPanel.add(nicknamePanel, "NICKNAME");
		mainPanel.add(gameScreenPanel, "GAME");
		mainPanel.add(optionsPanel, "OPTIONS");
		mainPanel.add(creditsPanel, "CREDITS");
		mainPanel.add(defeatPanel, "DEFEAT");
		
		setLayout(new BorderLayout());	
		add(mainPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setVisible(true);
		
	}
	public GameScreenPanel getGameScreenPanel() { return gameScreenPanel; }
	public MenuPanel getMenuPanel() { return menuPanel; }
    public NicknamePanel getNicknamePanel() { return nicknamePanel; }
    public OptionsPanel getOptionsPanel() { return optionsPanel; } 
    public DefeatPanel getDefeatPanel() { return defeatPanel; }
    public CreditsPanel getCreditsPanel() { return creditsPanel; }
    
    public void showMenu() { cardLayout.show(mainPanel, "MENU"); }
    public void showNickname() { cardLayout.show(mainPanel, "NICKNAME"); }
    public void showGame() { 
    	cardLayout.show(mainPanel, "GAME"); 
        SwingUtilities.invokeLater(() ->
        gameScreenPanel.requestFocusInWindow());
        }
    public void showOptions() {cardLayout.show(mainPanel,  "OPTIONS"); }
    public void showDefeat() { cardLayout.show(mainPanel, "DEFEAT"); }
    public void showCredits() { cardLayout.show(mainPanel, "CREDITS"); }

	@Override
	public void update(Observable o, Object arg) {
		if (!(arg instanceof GameState)) return;
		
		// public enum GameState {MENU,NICKNAME,CREDITS,OPTIONS,PLAY,PAUSE,DEFEAT}
		GameState state = (GameState) arg;
		switch(state) {
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
		case PAUSE:
			break;
		case GAME_OVER:
			showDefeat();
			break;
		default:
			break;
		}
		
	}
}
