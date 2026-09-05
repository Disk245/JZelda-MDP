package model;

import java.awt.Rectangle;

import model.gameObjects.CoinObject;
import model.gameObjects.HeartObject;

public class Slime extends Enemy {

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
		String[] slimeLoot = {"coin","health"};
		this.loot = slimeLoot;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 1 * GameConfig.SCALE, 12 * GameConfig.SCALE,
				12 * GameConfig.SCALE));
		this.setBehavior(new MeleeBehavior());
	}

	@Override
	public GameObject produceLoot() {
		double chance = Math.random();
		if (chance < 0.90) {
			return new CoinObject("coin", this.x, this.y);
		}
		else {
			return new HeartObject("heath", this.x, this.y);
		}
	}
}
