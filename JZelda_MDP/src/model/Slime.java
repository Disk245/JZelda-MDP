package model;

import java.awt.Rectangle;

public class Slime extends Enemy {

	public Slime(String id, int x, int y, String name) {
		super(id, x, y, name, 2);
		this.attackCooldown = 100;
		this.attackDamage = 1;
		this.attackDuration = 30;
		this.invincibilityFrames = 15;
		this.maxHealth = 5;
		this.currentHealth = 5;
		attackRange = GameConfig.TILE_SIZE / 3;
		detectionRange = GameConfig.TILE_SIZE * 4;
		String[] slimeLoot = {"coin","health"};
		this.loot = slimeLoot;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 1 * GameConfig.SCALE, 12 * GameConfig.SCALE,
				12 * GameConfig.SCALE));
		this.setBehavior(new MeleeBehavior());
	}
}
