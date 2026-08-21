package model;

import java.awt.Rectangle;

public class DoorObject extends GameObject implements Interactable{

	private boolean conditionSatisfied;
	
	public DoorObject(String id, int x, int y) {
		super(id, x, y, 9, ItemType.DOOR_CLOSED);
		
		this.setSolidArea(new Rectangle(0,0,GameConfig.TILE_SIZE,GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
		this.conditionSatisfied = true;
	}
	
	public void openDoor() { 
		this.itemType = ItemType.DOOR_OPEN; 
		this.setCollisionOn(false);
		this.setSpriteId(8);
		}

	@Override
	public void interact() {
		if (conditionSatisfied)
			openDoor();	
	}

	public boolean isConditionSatisfied() {
		return conditionSatisfied;
	}

	public void setConditionSatisfied(boolean conditionSatisfied) {
		this.conditionSatisfied = conditionSatisfied;
	}
	
	


}
