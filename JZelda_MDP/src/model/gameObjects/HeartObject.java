package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Player;
import model.GameObject.ItemType;
import model.Pickable;

public class HeartObject extends GameObject implements Pickable{
	
	public HeartObject(String id, int x, int y) {
		super(id, x, y, 14, ItemType.HEART_DROP);
		this.setSolidArea(new Rectangle(3 * GameConfig.SCALE, 1 * GameConfig.SCALE, 8 * GameConfig.SCALE, 8 * GameConfig.SCALE));
		this.setCollisionOn(false);
	}

	@Override
	public void pickup(Player player) {
		player.setCurrentHealth(player.getCurrentHealth() + 1);
		
	}


}
