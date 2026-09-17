package model;

import java.awt.Rectangle;

/**
 * The collision checker handles the collision with all entities and tiles.
 */
public class CollisionChecker {

	private GameModel model;

	public CollisionChecker(GameModel model) {
		this.model = model;
	}

	/**
	 * Checks the neighboring tiles for collsion. Firstly, the method calculates the
	 * character's neighboring cooridnates. Then, the method takes the character's
	 * direction and checks whether or not the next tile has collision. If true,
	 * blocks player movement
	 * 
	 * @param character the player character
	 */
	public void checkTileCollision(Character character) {

		Rectangle characterArea = character.getSolidArea();

		int characterLeftWorldX = character.getX() + characterArea.x;
		int characterRightWorldX = character.getX() + characterArea.x + characterArea.width - 1;
		int characterTopWorldY = character.getY() + characterArea.y;
		int characterBottomWorldY = character.getY() + characterArea.y + characterArea.height - 1;

		int characterLeftCol = characterLeftWorldX / GameConfig.TILE_SIZE;
		int characterRightCol = characterRightWorldX / GameConfig.TILE_SIZE;
		int characterTopRow = characterTopWorldY / GameConfig.TILE_SIZE;
		int characterBottomRow = characterBottomWorldY / GameConfig.TILE_SIZE;

		int row1, col1, row2, col2;

		switch (character.getDirection()) {
		case UP:
			row1 = row2 = (characterTopWorldY - character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			col1 = characterLeftCol;
			col2 = characterRightCol;
			break;
		case DOWN:
			row1 = row2 = (characterBottomWorldY + character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			col1 = characterLeftCol;
			col2 = characterRightCol;
			break;
		case LEFT:
			col1 = col2 = (characterLeftWorldX - character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			row1 = characterTopRow;
			row2 = characterBottomRow;
			break;
		case RIGHT:
			col1 = col2 = (characterRightWorldX + character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			row1 = characterTopRow;
			row2 = characterBottomRow;
			break;
		default:
			return;
		}

		if (isOutsideBorders(row1, col1) || isOutsideBorders(row2, col2)) {
			return;
		}

		Room room = model.getCurrentRoom();
		int tileNum1 = room.getRoomTile(row1, col1);
		int tileNum2 = room.getRoomTile(row2, col2);
		if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
			character.setColliding(true);
		}
	}

	/**
	 * Checks collisions with solid entity. Firstly, the method calculates the
	 * character's future occupied area. Then, it creates a rectangle based on the
	 * data. The method applies the same process to an entity. If the two rectangles
	 * intersect, block palyer movement.*
	 * 
	 * @param player the player character
	 * @param entity any other entity
	 */
	public void checkEntityCollision(Character player, Entity entity) {

		if (!entity.isCollisionOn())
			return;

		// Player's area

		Rectangle characterArea = player.getSolidArea();

		int characterWorldX = player.getX() + characterArea.x;
		int characterWorldY = player.getY() + characterArea.y;

		switch (player.getDirection()) {
		case UP:
			characterWorldY -= player.getCharacterSpeed();
			break;

		case DOWN:
			characterWorldY += player.getCharacterSpeed();
			break;

		case LEFT:
			characterWorldX -= player.getCharacterSpeed();
			break;

		case RIGHT:
			characterWorldX += player.getCharacterSpeed();
			break;
		}

		Rectangle futureCharacterArea = new Rectangle(characterWorldX, characterWorldY, characterArea.width,
				characterArea.height);

		Rectangle entityWorldArea = entity.getWorldArea();

		if (futureCharacterArea.intersects(entityWorldArea)) {
			player.setColliding(true);
		}
	}

	/**
	 * Checks if there is an interactable entity in the vicinity of the player,
	 * depending on the direction they're facing. The stream API is used to filter
	 * the entities and find the one to interact with at the end.
	 * 
	 * @param player the player
	 * @return the entity to interact with, null if none is present.
	 */
	public Entity findInteractable(Character player) {
		Rectangle playerArea = player.getSolidArea();

		// Gets player area. Since the original area is relative to the character in
		// pixels,
		// it needs to add the player's coordinates to place it correctly.
		Rectangle playerInteractionArea = new Rectangle(player.getX() + playerArea.x, player.getY() + playerArea.y,
				playerArea.width, playerArea.height);

		// Sets interaction range
		int interactionRange = GameConfig.ORIGINAL_TILE_SIZE / 4;

		// Moves the area in the facing direction
		switch (player.getDirection()) {
		case UP:
			playerInteractionArea.y -= interactionRange;
			break;
		case DOWN:
			playerInteractionArea.y += interactionRange;
			break;
		case LEFT:
			playerInteractionArea.x -= interactionRange;
			break;
		case RIGHT:
			playerInteractionArea.x += interactionRange;
			break;
		}

		return model.getCurrentRoom().getEntities().stream().filter(entity -> entity instanceof Interactable)
				.filter(entity -> playerInteractionArea.intersects(entity.getWorldArea())).findFirst().orElse(null);
	}

	/**
	 * Checks if the coordinates are outside o the room borders. Used to prevent
	 * being knocked back out of the room.
	 * 
	 * @param x position on the x axis
	 * @param y position on the y axis
	 * @return true if the coordinates are inside of the room
	 */
	public boolean isOutsideBorders(int x, int y) {
		int[][] layout = model.getCurrentRoom().getRoomLayout();
		if (x < 0 || x >= layout.length || y < 0 || y >= layout[x].length)
			return true;
		return false;
	}

	/**
	 * Checks if the attacker's hit connects to the receiver.
	 * 
	 * @param the    attacker entity
	 * @param target the target entity
	 * @return true if the hit lands
	 */
	public boolean checkCollision(Entity first, Entity second) {
		return first.getWorldArea().intersects(second.getWorldArea());
	}
}
