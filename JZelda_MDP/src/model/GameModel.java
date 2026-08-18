package model;

import java.util.Observable;

import model.Character.Direction;


@SuppressWarnings("deprecation")
public class GameModel extends Observable {
	
	public enum GameState {MENU,NICKNAME,CREDITS,OPTIONS,PLAY,PAUSE,DEFEAT}
	
	GameState gameState = GameState.MENU;

	private Player player = new Player("1", 100, 100, "aa", 4);
	private Direction movementDirection;

	private final Tile[] tiles = {
		    new Tile(0, false), 	// SAND
		    new Tile(1, true),  	// ROCK
		    new Tile(2, false),  	// GRASS
		    new Tile(3, true)  		// TREE
		};
	
	public GameState getGameState() { return gameState; }
	public void setGameState(GameState state) {
		this.gameState = state;
		setChanged();
		notifyObservers(state);
	}
	
	public void updateGame() {
		movePlayer();
		updatePlayer();
		updateMap();
		
		setChanged();
		notifyObservers(this);
	}
	
	public void updateMap() {
	}
	
	/**
	 * Registers player movement start. It is separate to avoid
	 * moving the player directly at each keyboard press,
	 * making the movement clunky
	 * @param direction the direction to face
	 */
	public void startPlayerMovement(Direction direction) {
		movementDirection = direction;
		player.setDirection(direction);
	}
	
	public void movePlayer() {
		if (movementDirection == null) return;
		
		player.setDirection(movementDirection);
		int speed = player.getCharacterSpeed();
		switch(movementDirection) {
			case UP:
				player.move(0, -speed);
				break;
			case DOWN:
				player.move(0, +speed);
				break;
			case LEFT:
				player.move(-speed, 0);
				break;
			case RIGHT:
				player.move(speed, 0);
				break;
		}
	}
	
	/**
	 * Unregisters player movement. Checks the current direction
	 * so it won't stop movement if two keys were pressed at
	 * the same time.
	 * @param direction the direction the player is facing
	 */
	public void stopPlayerMovement(Direction direction) {
	    if (movementDirection == direction) {
	        movementDirection = null;
	        player.stop();
	    }
	}
	
	public void updatePlayer() { player.update(); }
	public Player getPlayer() { return player; }
	
	
	
	
}
