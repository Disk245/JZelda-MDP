package model;


import model.Character.CharacterState;
import model.Character.Direction;

/**
 * The class that handles the movement of the player.
 * It communicates with the collision checker to handle collisions.
 */
public class MovementSystem {

	private final CollisionChecker collisionChecker;
	private Direction movementDirection;

	/**
	 * Initializes the movement system.
	 * @param collisionChecker the collision checker required for collisions.
	 */
	public MovementSystem(CollisionChecker collisionChecker) {
		this.collisionChecker = collisionChecker;
	}

	/**
	 * Clears the currently registered movement direction.
	 */
	public void resetMovement() {
		movementDirection = null;
	}

	/**
	 * Registers player movement start. It is separate to avoid moving the player
	 * directly at each keyboard press, making the movement clunky
	 * 
	 * @param direction the direction to face
	 */
	public void startPlayerMovement(Player player, Direction direction) {
		if (player.getCharacterState() == CharacterState.ATTACKING
				|| player.getCharacterState() == CharacterState.DEAD) {
			return;
		}

		movementDirection = direction;
		player.setDirection(direction);
	}

	/**
	 * Checks whether ot not the movement is possible. If it isn't, it stops the
	 * player.
	 * 
	 * @param player      the player character
	 * @param currentRoom the current room in the map
	 */
	public void movePlayer(Player player, Room currentRoom) {
		if (movementDirection == null) {
			return;
		}

		player.setDirection(movementDirection);

		if (!moveCharacter(player, true, player, currentRoom)) {
			player.stop();
		}
	}

	/**
	 * Moves an enemy and stops it if the movement is blocked.
	 *
	 * @param enemy       the enemy to move
	 * @param player      the player used for collision checking
	 * @param currentRoom the room containing the characters
	 */
	public void moveEnemy(Enemy enemy, Player player, Room currentRoom) {

		if (!moveCharacter(enemy, true, player, currentRoom)) {
			enemy.stop();
		}
	}

	/**
	 * Checks whether or not the character can move.
	 * Firstly, it checks collision with tiles.
	 * Secondly, checks collision with an entity.
	 * If all those checks return false, it calculates the
	 * character's future position and, if the character is not
	 * the player or the player is being pushed by an attack and not
	 * walking on its own, blocks the transition between rooms.
	 * If the player is moving on their own will, it calls its move method.
	 * If the player is being pushed, only translates it to keep the facing direction the same.
	 * 
	 * @param character     the character trying to move
	 * @param walking       if the character is currently walking or not. Needed to
	 *                      not change the hurt state
	 * @param player        the player character
	 * @param currentRoom
	 * @return true if movement is possible
	 */
	private boolean moveCharacter(Character character, boolean walking, Player player,
			Room currentRoom) {
		character.setColliding(false);

		if (character.isCollisionOn()) {
			collisionChecker.checkTileCollision(character);

			if (!character.isColliding()) {
				for (Entity entity : currentRoom.getEntities()) {
					if (entity == character) {
						continue;
					}

					collisionChecker.checkEntityCollision(character, entity);

					if (character.isColliding()) {
						break;
					}
				}
			}

			if (!character.isColliding() && character != player) {
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
	 * movement if two keys were pressed at the same time. It contains an additional
	 * check to avoid canceling death animation with movement input
	 * 
	 * @param direction the direction the player is facing
	 */
	public void stopPlayerMovement(Player player, Direction direction) {
		if (movementDirection == direction) {
			movementDirection = null;

			// Check needed to avoid canceling death animation with inputs
			CharacterState state = player.getCharacterState();

			if (state != CharacterState.ATTACKING && state != CharacterState.HURT && state != CharacterState.DEAD) {
				player.stop();
			}
		}
	}

	/**
	 * Moves a character during knockback while preserving its facing direction.
	 *
	 * @param character   the character affected by knockback
	 * @param player      the player used for collision checking
	 * @param currentRoom the room containing the characters
	 */
	public void moveKnockback(Character character, Player player, Room currentRoom) {

		Direction facingDirection = character.getDirection();
		character.setDirection(character.getKnockbackDirection());

		boolean moved = moveCharacter(character, false, player, currentRoom);
		character.setDirection(facingDirection);

		if (!moved) {
			character.stopKnockback();
			return;
		}

		character.updateKnockback();
	}
}
