package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.Purchasable;
import model.GameObject.ItemType;

/**
 * A chest. It contains a piece of loot and can be opened with a key.
 */
public class ChestObject extends GameObject implements Interactable {

	GameObject loot;
	String lootIdentifier;

	/**
	 * Generates a chest, giving the information to place it correctly on the map.
	 * It also sets its area as a full tile, and collision to true, blocking player
	 * movement.
	 * 
	 * @param id   the id needed to identify the entity
	 * @param x    position on the x axis
	 * @param y    position on the y axis
	 * @param loot the contained loot
	 */
	public ChestObject(String id, int x, int y, GameObject loot) {
		super(id, x, y, 1, ItemType.CHEST_CLOSED);

		this.setSolidArea(new Rectangle(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
		this.loot = loot;

	}

	/**
	 * Calls the chest opening if the player has a key.
	 */
	@Override
	public String[] interact(Player player) {
		if (player.hasKey())
			openChest(player);
		return null;
	}

	/**
	 * Opens the chest. The method changes the chest's sprite id, letting the view
	 * draw the correct image, then produces said loot, giving it to the player. It
	 * uses the Purchasable interface to assign it correctly
	 * 
	 * @param player the player
	 */
	public void openChest(Player player) {
		this.itemType = ItemType.CHEST_OPEN;
		this.setSpriteId(2);

		GameObject collectedLoot = takeLoot();

		if (collectedLoot != null) {
			player.addToInventory(collectedLoot);

			if (collectedLoot instanceof Purchasable item) {
				item.ApplyEffect(player);
			}
		}
	}

	public GameObject takeLoot() {
		GameObject collectedLoot = loot;
		loot = null;
		return collectedLoot;
	}

}
