package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Pickable;
import model.Player;
import model.GameObject.ItemType;

/**
 * A coin that can be picked up by the player by walking on it. Implements the
 * Pickable interface to allow pickup.
 */
public class CoinObject extends GameObject implements Pickable {

	/**
	 * Generates a coin at the given coordinates. It also sets its area and its
	 * collision to false, allowing the player to walk over it for pickup.
	 * 
	 * @param id the id needed to identify the entity
	 * @param x  position on the x axis
	 * @param y  position on the y axis
	 */
	public CoinObject(String id, int x, int y) {
		super(id, x, y, 15, ItemType.COIN_DROP);
		this.setSolidArea(new Rectangle(3 * GameConfig.SCALE, 1 * GameConfig.SCALE, 12 * GameConfig.SCALE,
				12 * GameConfig.SCALE));
		this.setCollisionOn(false);
	}

	/**
	 * Allows coin pickup, increasing player money.
	 */
	@Override
	public void pickup(Player player) {
		player.setCoins(player.getCoins() + 5);
		System.out.println("MONEY ADDED");

	}

}
