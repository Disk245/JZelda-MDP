package model;

public class RangedBehavior implements EnemyBehavior {

	@Override
	public void updateBehavior(Enemy enemy, Player player, GameModel model) {
		enemy.facePlayer(player);

		if (!enemy.isInRange(player, enemy.getDetectionRange())) {
			enemy.stop();
			return;
		}

		if (!enemy.isInRange(player, enemy.getAttackRange()) || !enemy.isAligned(player, enemy.getTolerance())) {
			model.moveEnemy(enemy);
			return;
		}

		enemy.stop();

		Projectile projectile = enemy.tryRangedAttack(player);

		if (projectile != null) {
			model.getProjectiles().add(projectile);
		}
	}
}
