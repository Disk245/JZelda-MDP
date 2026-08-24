package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import model.Character.CharacterState;
import model.Character.Direction;
import model.gameObjects.ChestObject;

@SuppressWarnings("deprecation")
public class GameModel extends Observable {

	public enum GameState {
		MENU, NICKNAME, CREDITS, OPTIONS, PLAY, PAUSE, GAME_OVER, DIALOGUE
	}

	GameState gameState = GameState.MENU;

	private Player player = new Player("1", 100, 100, "aa", 4);
	private Direction movementDirection;

	private WorldMap worldMap = new WorldMap();
	private int currentRoomRow;
	private int currentRoomColumn;
	private Room currentRoom;
	private GameObject currentShopItem;
	private Character currentDialogueCharacter;
	private List<Projectile> projectiles = new ArrayList<>();

	private String[] currentDialogue;

	private CollisionChecker collisionChecker = new CollisionChecker(this);

	public GameModel() {
		setCurrentRoom(3, 0);
		setPlayerTilePosition(2, 8);
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState state) {
		this.gameState = state;
		setChanged();
		notifyObservers(state);
	}

	/**
	 * Updates the whole game status. It checks the map, players, entities and
	 * projectiles. It also checks player death
	 */
	public void updateGame() {

		if (player.isInKnockback()) {
			moveKnockback(player);
		} else if (player.getCharacterState() != CharacterState.ATTACKING
				&& player.getCharacterState() != CharacterState.HURT
				&& player.getCharacterState() != CharacterState.DEAD) {
			movePlayer();
		}
		updateMap();
		updateEntities();
		updateProjectiles();
		updatePlayer();

		if (player.isDeathAnimationOver()) {
			setGameState(GameState.GAME_OVER);
			return;
		}

		setChanged();
		notifyObservers(this);

	}

	/**
	 * Updates the entities. The method cycles through all entities and determines
	 * their movement, death and removal. The iterator is needed for safe removal of
	 * dead entities.
	 */
	private void updateEntities() {
		Iterator<Entity> iterator = currentRoom.getEntities().iterator();

		while (iterator.hasNext()) {
			Entity entity = iterator.next();

			if (entity instanceof Enemy enemy) {
				enemy.update();

				if (enemy.isDeathAnimationOver()) {
					iterator.remove();
					worldMap.registerEnemyKill();
					continue;
				}

				if (enemy.isInKnockback()) {
					moveKnockback(enemy);
					continue;
				}

				CharacterState state = enemy.getCharacterState();
				if (state == CharacterState.DEAD || state == CharacterState.HURT || state == CharacterState.ATTACKING) {
					continue;
				}

				enemy.updateBehavior(player, this);
			}
		}
		worldMap.unlockFinalDoor();
	}

