package model;

/**
 * The interface required to implement a specific enemy behavior through
 * the Strategy Pattern.
 */
public interface EnemyBehavior {

	void updateBehavior(Enemy enemy, Player player, GameModel model);

}
