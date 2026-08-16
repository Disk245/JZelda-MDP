package controller;

import javax.swing.*;
import model.GameModel;
import view.GamePanel;
import view.GameScreenPanel;

public class GameController {
	private GameModel model;
	private GameScreenPanel view;
	
	public GameController(GameModel model, GameScreenPanel view) {
		this.model = model;
		this.view = view;
	}
}