	/**
	 * Moves the player to an adjacent room. First, it checks if the room can be
	 * reached through changeRoom(). If true, changes room and sets the player in
	 * the corresponding position.
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
			projectiles.clear();
			return;
		}

		// Right
		if (player.getX() > maxX) {
			if (changeRoom(currentRoomRow, currentRoomColumn + 1))
				player.setX(0);
			else
				player.setX(maxX);
			projectiles.clear();
			return;
		}

		// Top
		if (player.getY() < 0) {
			if (changeRoom(currentRoomRow - 1, currentRoomColumn)) {
				if (currentRoomRow == 0 && currentRoomColumn == 1) {
					player.setY(maxY - GameConfig.TILE_SIZE);
				} else
					player.setY(maxY);
			} else
				player.setY(0);
			projectiles.clear();
			return;
		}

		// Bottom
		if (player.getY() > maxY) {
			if (changeRoom(currentRoomRow + 1, currentRoomColumn))
				player.setY(0);
			else
				player.setY(maxY);
			projectiles.clear();
			return;
		}
	}

	/**
	 * Checks if the room can be changed. If false, also keeps the player on the
	 * edge of the room
	 * 
	 * @param row    the current row in the world map
	 * @param column the current column in the world map
	 * @return if the room can be changed
	 */
	private boolean changeRoom(int row, int column) {
		if (row < 0 || row >= 4 || column < 0 || column >= 3) {
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
	 * Registers player movement start. It is separate to avoid moving the player
	 * directly at each keyboard press, making the movement clunky
	 * 
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

	private void movePlayer() {
		if (movementDirection == null) {
			return;
		}

		player.setDirection(movementDirection);

		if (!moveCharacter(player, true, null)) {
			player.stop();
		}
	}

	/**
	 * Checks whether or not the character can move.
	 * 
	 * @param character     the character trying to move
	 * @param walking       if the character is currently walking or not. Needed to
	 *                      not change the hurt state
	 * @param ignoredEntity needed for the attacker's collision area not to be
	 *                      considered by the knockback
	 * @return true if movement is possible
	 */
	private boolean moveCharacter(Character character, boolean walking, Entity ignoredEntity) {
		character.setColliding(false);

		if (character.isCollisionOn()) {
			collisionChecker.checkTileCollision(character);

			if (!character.isColliding()) {
				for (Entity entity : currentRoom.getEntities()) {
					if (entity == character || entity == ignoredEntity) {
						continue;
					}

					collisionChecker.checkEntityCollision(character, entity);

					if (character.isColliding()) {
						break;
					}
				}
			}

			if (!character.isColliding() && character != player && player != ignoredEntity) {
				collisionChecker.checkEntityCollision(character, player);
			}

			if (character.isColliding()) {
				return false;
			}
		}

		int speed = character.getCharacterSpeed();
		int deltaX = 0;
		int deltaY = 0;

		switch (character.getDirection()) {
		case UP:
			deltaY = -speed;
			break;
		case DOWN:
			deltaY = speed;
			break;
		case LEFT:
			deltaX = -speed;
			break;
		case RIGHT:
			deltaX = speed;
			break;
		}

		int nextX = character.getX() + deltaX;
		int nextY = character.getY() + deltaY;

		int maxX = GameConfig.SCREEN_WIDTH - GameConfig.TILE_SIZE;
		int maxY = GameConfig.SCREEN_HEIGHT - GameConfig.TILE_SIZE;

		boolean blockBorders = character != player || !walking;

		if (character.isCollisionOn() && blockBorders && (nextX < 0 || nextY < 0 || nextX > maxX || nextY > maxY)) {
			return false;
		}

		if (walking) {
			character.move(deltaX, deltaY);
		} else {
			character.translate(deltaX, deltaY);
		}

		return true;
	}

	/**
	 * Unregisters player movement. Checks the current direction so it won't stop
	 * movement if two keys were pressed at the same time.
	 * 
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

	public void updatePlayer() {
		player.update();
	}

	public Player getPlayer() {
		return player;
	}

	private void setCurrentRoom(int row, int column) {
		currentRoomRow = row;
		currentRoomColumn = column;
		currentRoom = worldMap.getRoom(row, column);
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public int getCurrentRoomRow() {
		return currentRoomRow;
	}

	public void setCurrentRoomRow(int currentRoomRow) {
		this.currentRoomRow = currentRoomRow;
	}

	public int getCurrentRoomColumn() {
		return currentRoomColumn;
	}

	public void setCurrentRoomColumn(int currentRoomColumn) {
		this.currentRoomColumn = currentRoomColumn;
	}

	public WorldMap getWorldMap() {
		return worldMap;
	}

	private void setPlayerTilePosition(int tileX, int tileY) {
		player.setX(tileX * GameConfig.TILE_SIZE);
		player.setY(tileY * GameConfig.TILE_SIZE);
	}

	/**
	 * Allows player to interact with interactable entities, such as a sign.
	 */
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
		} else {
			currentShopItem = null;
		}

		String[] dialogue = player.interact(entity);

		if (entity instanceof ChestObject chest) {
			GameObject loot = chest.takeLoot();

			if (loot != null) {
				player.addToInventory(loot);
			}
			if (loot instanceof Purchasable item) {
				item.ApplyEffect(player);
			}

			notifyListeners();
			return;
		}

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

	/**
	 * Buys the current item selected in the shop
	 * 
	 * @param player          the buyer
	 * @param currentShopItem the item to buy
	 */
	public void BuyItem(Player player, GameObject currentShopItem) {
		if (!(currentShopItem instanceof Purchasable p)) {
			return;
		}
		if (!player.canBuy(p.getPrice())) {
			currentDialogue = new String[] { "You don't have enough coins." };
			notifyListeners();
			return;
		}
		player.addToInventory(currentShopItem);
		player.removeCoins(p.getPrice());
		currentRoom.removeEntity(currentShopItem);
		p.ApplyEffect(player);
		this.currentShopItem = null;

		currentDialogue = new String[] { "Enjoy your purchase!" };

		notifyListeners();
	}

	public void notifyListeners() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Handles melee attacks
	 */
	public void handleAttack() {
		if (!player.canAttack()) {
			return;
		}

		player.attack();

		if (player.getCurrentHealth() >= 5) {
			projectiles.add(player.shoot());
		}

		else {
			Rectangle attackArea = player.getAttackArea();
			for (Entity entity : currentRoom.getEntities()) {
				if (entity instanceof Enemy enemy && attackArea.intersects(enemy.getWorldArea())) {
					if (enemy.takeDamage(player.getAttackDamage())) {
						applyKnockback(enemy, player);
					}
				}
			}
		}
	}

	/**
	 * Updates the status of projectiles. Handles shooting and removal.
	 */
	public void updateProjectiles() {
		for (Projectile p : projectiles) {
			p.update();

			if (p.getShooter() instanceof Player player) {
				for (Entity entity : currentRoom.getEntities()) {
					if (entity instanceof Enemy enemy && collisionChecker.checkCollision(p, enemy)) {
						if (enemy.takeDamage(p.getDamage())) {
							applyKnockback(enemy, player);
						}
						p.expire();
					}
				}
			}

			else if (p.getShooter() instanceof Enemy enemy) {
				if (collisionChecker.checkCollision(p, player)) {
					player.takeDamage(p.getDamage());
					p.expire();
				}
			}
		}
		projectiles.removeIf(
				p -> p.isExpired() || p.getX() + GameConfig.TILE_SIZE < 0 || p.getY() + GameConfig.TILE_SIZE < 0
						|| p.getX() >= GameConfig.SCREEN_WIDTH || p.getY() >= GameConfig.SCREEN_HEIGHT);
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public String[] getCurrentDialogue() {
		return currentDialogue;
	}

	public GameObject getCurrentShopItem() {
		return currentShopItem;
	}

	/**
	 * Resets the state of the game
	 * 
	 * @param nickname sets the player's nickname
	 */
	public void resetGame(String nickname) {
		player = new Player("1", 100, 100, nickname, 4);
		worldMap = new WorldMap();

		movementDirection = null;
		projectiles.clear();

		currentShopItem = null;
		currentDialogueCharacter = null;
		currentDialogue = null;

		setCurrentRoom(3, 0);
		setPlayerTilePosition(2, 8);

		collisionChecker = new CollisionChecker(this);

		notifyListeners();
	}

	void moveEnemy(Enemy enemy) {
		if (!moveCharacter(enemy, true, null)) {
			enemy.stop();
		}
	}

	/**
	 * Applies knockback to the attacked character
	 * 
	 * @param target   the target character
	 * @param attacker the attacker
	 */
	void applyKnockback(Character target, Character attacker) {
		if (target.getCharacterState() == CharacterState.DEAD) {
			return;
		}
		target.startKnockback(attacker.getDirection());
	}

	/**
	 * Checks character movement during knockback
	 * 
	 * @param character the knocked back character
	 */
	private void moveKnockback(Character character) {
		Direction facingDirection = character.getDirection();
		character.setDirection(character.getKnockbackDirection());
		boolean moved = moveCharacter(character, false, null);
		character.setDirection(facingDirection);

		if (!moved) {
			character.stopKnockback();
			return;
		}
		character.updateKnockback();
	}

}
