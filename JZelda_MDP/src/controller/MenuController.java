package controller;


import javax.swing.*;
import model.GameModel;
import model.GameModel.GameState;
import view.GamePanel;
import view.MenuPanel;
import view.NicknamePanel;
import view.OptionsPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MenuController implements ActionListener {	
	private GameModel model;
	private MenuPanel view;
	private NicknamePanel nicknameView;
	private OptionsPanel optionsView;
	private GamePanel gamePanel;
	
	public MenuController(GameModel model, MenuPanel view, NicknamePanel nicknameView, OptionsPanel optionsView) {
		this.model = model;
		this.view = view;		
		this.view.setMenuListeners(this);
		this.nicknameView = nicknameView;
		this.nicknameView.setNicknameListeners(this);
		this.optionsView = optionsView;
		this.optionsView.setOptionsListeners(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
		case "start": 
			System.out.println("Start Game pressed!");
			model.setGameState(GameState.NICKNAME);
			break;
		case "options":
			System.out.println("Options pressed!");
			model.setGameState(GameState.OPTIONS);
			break;
		case "stats":
			System.out.println("Stats pressed");
			break;
		case "credits":
			System.out.println("Credits pressed!");
			model.setGameState(GameState.CREDITS);
			break;
		case "exit":
			System.exit(0);
			break;
		case "confirm":
			String nickname = nicknameView.getNickname();
			if (nickname.isBlank()) {
				System.out.println("Nickname vuoto.");
				break;
			}
			System.out.println("Nickname confirmed!");
			System.out.println(nickname);
			model.setGameState(GameState.PLAY);
			model.startGameThread();
			break;
		case "return":
			System.out.println("Back to menu!");
			model.setGameState(GameState.MENU);
			break;
		case "audio":
			System.out.println(optionsView.isAudioOn() ? "Audio toggled on" : "Audio toggled off");
			break;
		case "reset":
			System.out.println("Stats reset!");
			break;
		}
		
	}

}
