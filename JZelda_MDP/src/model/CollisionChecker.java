package model;

import java.awt.Rectangle;

public class CollisionChecker {
	
	private GameModel model;
	
	public CollisionChecker(GameModel model) {
		this.model = model;
	}
	
	/**
	 * Checks the neighboring tiles for collsion.
	 * Firstly, the method calculates the character's neighboring cooridnates.
	 * Then, the  method takes the character's direction and checks whether or not the next tile has collision.
	 * If true, blocks player movement
	 * @param character the player character
	 */
	public void checkTileCollision(Character character) {
		
		Rectangle characterArea = character.getSolidArea();
		
		int characterLeftWorldX = character.getX() + characterArea.x;
		int characterRightWorldX = character.getX() + characterArea.x + characterArea.width -1;
		int characterTopWorldY = character.getY() + characterArea.y;
		int characterBottomWorldY = character.getY() + characterArea.y + characterArea.height -1;
	
		int characterLeftCol = characterLeftWorldX / GameConfig.TILE_SIZE;
		int characterRightCol = characterRightWorldX / GameConfig.TILE_SIZE;
		int characterTopRow = characterTopWorldY / GameConfig.TILE_SIZE;
		int characterBottomRow = characterBottomWorldY / GameConfig.TILE_SIZE;
		
		int tileNum1, tileNum2;
		
		switch (character.getDirection()) {
		case UP: 
			characterTopRow = (characterTopWorldY - character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			if (isOutsideBorders(characterTopRow, characterLeftCol)
					|| (isOutsideBorders(characterTopRow, characterRightCol))) 
						return; 
			
			tileNum1 = model.getCurrentRoom().getRoomTile(characterTopRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterTopRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		case DOWN: 
			characterBottomRow = (characterBottomWorldY + character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			if (isOutsideBorders(characterBottomRow, characterLeftCol)
					|| (isOutsideBorders(characterBottomRow, characterRightCol))) 
						return; 
			
			tileNum1 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		case LEFT: 
			characterLeftCol = (characterLeftWorldX - character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			if (isOutsideBorders(characterTopRow, characterLeftCol)
					|| (isOutsideBorders(characterBottomRow, characterLeftCol))) 
						return; 
			
			tileNum1 = model.getCurrentRoom().getRoomTile(characterTopRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterLeftCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		case RIGHT: 
			characterRightCol = (characterRightWorldX + character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			if (isOutsideBorders(characterTopRow, characterRightCol)
					|| (isOutsideBorders(characterBottomRow, characterRightCol))) 
						return; 
			
			tileNum1 = model.getCurrentRoom().getRoomTile(characterTopRow, characterRightCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		}
	}
	
	/**
	 * Checks collisions with solid entity.
	 * Firstly, the method calculates the character's future occupied area.
	 * Then, it creates a rectangle based on the data.
	 * The method applies the same process to an entity.
	 * If the two rectangles intersect, block palyer movement.*
	 * @param player the player character
	 * @param entity any other entity
	 */
	public void checkEntityCollision (Character player, Entity entity) {
			
	    if (!entity.isCollisionOn()) return;
		
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
	    
	    Rectangle futureCharacterArea = new Rectangle(characterWorldX, characterWorldY, 
	    		characterArea.width, characterArea.height);
	    
	    // Entity's area
	    
	    Rectangle entityArea = entity.getSolidArea();
	    
	    int entityWorldX = entity.getX() + entityArea.x;
	    int entityWorldY = entity.getY() + entityArea.y;
	    
	    Rectangle entityWorldArea = new Rectangle( entityWorldX, entityWorldY, 
	    		entityArea.width, entityArea.height);

	        if (futureCharacterArea.intersects(entityWorldArea)) {
	            player.setColliding(true);
	        }
	    } 
	
	public Entity findInteractable(Character player) {
		Rectangle playerArea = player.getSolidArea();
		
		// Gets player area. Since the original area is relative to the character in pixels,
		// it needs to add the player's coordinates to place it correctly.
		Rectangle playerInteractionArea = new Rectangle(player.getX() + playerArea.x, player.getY() + playerArea.y, 
				playerArea.width, playerArea.height);
		
		// Sets interaction range
		int interactionRange = GameConfig.ORIGINAL_TILE_SIZE / 4;
		
		// Moves the area in the facing direction
		switch(player.getDirection()) {
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
		
		for (Entity entity : model.getCurrentRoom().getEntities()) {
			if (!(entity instanceof Interactable)) continue;
			
			Rectangle entityArea = entity.getSolidArea();
			
			Rectangle entityInteractionArea = new Rectangle(entity.getX() + entityArea.x, entity.getY() + entityArea.y, 
					entityArea.width, entityArea.height);
			
			if (playerInteractionArea.intersects(entityInteractionArea)) return entity;		
		}
		
		return null;	
	}
	
	public boolean isOutsideBorders(int x, int y) {
		int[][] layout = model.getCurrentRoom().getRoomLayout();
		if (x <0 || x >= layout.length || y < 0 || y >= layout[x].length)
			return true;
		return false;
	}
}
