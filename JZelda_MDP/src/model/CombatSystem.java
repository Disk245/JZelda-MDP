package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class CombatSystem {

	private final CollisionChecker collisionChecker;
	private final List<Projectile> projectiles = new ArrayList<>();

	public CombatSystem(CollisionChecker collisionChecker) {
		this.collisionChecker = collisionChecker;
	}

	/**
	 * Performs the player's attack and creates a projectile or checks the melee
	 * area.
	 *
	 * @param player      the attacking player
	 * @param currentRoom the room containing the enemies
	 */
	public void handlePlayerAttack(Player player, Room currentRoom) {
		if (!player.canAttack()) {
			return;
		}

		player.attack();

		if (player.getCurrentHealth() >= 5) {
			addProjectile(player.shoot(0));
			return;
		}

		Rectangle attackArea = player.getAttackArea();

		for (Entity entity : currentRoom.getEntities()) {
			if (entity instanceof Enemy enemy && attackArea.intersects(enemy.getWorldArea())) {

				if (enemy.takeDamage(player.getAttackDamage())) {
					applyKnockback(enemy, player);
				}
			}
		}
	}

	/**
	 * Updates projectiles, handles collisions and removes expired projectiles.
	 *
	 * @param player      the current player
	 * @param currentRoom the room containing possible targets
	 */
	public void updateProjectiles(Player player, Room currentRoom) {
		for (Projectile p : projectiles) {
			p.update();

			if (p.getShooter() instanceof Player) {
				for (Entity entity : currentRoom.getEntities()) {
					if (entity instanceof Enemy enemy && collisionChecker.checkCollision(p, enemy)) {
						if (enemy.takeDamage(p.getDamage())) {
							applyKnockback(enemy, player);
						}
						p.expire();
					}
				}
			}

			else if (p.getShooter() instanceof Enemy enemy) {
				if (collisionChecker.checkCollision(p, player)) {
					player.takeDamage(p.getDamage());
					p.expire();
				}
			}
		}
		projectiles.removeIf(
				p -> p.isExpired() || p.getX() + GameConfig.TILE_SIZE < 0 || p.getY() + GameConfig.TILE_SIZE < 0
						|| p.getX() >= GameConfig.SCREEN_WIDTH || p.getY() >= GameConfig.SCREEN_HEIGHT);
	}

	/**
	 * Adds a projectile to the active projectile list.
	 *
	 * @param projectile the projectile to add
	 */
	public void addProjectile(Projectile projectile) {
		if (projectile != null) {
			projectiles.add(projectile);
		}
	}

	public void applyKnockback(Character target, Character attacker) {

		if (target.getCharacterState() != Character.CharacterState.DEAD) {
			target.startKnockback(attacker.getDirection());
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Removes all active projectiles.
	 */
	public void clearProjectiles() {
		projectiles.clear();
	}
}
