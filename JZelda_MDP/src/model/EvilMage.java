package model;

import java.awt.Rectangle;

import model.gameObjects.CoinObject;
import model.gameObjects.HeartObject;

public class EvilMage extends Enemy{

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
	
	@Override
	public Projectile shoot(int projectileSpeed) {
		return super.shoot(5);
	}

}
