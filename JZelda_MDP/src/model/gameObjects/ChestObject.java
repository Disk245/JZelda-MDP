package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.Purchasable;
import model.GameObject.ItemType;

public class ChestObject extends GameObject implements Interactable {

	GameObject loot;
	String lootIdentifier;

	public ChestObject(String id, int x, int y, GameObject loot) {
		super(id, x, y, 1, ItemType.CHEST_CLOSED);

		this.setSolidArea(new Rectangle(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
		this.loot = loot;

	}

	@Override
	public String[] interact(Player player) {
		if (player.hasKey())
			openChest(player);
		return null;
	}

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
