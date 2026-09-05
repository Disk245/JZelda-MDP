package controller;

import javax.swing.*;

import audio.AudioManager;
import model.GameModel;
import model.GameModel.GameState;
import view.CreditsPanel;
import view.GameEndPanel;
import view.MenuPanel;
import view.NicknamePanel;
import view.OptionsPanel;
import view.PausePanel;
import view.StatsPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuController implements ActionListener {
	private final AudioManager audioManager = AudioManager.getInstance();
	private GameModel model;
	private MenuPanel view;
	private NicknamePanel nicknameView;
	private OptionsPanel optionsView;
	private CreditsPanel creditsview;
	private GameEndPanel defeatView;
	private GameController gameController;
	private PausePanel pauseView;
	private StatsPanel statsView;

	public MenuController(GameModel model, MenuPanel view, NicknamePanel nicknameView, OptionsPanel optionsView,
			GameController gameController, GameEndPanel defeatView, CreditsPanel creditsView, PausePanel pauseView, StatsPanel statsView) {
		this.model = model;
		this.view = view;
		this.view.setMenuListeners(this);
		this.nicknameView = nicknameView;
		this.nicknameView.setNicknameListeners(this);
		this.optionsView = optionsView;
		this.optionsView.setOptionsListeners(this);
		this.gameController = gameController;
		this.defeatView = defeatView;
		this.defeatView.setDefeatListener(this);
		this.creditsview = creditsView;
		this.creditsview.setCreditsListener(this);
		this.pauseView = pauseView;
		this.pauseView.setPauseListener(this);
		this.statsView = statsView;
		this.statsView.setStatsListener(this);
	}

	/**
	 * Opens the url from the user's browser
	 * @param url the url to open
	 */
	public void openUrl(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch(action) {
		case "start": 
			model.setGameState(GameState.NICKNAME);
			break;
		case "options":

			model.setGameState(GameState.OPTIONS);
			break;
		case "stats":
			model.setGameState(GameState.STATS);
			break;
		case "credits":
			model.setGameState(GameState.CREDITS);
			break;
		case "exit":
			System.exit(0);
			break;
		case "confirm":
			String nickname = nicknameView.getNickname().trim();
			if (nickname.isBlank()) {
				break;
			}
			nicknameView.clearNickname();
			model.resetGame(nickname);
			model.setGameState(GameState.PLAY);
			gameController.startGameThread();
			break;
		case "return":
			audioManager.playLoop("src/audio/bgm_menu.wav");
		    gameController.resetGameOverState();
		    model.setGameState(GameState.MENU);
		    break;
		case "audio":
		    boolean audioEnabled = optionsView.isAudioOn();
		    audioManager.setAudioEnabled(audioEnabled);
			break;
		case "reset":
		    model.getStatsManager().resetStats();
		    model.getStatsManager().writeToFile();
		    statsView.refreshStats();
		    break;
		case "link_swing":
			openUrl("https://www.youtube.com/playlist?list=PLU8dZfh0ZIUn7-TDZfSmX9QRnBgmdJJWD");
			break;
		case "link_2dgame":
			openUrl("https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq");
		break;
		case "link_github":
			openUrl("https://github.com/sapienza-metodologie-di-programmazione/guide?tab=readme-ov-file");
			break;
		case "link_sounds":
			openUrl("https://pixabay.com");
			break;
		}
		
	}

}
