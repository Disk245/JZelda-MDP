package model;

import java.awt.Rectangle;

public abstract class Character extends Entity {

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	public enum CharacterState {
		IDLE, WALKING, ATTACKING, HURT, DEAD
	}

	private String name;
	private Direction direction = Direction.DOWN;
	private CharacterState characterState = CharacterState.IDLE;
	private int stateTicks = 0;
	private boolean colliding = false;
	protected int invincibilityFrames;
	protected int attackDuration;
	protected int attackCooldown;
	private int attackCooldownTicks = 0;
	protected int deathDuration;
	private boolean godMode = false;
	protected int attackRange;
	private boolean knockback;
	private int knockbackCounter;
	private int knockbackDuration;
	private Direction knockbackDirection;

	// Stats
	protected int maxHealth;
	protected int currentHealth;
	protected int attackDamage;
	protected int characterSpeed;

	public Character(String id, int x, int y, String name, int characterSpeed) {
		super(id, x, y);
		this.name = name;
		this.characterSpeed = characterSpeed;
		this.deathDuration = 65;
	}

	/**
	 * Calculates the amount of space to move
	 * 
	 * @param deltaX the change in the x axis
	 * @param deltaY the change in the y axis
	 */
	public void translate(int deltaX, int deltaY) {
		x += deltaX;
		y += deltaY;
	}

	/**
	 * Moves the player
	 * 
	 * @param deltaX the amount of space to move on the x axis
	 * @param deltaY the amount of space to moveo nthe y axis
	 */
	public void move(int deltaX, int deltaY) {
		translate(deltaX, deltaY);
		setCharacterState(CharacterState.WALKING);
	}

	public void stop() {
		setCharacterState(CharacterState.IDLE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getCharacterSpeed() {
		return characterSpeed;
	}

	public void setCharacterSpeed(int characterSpeed) {
		this.characterSpeed = characterSpeed;
	}

	public CharacterState getCharacterState() {
		return characterState;
	}

	public void setCharacterState(CharacterState characterState) {
		if (characterState != this.characterState) {
			this.characterState = characterState;
			stateTicks = 0;
		}
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}

	public int getStateTicks() {
		return stateTicks;
	}

	/**
	 * Updates the character's logic
	 */
	public void update() {
		stateTicks++;

		if (attackCooldownTicks > 0) {
			attackCooldownTicks--;
		}

		if (characterState == CharacterState.ATTACKING && stateTicks >= attackDuration) {
			setCharacterState(CharacterState.IDLE);
		}

		if (characterState == CharacterState.HURT && stateTicks >= invincibilityFrames) {
			setCharacterState(CharacterState.IDLE);
		}

	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	/**
	 * Damages the character. Godmode skips damage
	 * 
	 * @param damage the amount of damage received
	 * @return true if damage is succesfully applied
	 */
	public boolean takeDamage(int damage) {
		if (godMode || getCharacterState() == CharacterState.HURT || getCharacterState() == CharacterState.DEAD) {
			return false;
		}

		currentHealth -= damage;

		if (currentHealth <= 0) {
			currentHealth = 0;
			this.setSolidArea(new Rectangle(0, 0, 0, 0));
			setCharacterState(CharacterState.DEAD);
		} else {
			setCharacterState(CharacterState.HURT);
		}

		return true;
	}

	/**
	 * Generates the character's attack area, according to the direction.
	 * 
	 * @return a new Rectangle that extends the original character area.
	 */
	public Rectangle getAttackArea() {
		int size = attackRange;
		Rectangle body = getSolidArea();

		int worldX = getX() + body.x;
		int worldY = getY() + body.y;

		return switch (getDirection()) {
		case UP -> new Rectangle(worldX, worldY - size, body.width, size);
		case DOWN -> new Rectangle(worldX, worldY + body.height, body.width, size);
		case LEFT -> new Rectangle(worldX - size, worldY, size, body.height);
		case RIGHT -> new Rectangle(worldX + body.width, worldY, size, body.height);
		};
	}

	/**
	 * Checks whether ot not the character can perform an attack
	 * 
	 * @return true if the attack can be performed
	 */
	public boolean canAttack() {
		return attackCooldownTicks == 0 && characterState != CharacterState.ATTACKING
				&& characterState != CharacterState.HURT && characterState != CharacterState.DEAD;
	}

	protected void startAttackCooldown() {
		attackCooldownTicks = attackCooldown;
	}

	/**
	 * Performs the character's attack
	 */
	public void attack() {
		if (!canAttack()) {
			return;
		}

		setCharacterState(CharacterState.ATTACKING);
		startAttackCooldown();
	}

	/**
	 * Generates a projectile
	 * 
	 * @return the produced projectile
	 */
	public Projectile shoot() {
		int projectileX = getX();
		int projectileY = getY();
		int offset = GameConfig.TILE_SIZE / 2;

		switch (direction) {
		case UP:
			projectileY -= offset;
			break;

		case DOWN:
			projectileY += offset;
			break;

		case LEFT:
			projectileX -= offset;
			break;

		case RIGHT:
			projectileX += offset;
			break;
		}

		return (new Projectile(getId() + "_projectile", projectileX, projectileY, direction, 10, getAttackDamage(),
				this));
	}

	public boolean isDeathAnimationOver() {
		return characterState == CharacterState.DEAD && stateTicks >= deathDuration;
	}

	public boolean isGodMode() {
		return godMode;
	}

	public void setGodMode(boolean godMode) {
		this.godMode = godMode;
	}

	/**
	 * Starts character knockback. It also ensures the character keeps facing the
	 * direction it's pushed away from.
	 * 
	 * @param direction
	 */
	public void startKnockback(Direction direction) {
		knockbackDirection = direction;

		switch (direction) {
		case UP:
			setDirection(Direction.DOWN);
			break;
		case DOWN:
			setDirection(Direction.UP);
			break;
		case LEFT:
			setDirection(Direction.RIGHT);
			break;
		case RIGHT:
			setDirection(Direction.LEFT);
			break;
		}

		knockback = true;
		knockbackCounter = 0;

		knockbackDuration = (int) Math.ceil((double) GameConfig.TILE_SIZE / getCharacterSpeed());
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	/*
	 * This section checks all other methods relative to the knockback
	 */
	public boolean isInKnockback() {
		return knockback;
	}

	public void updateKnockback() {
		knockbackCounter++;

		if (knockbackCounter >= knockbackDuration) {
			stopKnockback();
		}
	}

	public Direction getKnockbackDirection() {
		return knockbackDirection;
	}

	public void stopKnockback() {
		knockback = false;
		knockbackCounter = 0;
	}

}
