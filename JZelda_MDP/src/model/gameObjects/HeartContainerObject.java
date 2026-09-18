package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.Purchasable;

/**
 * The class representing a heart container, which grants the player 2 extra
 * maximum life. It implements the Interactable interface to allow player
 * interaction, and the Purchasable interface to allow buying.
 */
public class HeartContainerObject extends GameObject implements Interactable, Purchasable {

	private int price;
	private String[] dialogue = { "Good choice.", "It will improve your health",
			"by 2 points. It costs " + price + " coins" };

	/**
	 * Generates a heart container. It also sets its area and its collisions as
	 * true, blocking player movement.
	 * 
	 * @param id       the id needed to identify the entity
	 * @param x        position on the x axis
	 * @param y        position on the y axis
	 * @param price    the item's price
	 * @param dialogue the lines of dialogue required for the shop interaction
	 */
	public HeartContainerObject(String id, int x, int y, int price, String[] dialogue) {
		super(id, x, y, 12, ItemType.HEART_CONTAINER);
		this.price = price;
		this.dialogue = dialogue;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 0, 14 * GameConfig.SCALE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
	}

	@Override
	public String[] interact(Player player) {
		return dialogue;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Increases the player's maximum health by 2.
	 */
	@Override
	public void ApplyEffect(Player player) {
		System.out.println(player.getMaxHealth());
		player.setMaxHealth(player.getMaxHealth() + 2);
		player.setCurrentHealth(player.getCurrentHealth() + 2);
		System.out.println(player.getMaxHealth());

	}

}
