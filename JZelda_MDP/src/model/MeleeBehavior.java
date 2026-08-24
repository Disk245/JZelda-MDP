package model;

public class MeleeBehavior implements EnemyBehavior {

	@Override
	public void updateBehavior(Enemy enemy, Player player, GameModel model) {
		enemy.facePlayer(player);

		if (enemy.getAttackArea().intersects(player.getWorldArea())) {

			if (enemy.tryMeleeAttack(player)) {
				model.applyKnockback(player, enemy);
			}

			return;
		}

		if (enemy.isInRange(player, enemy.getDetectionRange())) {
			model.moveEnemy(enemy);
		} else {
			enemy.stop();
		}
	}
}
