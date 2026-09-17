package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import audio.AudioManager;
import model.Character.Direction;
import model.GameModel;
import model.GameModel.GameState;
import model.WorldMap;
import model.gameObjects.CoinObject;
import model.gameObjects.HeartObject;
import view.GameScreenPanel;

/**
 * Controls the actions performed in game. It implements the KeyListener interface
 * to respond to key press, Runnable to handle the game loop, and Observer to
 * receive updates on item pickup.
 */
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

	/**
	 * Initializes the game controller. The audioManager field represents the single
	 * instance of the audio manager, used to play tracks and sfx. The isInBossRoom
	 * boolean stores the information needed to change the music in the boss room.
	 * The previousPlayerHealth field is necessary to check whether or not to play
	 * the "hurt" sound
	 * 
	 * @param model the game model
	 * @param view  the game view
	 */
	public GameController(GameModel model, GameScreenPanel view) {
		this.model = model;
		this.view = view;
		this.audioManager = AudioManager.getInstance();
		this.isInBossRoom = model.isCurrentRoomBossRoom();
		this.previousPlayerHealth = model.getPlayer().getCurrentHealth();

		model.addObserver(this);
		view.setFocusable(true);
		view.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Handles responses on key press. It communicates to the model the player's
	 * movement intentions and interactions, and handles pause by changing the GameState value.
	 * 
	 * @param e the key press event
	 */
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

	/**
	 * Stops the player's movement on key release.
	 * 
	 * @param e the key release event
	 */
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
	 * Starts the game, creating a new gameThread if not initialised. Resets endSoundPlayed
	 * and updates previousPlayerHealth and isInBossRoom to ensure the game keeps working even after
	 * restarting during the same session.
	 */
	public void startGameThread() {
		if (gameThread == null) {
			gameThread = new Thread(this);
			gameThread.start();
		}
		previousPlayerHealth = model.getPlayer().getCurrentHealth();

		endSoundPlayed = false;
		isInBossRoom = model.isCurrentRoomBossRoom();

		audioManager.playLoop("src/audio/bgm_explore.wav");
	}

	/**
	 * Updates the game loop. The drawInterval variable sets a target of 60 updates per
	 * second, making the thread sleep until the next refresh is reached. It
	 * takes the current GameState value and uses it to update the game if it's
	 * PLAY, or plays the end sound once if the state changes to GAME_OVER or WIN.
	 * The game loop keeps running in these states.
	 */
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

				boolean currentlyInBossRoom = model.isCurrentRoomBossRoom();
				if (currentlyInBossRoom != isInBossRoom) {
					if (currentlyInBossRoom) {
						audioManager.playLoop("src/audio/bgm_boss.wav");
					} else {
						audioManager.playLoop("src/audio/bgm_explore.wav");
					}

					isInBossRoom = currentlyInBossRoom;
				}
			}

			if ((state == GameState.GAME_OVER || state == GameState.WIN) && !endSoundPlayed) {

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

	/**
	 * Resets endSoundPlayed and updates previousPlayerHealth. Used in the
	 * MenuController before going back to the menu
	 */
	public void resetGameOverState() {
		endSoundPlayed = false;
		previousPlayerHealth = model.getPlayer().getCurrentHealth();
	}

	/**
	 * Plays the corresponding sound to the update received from the model when a coin
	 * or heart is picked up
	 *
	 * @param o the model sending the update
	 * @param arg the picked up object
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof CoinObject) {
			audioManager.play("src/audio/driken5482_retro_coin.wav");
		} else if (arg instanceof HeartObject) {
			audioManager.play("src/audio/freesound_community_powerup.wav");
		}

	}
}
