package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import model.Character.CharacterState;
import model.Character.Direction;

@SuppressWarnings("deprecation")
public class GameModel extends Observable {

	private static final String ID_DEFAULT = "1";
	private static final int DEFAULT_X_POSITION = 100;
	private static final int DEFAULT_Y_POSITION = 100;
	private static final String NICKNAME = "aa";
	private static final int CHARACTER_SPEED = 4;

	public enum GameState {
		MENU, NICKNAME, CREDITS, OPTIONS, PLAY, PAUSE, GAME_OVER, DIALOGUE, WIN, STATS
	}

	private static final GameModel INSTANCE = new GameModel();

	private final MovementSystem movementSystem;
	private final CombatSystem combatSystem;

	GameState gameState = GameState.MENU;

	private Player player = new Player(ID_DEFAULT, DEFAULT_X_POSITION, DEFAULT_Y_POSITION, NICKNAME, CHARACTER_SPEED);

	private WorldMap worldMap = new WorldMap();
	private int currentRoomRow;
	private int currentRoomColumn;
	private Room currentRoom;
	private GameObject currentShopItem;
	private Character currentDialogueCharacter;
	private RunStats currentRun = new RunStats();
	private StatsManager statsManager = new StatsManager();

	private String[] currentDialogue;

	private CollisionChecker collisionChecker = new CollisionChecker(this);

	private GameModel() {
		movementSystem = new MovementSystem(collisionChecker);
		combatSystem = new CombatSystem(collisionChecker);
		setCurrentRoom(3, 0);
		setPlayerTilePosition(2, 8);
	}

	public static GameModel getInstance() {
		return INSTANCE;
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
			movementSystem.moveKnockback(player, player, currentRoom);
		} else if (player.getCharacterState() != CharacterState.ATTACKING
				&& player.getCharacterState() != CharacterState.HURT
				&& player.getCharacterState() != CharacterState.DEAD) {
			movementSystem.movePlayer(player, currentRoom);
		}
		updateMap();
		updateEntities();
		combatSystem.updateProjectiles(player, currentRoom);
		updatePlayer();

		if (player.isDeathAnimationOver()) {
			currentRun.stopTimer();
			currentRun.calculateFinalScore(player);
			statsManager.registerDeath();
			statsManager.registerRun(currentRun);
			statsManager.writeToFile();
			setGameState(GameState.GAME_OVER);
			return;
		}

		checkVictory();

		if (gameState == GameState.WIN) {
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
		List<Entity> lootDrops = new ArrayList<>();
		Iterator<Entity> iterator = currentRoom.getEntities().iterator();

		while (iterator.hasNext()) {
			Entity entity = iterator.next();

			if (entity instanceof Enemy enemy) {
				enemy.update();

				if (enemy.isDeathAnimationOver()) {
					GameObject drop = enemy.produceLoot();
					if (drop != null) {
						lootDrops.add(drop);
					}

					iterator.remove();
					currentRun.registerKill(enemy.getPoints());
					worldMap.registerEnemyKill();
					continue;
				}

				if (enemy.isInKnockback()) {
					movementSystem.moveKnockback(enemy, player, currentRoom);
					continue;
				}

				CharacterState state = enemy.getCharacterState();
				if (state == CharacterState.DEAD || state == CharacterState.HURT || state == CharacterState.ATTACKING) {
					continue;
				}

				enemy.updateBehavior(player, this);
			}

			else if (entity instanceof Pickable p && collisionChecker.checkCollision(player, entity)) {
				p.pickup(player);
				iterator.remove();
				setChanged();
				notifyObservers(p);
			}
		}
		for (Entity e : lootDrops) {
			currentRoom.addEntity(e);
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
			combatSystem.clearProjectiles();
			return;
		}

		// Right
		if (player.getX() > maxX) {
			if (changeRoom(currentRoomRow, currentRoomColumn + 1))
				player.setX(0);
			else
				player.setX(maxX);
			combatSystem.clearProjectiles();
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
			combatSystem.clearProjectiles();
			return;
		}

		// Bottom
		if (player.getY() > maxY) {
			if (changeRoom(currentRoomRow + 1, currentRoomColumn))
				player.setY(0);
			else
				player.setY(maxY);
			combatSystem.clearProjectiles();
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
	 * Registers player movement start. Delegates the logic to the movement system.
	 * 
	 * @param direction the direction to face
	 */
	public void startPlayerMovement(Direction direction) {
		movementSystem.startPlayerMovement(player, direction);
	}

	/**
	 * Unregisters player movement. Delegates the logic to the movement system.
	 * 
	 * @param direction the direction the player is facing
	 */
	public void stopPlayerMovement(Direction direction) {
		movementSystem.stopPlayerMovement(player, direction);
	}

	/**
	 * Delegates the player's attack to the combat system.
	 */
	public void handleAttack() {
		combatSystem.handlePlayerAttack(player, currentRoom);
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

	/**
	 * Checks whether the player is currently in the boss room.
	 *
	 * @return true if the current room is the boss room
	 */
	public boolean isCurrentRoomBossRoom() {
		return currentRoom.equals(worldMap.getRoom(0, 1));
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

	public List<Projectile> getProjectiles() {
		return combatSystem.getProjectiles();
	}

	void addProjectile(Projectile projectile) {
		combatSystem.addProjectile(projectile);
	}

	public String[] getCurrentDialogue() {
		return currentDialogue;
	}

	public GameObject getCurrentShopItem() {
		return currentShopItem;
	}

	/**
	 * Resets the state of the game, the player spawn position and the current run
	 * stats, including the timer.
	 * 
	 * @param nickname sets the player's nickname
	 */
	public void resetGame(String nickname) {
		currentRun.reset();
		currentRun.startTimer();
		player = new Player("1", 100, 100, nickname, 4);
		worldMap = new WorldMap();

		movementSystem.resetMovement();
		combatSystem.clearProjectiles();

		currentShopItem = null;
		currentDialogueCharacter = null;
		currentDialogue = null;

		setCurrentRoom(3, 0);
		setPlayerTilePosition(2, 8);

		notifyListeners();
	}

	/**
	 * Moves the enemy, delegating the logic to the movement system
	 * 
	 * @param enemy the enemy to move.
	 */
	void moveEnemy(Enemy enemy) {
		movementSystem.moveEnemy(enemy, player, currentRoom);
	}

	/**
	 * Applies knockback to the attacked character
	 * 
	 * @param target   the target character
	 * @param attacker the attacker
	 */
	void applyKnockback(Character target, Character attacker) {
		combatSystem.applyKnockback(target, attacker);
	}

	private void checkVictory() {
		boolean roomCleared = currentRoom.getEntities().isEmpty();
		boolean playerAlive = player.getCharacterState() != CharacterState.DEAD;

		if (isCurrentRoomBossRoom() && roomCleared && playerAlive) {
			currentRun.stopTimer();
			currentRun.calculateFinalScore(player);
			statsManager.registerVictory(currentRun);
			statsManager.registerRun(currentRun);
			statsManager.writeToFile();
			setGameState(GameState.WIN);
		}
	}

	public RunStats getCurrentRun() {
		return currentRun;
	}

	public StatsManager getStatsManager() {
		return statsManager;
	}

}
