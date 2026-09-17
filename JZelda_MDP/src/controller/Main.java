package controller;

import javax.swing.UIManager;

import audio.AudioManager;
import model.GameModel;
import view.FontManager;
import view.MainFrame;

/**
 * The Main class. It sets the UI standard font, initializes the model, the
 * audio manager, the controllers and the MainFrame containing the views.
 */
public class Main {

	/**
	 * Starts the application
	 *
	 * @param args the command line arguments, not used
	 */
	public static void main(String[] args) {
		UIManager.put("Label.font", FontManager.getFont(44f));
		UIManager.put("Button.font", FontManager.getFont(44f));
		UIManager.put("CheckBox.font", FontManager.getFont(44f));
		UIManager.put("TextField.font", FontManager.getFont(44f));

		GameModel model = GameModel.getInstance();
		MainFrame frame = new MainFrame(model);
		model.addObserver(frame);
		model.addObserver(frame.getGameScreenPanel());

		GameController gameController = new GameController(model, frame.getGameScreenPanel());

		MenuController menuController = new MenuController(model, frame.getMenuPanel(), frame.getNicknamePanel(),
				frame.getOptionsPanel(), gameController, frame.getDefeatPanel(), frame.getCreditsPanel(),
				frame.getGameScreenPanel().getPausePanel(), frame.getStatsPanel());

		AudioManager.getInstance().playLoop("src/audio/bgm_menu.wav");
		// startDirectlyInGame(model, gameController);

	}

	/**
	 * Skips menu to get directly in game
	 * 
	 * @param model          the game model
	 * @param gameController the game controller
	 */
	@SuppressWarnings("unused")
	private static void startDirectlyInGame(GameModel model, GameController gameController) {
		model.setGameState(GameModel.GameState.PLAY);
		gameController.startGameThread();
	}

}
