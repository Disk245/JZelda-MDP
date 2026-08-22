package controller;

import javax.swing.UIManager;

import model.GameModel;
import view.FontManager;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
	    UIManager.put("Label.font", FontManager.getFont(44f));
	    UIManager.put("Button.font", FontManager.getFont(44f));
	    UIManager.put("CheckBox.font", FontManager.getFont(44f));
	    UIManager.put("TextField.font", FontManager.getFont(44f));
	    
	    
		GameModel model = new GameModel();
		MainFrame frame = new MainFrame(model);
		model.addObserver(frame);
		model.addObserver(frame.getGameScreenPanel());
		
		GameController gameController = new GameController(model, frame.getGameScreenPanel());
		
		MenuController menuController = new MenuController(model, frame.getMenuPanel(), 
				frame.getNicknamePanel(), frame.getOptionsPanel(), gameController);
		//startDirectlyInGame(model, gameController);

	}
	
	/**
	 * Skips menu to get directly in game
	 * @param model the game model
	 * @param gameController the game controller
	 */
	@SuppressWarnings("unused")
	private static void startDirectlyInGame(
	        GameModel model,
	        GameController gameController
	) {
	    model.setGameState(GameModel.GameState.PLAY);
	    gameController.startGameThread();
	}

}
