package model;

/**
 * The interface required to implement a specific enemy behavior through the
 * Strategy Pattern.
 */
public interface EnemyBehavior {

	/**
	 * Updates the enemy's behavior (e.g. makes it attack when in range)
	 * 
	 * @param enemy  the enemy following the behavior
	 * @param player the player
	 * @param model  the game model
	 */
	void updateBehavior(Enemy enemy, Player player, GameModel model);

}
