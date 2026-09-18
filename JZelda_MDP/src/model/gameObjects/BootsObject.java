package model.gameObjects;

import model.GameObject;
import model.Player;
import model.Purchasable;

/**
 * Boots. They grant 2 extra movement speed to the player.
 */
public class BootsObject extends GameObject implements Purchasable {

	private int price;
	private int speedBonus;

	/**
	 * Create a boot object, placing it at the given coordinates.
	 * 
	 * @param id the id needed to identify the entity
	 * @param x  position on the x axis
	 * @param y  position on the y axis
	 */
	public BootsObject(String id, int x, int y) {
		super(id, x, y, 11, ItemType.BOOTS);
		this.speedBonus = 2;
	}

	public int getPrice() {
		return 0;
	}

	/**
	 * Applies the boots' effect.
	 */
	@Override
	public void ApplyEffect(Player player) {
		player.setCharacterSpeed(player.getCharacterSpeed() + speedBonus);
	}

}
