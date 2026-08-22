package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.GameObject.ItemType;

public class ChestObject extends GameObject implements Interactable{
	
	GameObject loot;
	String lootIdentifier;
	
	public ChestObject(String id, int x, int y, GameObject loot) {
		super(id, x, y, 1, ItemType.CHEST_CLOSED);
		
		this.setSolidArea(new Rectangle(0,0,GameConfig.TILE_SIZE,GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
		this.loot = loot;

	}

	@Override
	public String[] interact() {
		openChest();
		return null;
		
	}
	
	public void openChest() {
		this.itemType = ItemType.CHEST_OPEN;
		this.setSpriteId(2);
	}

}
