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
		    new Tile(0, false), 	// GRASS
		    new Tile(1, true),  	// TREE
		    new Tile(2, false),  	// SAND
		    new Tile(3, true),  	// CLIFF BOTTOM LEFT
		    new Tile(4, true),		// CLIFF BOTTOM
		    new Tile(5, true),		// CLIFF BOTTOM RIGHT		
		    new Tile(6, true),		// CLIFF RIGHT
		    new Tile(7, true),		// CLIFF TOP RIGHT
		    new Tile(8, true),		// CLIFF TOP
		    new Tile(9, true),		// CLIFF TOP LEFT
		    new Tile(10, true),		// CLIFF LEFT
		    new Tile(11, true),		// WATER 1
		    new Tile(12, true),		// WATER 2
		    new Tile(13, true),		// WATER SHORE
		    new Tile(14, true),		// ROCK
		    new Tile(15, true),		// DUNGEON WALL
		    new Tile(16, true),		// DUNGEON FLOOR
		};
	
	private final WorldMap worldMap = new WorldMap();
	private int currentRoomRow;
	private int currentRoomColumn;
	private Room currentRoom;
	
	public GameModel() {
	    setCurrentRoom(3, 0);
	    setPlayerTilePosition(2, 8);
	}
	
	public GameState getGameState() { return gameState; }
	public void setGameState(GameState state) {
		this.gameState = state;
		setChanged();
		notifyObservers(state);
	}
	
	public void updateGame() {
		updateMap();
		
		movePlayer();
		updatePlayer();
		
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Moves the player to an adjacent room.
	 * First, it checks if the room can be reached through changeRoom().
	 * If true, changes room and sets the player in the corresponding
	 * position.
	 */
	public void updateMap() {
	    int maxX = GameConfig.SCREEN_WIDTH - GameConfig.TILE_SIZE;
	    int maxY = GameConfig.SCREEN_HEIGHT - GameConfig.TILE_SIZE;

	    // Left
	    if (player.getX() < 0) {
	        if (changeRoom(currentRoomRow, currentRoomColumn - 1))
	            player.setX(maxX);
	        else 
	            player.setX(0);	        
	        return;
	    }

	    // Right
	    if (player.getX() > maxX) {
	        if (changeRoom(currentRoomRow, currentRoomColumn + 1))
	            player.setX(0);
	        else 
	            player.setX(maxX);	        
	        return;
	    }

	    // Top
	    if (player.getY() < 0) {
	        if (changeRoom(currentRoomRow - 1, currentRoomColumn))
	            player.setY(maxY);
	        else 
	            player.setY(0);	        
	        return;
	    }

	    // Bottom
	    if (player.getY() > maxY) {
	        if (changeRoom(currentRoomRow + 1, currentRoomColumn))
	            player.setY(0);
	        else 
	            player.setY(maxY);
	        return;
	    }
	}
	
	/**
	 * Checks if the room can be changed.
	 * If false, also keeps the player on the edge of the room
	 * @param row the current row in the world map
	 * @param column the current column in the world map
	 * @return if the room can be changed
	 */
	private boolean changeRoom(int row, int column) {
	    if (row < 0 || row >= 4 ||
	        column < 0 || column >= 3) {
	        return false;
	    }

	    Room nextRoom = worldMap.getRoom(row, column);

	    if (nextRoom == null) {
	        return false;
	    }

	    currentRoomRow = row;
	    currentRoomColumn = column;
	    currentRoom = nextRoom;
	    return true;
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
	
	/**
	 * Moves the player.
	 */
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
	
	private void setCurrentRoom(int row, int column) {
	    currentRoomRow = row;
	    currentRoomColumn = column;
	    currentRoom = worldMap.getRoom(row, column);
	}
	public Room getCurrentRoom() { return currentRoom; }
	
	public int getCurrentRoomRow() { return currentRoomRow; }
	public void setCurrentRoomRow(int currentRoomRow) { this.currentRoomRow = currentRoomRow; }
	
	public int getCurrentRoomColumn() { return currentRoomColumn; }
	public void setCurrentRoomColumn(int currentRoomColumn) { this.currentRoomColumn = currentRoomColumn; }
	
	private void setPlayerTilePosition(int tileX, int tileY) {
	    player.setX(tileX * GameConfig.TILE_SIZE);
	    player.setY(tileY * GameConfig.TILE_SIZE);
	}
	
	
	
}
