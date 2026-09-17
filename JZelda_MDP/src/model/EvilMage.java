package model;

import java.awt.Rectangle;

import model.gameObjects.CoinObject;
import model.gameObjects.HeartObject;

/**
 * One of the enemies created for these project. 
 * It's a ranged mage who throws fir projectiles at the character.
 * It aligns with the player at the right range and fires.
 * If it's too close, walks back to try and create some space.
 */
public class EvilMage extends Enemy{

	/**
	 * Creates an Evil Mage. It uses the superclass's constructor
	 * to place it on the map and identifyi it, then sets
	 * the other fields to their standard values.
	 * 
	 * @param id an id to identify the character
	 * @param x the position on the x axis
	 * @param y the position on the y axis
	 * @param name the character's name (e.g. "slime")
	 */
	public EvilMage(String id, int x, int y, String name) {
		super(id, x, y, name, 2);
		this.attackCooldown = 80;
		this.attackDamage = 1;
		this.attackDuration = 30;
		this.invincibilityFrames = 15;
		this.maxHealth = 5;
		this.currentHealth = 5;
		this.points = 200;
		this.setCharacterSpeed(1);
		this.setTolerance(GameConfig.TILE_SIZE / 2);
		attackRange = GameConfig.TILE_SIZE * 6;
		detectionRange = GameConfig.TILE_SIZE * 10;
		String[] mageLoot = {"coin","health"};
		this.loot = mageLoot;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 1 * GameConfig.SCALE, 12 * GameConfig.SCALE,
				12 * GameConfig.SCALE));
		this.setBehavior(new RangedBehavior());
	}

	/**
	 * Produces the enemy's loot. It uses a random generated number
	 * to determine which one to drop from a pool of two.
	 */
	@Override
	public GameObject produceLoot() {
		double chance = Math.random();
		if (chance < 0.5) {
			return new CoinObject("coin", this.x, this.y);
		}
		else {
			return new HeartObject("heath", this.x, this.y);
		}
	}
	
	/**
	 * Shoots a projectile, using the required projectile speed.
	 */
	@Override
	public Projectile shoot(int projectileSpeed) {
		return super.shoot(5);
	}

}
