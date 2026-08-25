package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.GameObject.ItemType;

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
	public String[] interact(Player player) {
		if (conditionSatisfied)
			openDoor();	
		return null;
	}

	public boolean isConditionSatisfied() {
		return conditionSatisfied;
	}

	public void setConditionSatisfied(boolean conditionSatisfied) {
		this.conditionSatisfied = conditionSatisfied;
	}
	
	


}
