package model;

import java.awt.Rectangle;

import model.gameObjects.CoinObject;
import model.gameObjects.HeartObject;

/**
 * A slime. It's a melee range enemy, which chases the player until it's close
 * enough to attack.
 */
public class Slime extends Enemy {

	/**
	 * Creates a slime. Id and positioning are handled by the superclass. Then, its
	 * other fields are initialized with the enemy's basic values.
	 * 
	 * @param id   an id to identify the character
	 * @param x    the position on the x axis
	 * @param y    the position on the y axis
	 * @param name the character's name (e.g. "slime")
	 */
	public Slime(String id, int x, int y, String name) {
		super(id, x, y, name, 2);
		this.attackCooldown = 100;
		this.attackDamage = 1;
		this.attackDuration = 30;
		this.invincibilityFrames = 15;
		this.maxHealth = 5;
		this.currentHealth = 5;
		this.points = 100;
		this.attackRange = GameConfig.TILE_SIZE / 3;
		this.detectionRange = GameConfig.TILE_SIZE * 6;
		String[] slimeLoot = { "coin", "health" };
		this.loot = slimeLoot;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 1 * GameConfig.SCALE, 12 * GameConfig.SCALE,
				12 * GameConfig.SCALE));
		this.setBehavior(new MeleeBehavior());
	}

	/**
	 * Implementation of the abstract method from enemy. It uses a random number to
	 * determine what loot to drop between two available.
	 */
	@Override
	public GameObject produceLoot() {
		double chance = Math.random();
		if (chance < 0.90) {
			return new CoinObject("coin", this.x, this.y);
		} else {
			return new HeartObject("heath", this.x, this.y);
		}
	}
}
