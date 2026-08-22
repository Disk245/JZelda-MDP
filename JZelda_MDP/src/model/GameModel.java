package model;

import java.awt.Rectangle;
import java.util.Observable;

import model.Character.CharacterState;
import model.Character.Direction;


@SuppressWarnings("deprecation")
public class GameModel extends Observable {
	
	public enum GameState {MENU,NICKNAME,CREDITS,OPTIONS,PLAY,PAUSE,DEFEAT,DIALOGUE}
	
	GameState gameState = GameState.MENU;

	private Player player = new Player("1", 100, 100, "aa", 4);
	private Direction movementDirection;
	
	private final WorldMap worldMap = new WorldMap();
	private int currentRoomRow;
	private int currentRoomColumn;
	private Room currentRoom;
	private GameObject currentShopItem;
	private Character currentDialogueCharacter;
	
	private String[] currentDialogue;
	
	private CollisionChecker collisionChecker = new CollisionChecker(this);
	
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
		
		if (player.getCharacterState() != CharacterState.ATTACKING && player.getCharacterState() != CharacterState.DEAD) {
			movePlayer();
		}
		updateMap();
		updateEntities();
		updatePlayer();
		
		setChanged();
		notifyObservers(this);
	}
	
	private void updateEntities() {
		worldMap.unlockFinalDoor();
		for (Entity e : currentRoom.getEntities()) {
			
		}
		
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
	        if (changeRoom(currentRoomRow - 1, currentRoomColumn)) {
	        	if (currentRoomRow == 0 && currentRoomColumn == 1) {
	        		player.setY(maxY - GameConfig.TILE_SIZE);
	        	}
	        	else
	        		player.setY(maxY);
	        }
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
	    if (player.getCharacterState() == CharacterState.ATTACKING
	            || player.getCharacterState() == CharacterState.DEAD) {
	        return;
	    }
	    
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
		
		// Tile Collision checking
		player.setColliding(false);
		
		if (player.isCollisionOn()) {
			collisionChecker.checkTileCollision(player);
		
			if (!player.isColliding()) {
		        for (Entity entity : currentRoom.getEntities()) {
		            collisionChecker.checkEntityCollision(player, entity);
		            if (player.isColliding()) {
		                break;
		            }
		        }
		    }
		}
		
		if (!player.isColliding()) {
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
	        if (player.getCharacterState() != CharacterState.ATTACKING) {
	            player.stop();
	        }
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
	
	public WorldMap getWorldMap() { return worldMap; }
	
	private void setPlayerTilePosition(int tileX, int tileY) {
	    player.setX(tileX * GameConfig.TILE_SIZE);
	    player.setY(tileY * GameConfig.TILE_SIZE);
	}
	
	public void interact() {
		if (gameState == GameState.DIALOGUE) {
		    currentDialogue = null;
		    currentShopItem = null;

		    if (currentDialogueCharacter != null) {
		        currentDialogueCharacter.setDirection(Direction.DOWN);
		        currentDialogueCharacter = null;
		    }

		    gameState = GameState.PLAY;
		    notifyListeners();
		    return;
		}
		
	    Entity entity = collisionChecker.findInteractable(player);
	    if (entity == null) {
	    	currentShopItem = null;
	    	return;
	    }
	    
	    if (entity instanceof GameObject g && g instanceof Purchasable) {
	    	currentShopItem = g;
	    }
	    else { currentShopItem = null; }
	    
	    String[] dialogue = player.interact(entity);

	    if (dialogue != null) {

	        if (entity instanceof Character npc) {
	            currentDialogueCharacter = npc;

	            switch (player.getDirection()) {
	                case UP:
	                    npc.setDirection(Direction.DOWN);
	                    break;
	                case DOWN:
	                    npc.setDirection(Direction.UP);
	                    break;
	                case RIGHT:
	                    npc.setDirection(Direction.LEFT);
	                    break;
	                case LEFT:
	                    npc.setDirection(Direction.RIGHT);
	                    break;
	            }
	        }

	        currentDialogue = dialogue;
	        player.stop();
	        gameState = GameState.DIALOGUE;
	        notifyListeners();
	    }
	    
	}
	
	public void BuyItem(Player player, GameObject currentShopItem) {
		if (!(currentShopItem instanceof Purchasable p)) { return; }
		if (!player.canBuy(p.getPrice())) {
			currentDialogue = new String[] {"You don't have enough coins."};
			notifyListeners();
			return;
		}
		player.addToInventory(currentShopItem);
		player.removeCoins(p.getPrice());
		currentRoom.removeEntity(currentShopItem);
		p.ApplyEffect(player);
		
		currentDialogue = new String[] {"Enjoy your purchase!"};
		
		currentShopItem = null;
		notifyListeners();
	}
	
	public void notifyListeners() {
	    setChanged();
	    notifyObservers();
	}
	
	public void handleAttack() {
	    if (!player.canAttack()) {
	        return;
	    }
	    
	    player.attack();

	    Rectangle attackArea = player.getAttackArea();

	    for (Entity entity : currentRoom.getEntities()) {
	        if (entity instanceof Character target && target != player) {

	            if (collisionChecker.checkAttackCollision(attackArea, target)) {
	                target.takeDamage(player.getAttackDamage());
	            }

	        }
	    }
	}
	
	public String[] getCurrentDialogue() { return currentDialogue; }
	public GameObject getCurrentShopItem() { return currentShopItem; }
	
}
