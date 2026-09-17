package model;

import model.Character.Direction;

/**
 * The behavior of a ranged character. If the player is in the detection range,
 * moves towards it. Once it is aligned with it and inside the attack range,
 * fires a projectile. If it's too close, walks back to create some distance.
 */
public class RangedBehavior implements EnemyBehavior {

	private static final int MINIMUM_RANGE = GameConfig.TILE_SIZE * 2;

	/**
	 * Updates the enemy's behavior. If it's in range, stops movement. If it's too
	 * closem walks back. If it's out of range, reduces the distance, trying to
	 * align with the player Once in range and aligned, stops and shoots.
	 */
	@Override
	public void updateBehavior(Enemy enemy, Player player, GameModel model) {

		// Player out of detection.
		if (!enemy.isInRange(player, enemy.getDetectionRange())) {
			enemy.stop();
			return;
		}

		// Player too close, enemy walsk back.
		if (enemy.isInRange(player, MINIMUM_RANGE)) {
			moveAwayFromPlayer(enemy, player, model);
			return;
		}

		// Player out of range, closing in:
		if (!enemy.isInRange(player, enemy.getAttackRange())) {
			enemy.facePlayer(player);
			model.moveEnemy(enemy);
			return;
		}

		// Aligning:
		if (!enemy.isAligned(player, enemy.getTolerance())) {
			alignWithPlayer(enemy, player, model);
			return;
		}

		// Correct distance
		enemy.stop();

		Projectile projectile = enemy.tryRangedAttack(player);

		if (projectile != null) {
			model.addProjectile(projectile);
		}
	}

	/**
	 * Makes the enemy back off until it's two tiles away from the player.
	 * 
	 * @param enemy  the enemy
	 * @param player the player
	 * @param model  the game model
	 */
	private void moveAwayFromPlayer(Enemy enemy, Player player, GameModel model) {

		int deltaX = player.getX() - enemy.getX();
		int deltaY = player.getY() - enemy.getY();

		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			enemy.setDirection(deltaX > 0 ? Direction.LEFT : Direction.RIGHT);
		} else {
			enemy.setDirection(deltaY > 0 ? Direction.UP : Direction.DOWN);
		}

		model.moveEnemy(enemy);
	}

	/**
	 * Tries to align with the player to allow shooting.
	 * 
	 * @param enemy  the enemy
	 * @param player the player
	 * @param model  the game model
	 */
	private void alignWithPlayer(Enemy enemy, Player player, GameModel model) {

		int deltaX = player.getX() - enemy.getX();
		int deltaY = player.getY() - enemy.getY();

		if (Math.abs(deltaX) < Math.abs(deltaY)) {
			enemy.setDirection(deltaX > 0 ? Direction.RIGHT : Direction.LEFT);
		} else {
			enemy.setDirection(deltaY > 0 ? Direction.DOWN : Direction.UP);
		}

		model.moveEnemy(enemy);
	}
}
