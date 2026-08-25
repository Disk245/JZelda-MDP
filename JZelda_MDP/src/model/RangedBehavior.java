package model;

import model.Character.Direction;

public class RangedBehavior implements EnemyBehavior {

	private static final int MINIMUM_RANGE = GameConfig.TILE_SIZE * 2;

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
			model.getProjectiles().add(projectile);
		}
	}

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

	private void alignWithPlayer(Enemy enemy, Player player, GameModel model) {

		int deltaX = player.getX() - enemy.getX();
		int deltaY = player.getY() - enemy.getY();

		/*
		 * Si muove lungo l'asse sul quale è già più vicino all'allineamento.
		 */
		if (Math.abs(deltaX) < Math.abs(deltaY)) {
			enemy.setDirection(deltaX > 0 ? Direction.RIGHT : Direction.LEFT);
		} else {
			enemy.setDirection(deltaY > 0 ? Direction.DOWN : Direction.UP);
		}

		model.moveEnemy(enemy);
	}
}
