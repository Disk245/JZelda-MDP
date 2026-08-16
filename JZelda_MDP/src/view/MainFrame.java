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
    private GamePanel gamePanel;
    private OptionsPanel optionsPanel;
	
	private GameModel model;
	
	
	public MainFrame(){
		super("JZelda");
		
		this.menuPanel = new MenuPanel();
		this.nicknamePanel = new NicknamePanel();
		this.gamePanel = new GamePanel();
		this.optionsPanel = new OptionsPanel();
		
		this.cardLayout = new CardLayout();
		this.mainPanel = new JPanel(cardLayout);
		
		this.add(menuPanel);
		mainPanel.add(menuPanel, "MENU");
		mainPanel.add(nicknamePanel, "NICKNAME");
		mainPanel.add(gamePanel, "GAME");
		mainPanel.add(optionsPanel, "OPTIONS");
		
		setLayout(new BorderLayout());	
		add(mainPanel, BorderLayout.CENTER);
		setSize(1280,1200);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setVisible(true);
		
	}
	
	public MenuPanel getMenuPanel() { return menuPanel; }
    public GamePanel getGamePanel() { return gamePanel; }
    public NicknamePanel getNicknamePanel() { return nicknamePanel; }
    public OptionsPanel getOptionsPanel() { return optionsPanel; }
    
    public void showMenu() { cardLayout.show(mainPanel, "MENU"); }
    public void showNickname() { cardLayout.show(mainPanel, "NICKNAME"); }
    public void showGame() { cardLayout.show(mainPanel, "GAME"); }
    public void showOptions() {cardLayout.show(mainPanel,  "OPTIONS"); }

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
			break;
		case OPTIONS:
			showOptions();
			break;
		case PLAY:
			showGame();
			break;
		case PAUSE:
			break;
		case DEFEAT:
			break;
		default:
			break;
		}
		
	}
}
