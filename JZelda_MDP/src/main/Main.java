package main;

import controller.GameController;
import controller.MenuController;
import model.GameModel;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		GameModel model = new GameModel();
		MainFrame frame = new MainFrame(model);
		model.addObserver(frame);
		
		MenuController menuController = new MenuController(model, frame.getMenuPanel(), 
				frame.getNicknamePanel(), frame.getOptionsPanel());
		
        GameController gameController = new GameController(model, frame.getGameScreenPanel());
        model.addObserver(frame.getGameScreenPanel());
		

	}

}
