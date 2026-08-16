package main;

import javax.swing.SwingUtilities;

import controller.MenuController;
import model.GameModel;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		GameModel model = new GameModel();
		MainFrame frame = new MainFrame();
		model.addObserver(frame);
		
		MenuController menuController = new MenuController(model, frame.getMenuPanel(), 
				frame.getNicknamePanel(), frame.getOptionsPanel());
        //GameController gameController = new GameController(model, frame.getGamePanel());
		

	}

}
