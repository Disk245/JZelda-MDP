package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Character {

	private int coins;
	private List<GameObject> inventory;
	private int score;

	public Player(String id, int x, int y, String name, int characterSpeed) {
		super(id, x, y, name, characterSpeed);
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 10 * GameConfig.SCALE, 12 * GameConfig.SCALE,
				6 * GameConfig.SCALE));
		this.inventory = new ArrayList<>();
		this.coins = 0;
		this.maxHealth = 5;
		this.currentHealth = 5;
		this.attackDamage = 2;
		this.invincibilityFrames = 12;
		this.attackDuration = 20;
		this.attackCooldown = 35;
		this.attackRange = (int) (GameConfig.TILE_SIZE * 1.5);
		this.score = 0;
	}

	/**
	 * Interacts with an interactable entity
	 * 
	 * @param entity the entity to try interact with
	 * @return an array of strings containing the dialogue, null if the entity is
	 *         not interactable
	 */
	public String[] interact(Entity entity) {
		if (entity instanceof Interactable i) {
			return i.interact(this);
		}
		return null;
	}

	public boolean hasKey() {
		Iterator<GameObject> iterator = inventory.iterator();

		while (iterator.hasNext()) {
			GameObject item = iterator.next();

			if (item.getItemType() == GameObject.ItemType.KEY) {
				iterator.remove();
				return true;
			}
		}

		return false;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void addCoins(int amount) {
		coins += amount;
	}

	public void removeCoins(int amount) {
		coins -= amount;
	}

	public boolean canBuy(int amount) {
		return coins >= amount;
	}

	public void addToInventory(GameObject g) {
		inventory.add(g);
	}

	public List<GameObject> getInventory() {
		return inventory;
	}

	public void removeFromInventory(GameObject g) {
		inventory.remove(g);
	}
	
	@Override
	public Projectile shoot(int projectileSpeed) {
		return super.shoot(10);
	}
	
	public void updateScore(int points) { score += points; }

}
