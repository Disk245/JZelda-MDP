package model;

import java.util.List;

import model.Character.CharacterState;
import model.Character.Direction;

public abstract class Enemy extends Character {

	private EnemyBehavior behavior;
	private int tolerance;
	protected int detectionRange;
	protected String[] loot;
	protected int points;

	public Enemy(String id, int x, int y, String name, int characterSpeed) {
		super(id, x, y, name, characterSpeed);
	}

	public EnemyBehavior getBehavior() {
		return behavior;
	}

	public void setBehavior(EnemyBehavior behavior) {
		this.behavior = behavior;
	}

	/**
	 * Checks whether or not the player is in range of the enemy's attack
	 * 
	 * @param player the player
	 * @param range  the enemy's range
	 * @return true if player is in range
	 */
	protected boolean isInRange(Player player, int range) {
		int deltaX = player.getX() - getX();
		int deltaY = player.getY() - getY();

		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		return distance <= range;
	}

	/**
	 * Checks if the enemy is aligned with the player with a margin of tolerance.
	 * 
	 * @param player
	 * @param tolerance the margin of tolerance to not require pixel perfect
	 *                  alignment
	 * @return
	 */
	protected boolean isAligned(Player player, int tolerance) {
		int deltaX = Math.abs(player.getX() - getX());
		int deltaY = Math.abs(player.getY() - getY());

		boolean sameColumn = deltaX <= tolerance;
		boolean sameRow = deltaY <= tolerance;

		return sameColumn || sameRow;
	}

	/**
	 * Checks whether or not a melee attack can be performed
	 * 
	 * @param player
	 * @return true if melee attack is possible
	 */
	public boolean tryMeleeAttack(Player player) {
		if (!canAttack()) {
			return false;
		}

		facePlayer(player);

		if (!getAttackArea().intersects(player.getWorldArea())) {
			return false;
		}

		attack();
		return player.takeDamage(getAttackDamage());
	}

	/**
	 * Checks whether or not a ranged attack can be performed
	 * 
	 * @param player
	 * @return true if ranged attack is possible
	 */
	public Projectile tryRangedAttack(Player player) {
		if (!canAttack()) {
			return null;
		}

		if (!isInRange(player, attackRange) || !isAligned(player, tolerance)) {
			return null;
		}

		facePlayer(player);
		attack();

		return shoot(5);
	}

	/**
	 * Turns the enemy towards the closest axis to the player.
	 * 
	 * @param player
	 */
	protected void facePlayer(Player player) {
		int deltaX = player.getX() - getX();
		int deltaY = player.getY() - getY();

		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			setDirection(deltaX > 0 ? Direction.RIGHT : Direction.LEFT);
		} else {
			setDirection(deltaY > 0 ? Direction.DOWN : Direction.UP);
		}
	}

	public void updateBehavior(Player player, GameModel model) {
		behavior.updateBehavior(this, player, model);
	}

	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		this.tolerance = tolerance;
	}

	public int getDetectionRange() {
		return detectionRange;
	}

	public void setDetectionRange(int detectionRange) {
		this.detectionRange = detectionRange;
	}

	public abstract GameObject produceLoot();

	public int getPoints() {
		return points;
	}

}
