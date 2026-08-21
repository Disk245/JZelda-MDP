package model;

import java.awt.Rectangle;

public class ChestObject extends GameObject implements Interactable{
	
	GameObject loot;
	
	public ChestObject(String id, int x, int y, GameObject loot) {
		super(id, x, y, 1, ItemType.CHEST_CLOSED);
		
		this.setSolidArea(new Rectangle(0,0,GameConfig.TILE_SIZE,GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
		this.loot = loot;

	}

	@Override
	public void interact() {
		openChest();
		
	}
	
	public void openChest() {
		this.itemType = ItemType.CHEST_OPEN;
		this.setSpriteId(2);
	}

}
