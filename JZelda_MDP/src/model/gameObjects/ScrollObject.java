package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.Purchasable;

/**
 * The class representing a scroll, granting the player 2 extra damage. It
 * implements the Interactable interface to allow player interaction, and the
 * Purchasable interface to allow buying.
 */
public class ScrollObject extends GameObject implements Interactable, Purchasable {

	private int price;
	String[] dialogue = { "That is a rare scroll.", "It will make you stronger.", "It costs " + price + " coins" };

	/**
	 * Generates a key container. It also sets its area and its collisions as true,
	 * blocking player movement.
	 * 
	 * @param id       the id needed to identify the entity
	 * @param x        position on the x axis
	 * @param y        position on the y axis
	 * @param price    the item's price
	 * @param dialogue the lines of dialogue required for the shop interaction
	 */
	public ScrollObject(String id, int x, int y, int price, String[] dialogue) {
		super(id, x, y, 13, ItemType.SCROLL);
		this.price = price;
		this.dialogue = dialogue;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 0, 14 * GameConfig.SCALE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
	}

	@Override
	public String[] interact(Player player) {
		return dialogue;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public void ApplyEffect(Player player) {
		System.out.println(player.getAttackDamage());
		player.setAttackDamage(player.getAttackDamage() + 2);
		System.out.println(player.getAttackDamage());
	}

}
