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
		int characterRightWorldX = character.getX() + characterArea.x + character.getSolidArea().width -1;
		int characterTopWorldY = character.getY() + characterArea.y;
		int characterBottomWorldY = character.getY() + characterArea.y + character.getSolidArea().height -1;
	
		int characterLeftCol = characterLeftWorldX / GameConfig.TILE_SIZE;
		int characterRightCol = characterRightWorldX / GameConfig.TILE_SIZE;
		int characterTopRow = characterTopWorldY / GameConfig.TILE_SIZE;
		int characterBottomRow = characterBottomWorldY / GameConfig.TILE_SIZE;
		
		int tileNum1, tileNum2;
		
		switch (character.getDirection()) {
		case UP: 
			characterTopRow = (characterTopWorldY - character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			tileNum1 = model.getCurrentRoom().getRoomTile(characterTopRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterTopRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		case DOWN: 
			characterBottomRow = (characterBottomWorldY + character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			tileNum1 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		case LEFT: 
			characterLeftCol = (characterLeftWorldX - character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			tileNum1 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		case RIGHT: 
			characterRightCol = (characterRightWorldX + character.getCharacterSpeed()) / GameConfig.TILE_SIZE;
			tileNum1 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterLeftCol);
			tileNum2 = model.getCurrentRoom().getRoomTile(characterBottomRow, characterRightCol);
			if (TileStorage.getTile(tileNum1).hasCollision() || TileStorage.getTile(tileNum2).hasCollision()) {
				character.setColliding(true);
			}
			break;
		}
	}
}
