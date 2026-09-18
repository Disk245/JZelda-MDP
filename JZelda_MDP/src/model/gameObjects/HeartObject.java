package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Player;
import model.GameObject.ItemType;
import model.Pickable;

/**
 * The class representing a heart droppable by slain enemies. It implements the
 * Pickable interface to allow pickup from the player by walking over it.
 */
public class HeartObject extends GameObject implements Pickable {

	/**
	 * Generates a heart. It also sets its area and its collisions as false to allow
	 * walking over it.
	 * 
	 * @param id the id needed to identify the entity
	 * @param x  position on the x axis
	 * @param y  position on the y axis
	 */
	public HeartObject(String id, int x, int y) {
		super(id, x, y, 14, ItemType.HEART_DROP);
		this.setSolidArea(
				new Rectangle(3 * GameConfig.SCALE, 1 * GameConfig.SCALE, 8 * GameConfig.SCALE, 8 * GameConfig.SCALE));
		this.setCollisionOn(false);
	}

	@Override
	public void pickup(Player player) {
		player.setCurrentHealth(player.getCurrentHealth() + 1);

	}

}
