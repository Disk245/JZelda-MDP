package model;

import java.awt.Rectangle;

public class DoorObject extends GameObject implements Interactable{

	public DoorObject(String id, int x, int y) {
		super(id, x, y, 4, ItemType.DOOR_CLOSED);
		
		this.setSolidArea(new Rectangle(0,0,GameConfig.TILE_SIZE,GameConfig.TILE_SIZE));
		
		this.setCollisionOn(true);
	}
	
	public void openDoor() { 
		this.itemType = ItemType.DOOR_OPEN; 
		this.setCollisionOn(false);
		this.setSpriteId(5);
		}

	@Override
	public void interact() {
		openDoor();	
	}
	


}
