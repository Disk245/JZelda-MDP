package controller;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import audio.AudioManager;
import model.Character.Direction;
import model.GameModel;
import model.GameModel.GameState;
import model.Pickable;
import model.WorldMap;
import view.FontManager;
import view.GamePanel;
import view.GameScreenPanel;

@SuppressWarnings("deprecation")
public class GameController implements KeyListener, Runnable, Observer {
	private GameModel model;
	private GameScreenPanel view;
	private Thread gameThread;
	private int FPS = 60;
	private final AudioManager audioManager;
	private boolean isInBossRoom;
	private int previousPlayerHealth;
	private boolean endSoundPlayed = false;

	public GameController(GameModel model, GameScreenPanel view) {
		this.model = model;
		this.view = view;
		this.audioManager = AudioManager.getInstance();
		this.isInBossRoom = isBossRoom();
		this.previousPlayerHealth = model.getPlayer().getCurrentHealth();
		
		model.addObserver(this);
		view.setFocusable(true);
		view.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (model.getGameState() != GameState.PLAY && model.getGameState() != GameState.DIALOGUE
				&& model.getGameState() != GameState.PAUSE) {
			return;
		}
		int code = e.getKeyCode();
		System.out.println("Input registered: " + e.getKeyChar());
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			model.startPlayerMovement(Direction.UP);
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			model.startPlayerMovement(Direction.DOWN);
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			model.startPlayerMovement(Direction.LEFT);
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			model.startPlayerMovement(Direction.RIGHT);
		}

		if (code == KeyEvent.VK_E) {
			GameState previousState = model.getGameState();

			model.interact();

			if (previousState != model.getGameState() && model.getGameState() == GameState.DIALOGUE)
				audioManager.play("src/audio/freesound_community_beep.wav");
		}

		if (code == KeyEvent.VK_R && model.getGameState() == GameState.DIALOGUE) {
			model.BuyItem(model.getPlayer(), model.getCurrentShopItem());
		}

		if (code == KeyEvent.VK_SPACE) {
			if (model.getPlayer().canAttack()) {
				audioManager.play("src/audio/oxidvideos_swing.wav");
				model.handleAttack();
			}
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			if (model.getGameState() == GameState.PLAY) 
				model.setGameState(GameState.PAUSE);	
			else if ((model.getGameState() == GameState.PAUSE))
				model.setGameState(GameState.PLAY);
		}

		// Cheat and debugging
		if (code == KeyEvent.VK_P) {
			model.getPlayer().setGodMode(model.getPlayer().isGodMode() ? false : true);
			if (model.getPlayer().isCollisionOn())
				model.getPlayer().setCollisionOn(false);
			else if (!model.getPlayer().isCollisionOn())
				model.getPlayer().setCollisionOn(true);
			System.out.println("Collision status: " + model.getPlayer().isCollisionOn());
			System.out.println("God mode: " + model.getPlayer().isGodMode());
		}

		if (code == KeyEvent.VK_O) {
			if (model.getPlayer().getCharacterSpeed() <= 6)
				model.getPlayer().setCharacterSpeed(model.getPlayer().getCharacterSpeed() + 10);
			else
				model.getPlayer().setCharacterSpeed(model.getPlayer().getCharacterSpeed() - 10);
			System.out.println("Speed incremented: " + model.getPlayer().getCharacterSpeed());
		}

		if (code == KeyEvent.VK_L) {
			WorldMap worldMap = model.getWorldMap();
			if (worldMap.getKillCounter() < 10)
				worldMap.registerEnemyKill(10);
			else
				worldMap.resetKillCounter();
			System.out.println(worldMap.getKillCounter());
		}

		if (code == KeyEvent.VK_K) {
			model.getPlayer().addCoins(200);
			System.out.println("Money added. New money: " + model.getPlayer().getCoins());
		}

		if (code == KeyEvent.VK_J) {
			model.getPlayer().takeDamage(1);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			model.stopPlayerMovement(Direction.UP);
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			model.stopPlayerMovement(Direction.DOWN);
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			model.stopPlayerMovement(Direction.LEFT);
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			model.stopPlayerMovement(Direction.RIGHT);
		}

	}
	
	/**
	 * Starts the game
	 */
	public void startGameThread() {
		if (gameThread == null) {
			gameThread = new Thread(this);
			gameThread.start();
		}
		previousPlayerHealth = model.getPlayer().getCurrentHealth();

		endSoundPlayed = false;
		isInBossRoom = isBossRoom();

		audioManager.playLoop("src/audio/bgm_explore.wav");
	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {
			
			GameState state = model.getGameState();
			
			if (state == GameState.PLAY) {

				model.updateGame();

				int currentPlayerHealth = model.getPlayer().getCurrentHealth();
				if (model.getPlayer().getCurrentHealth() < previousPlayerHealth) {
					audioManager.play("src/audio/driken5482_retro_hurt_2.wav");
					previousPlayerHealth = currentPlayerHealth;
				}

				boolean currentlyInBossRoom = isBossRoom();
				if (currentlyInBossRoom != isInBossRoom) {
					if (currentlyInBossRoom) {
						audioManager.playLoop("src/audio/bgm_boss.wav");
					} else {
						audioManager.playLoop("src/audio/bgm_explore.wav");
					}

					isInBossRoom = currentlyInBossRoom;
				}
			}

			if ((state == GameState.GAME_OVER || state == GameState.WIN)
			        && !endSoundPlayed) {

			    audioManager.stopLoop();

			    if (state == GameState.WIN) {
			        audioManager.play("src/audio/mori_sound_win.wav");
			    } else {
			        audioManager.play("src/audio/alphix_game_over.wav");
			    }

			    endSoundPlayed = true;
			}

			try {
				double remainingTime = nextDrawTime - System.nanoTime();

				remainingTime /= 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isBossRoom() {
		return model.getCurrentRoomRow() == 0 && model.getCurrentRoomColumn() == 1;
	}

	public void resetGameOverState() {
		endSoundPlayed = false;
		previousPlayerHealth = model.getPlayer().getCurrentHealth();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Pickable) {
			audioManager.play("src/audio/chieuk_coin.wav");
		}
		
	}
}
