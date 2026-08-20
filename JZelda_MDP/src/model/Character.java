package model;

public abstract class Character extends Entity{
	
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	public enum CharacterState {IDLE, WALKING, ATTACKING, HURT, DEAD}

	private String name;
	private Direction direction = Direction.DOWN;
	private CharacterState characterState = CharacterState.IDLE;
	private int stateTicks = 0;
	private boolean colliding = false;
	
	// Stats
	private int maxHealth;
	private int currentHealth;
	private int attackDamage;
	private int characterSpeed;
	
	public Character(String id, int x, int y, String name, int characterSpeed) {
		super(id,x,y);
		this.name = name;
		this.characterSpeed = characterSpeed;
	}
	
	public void move(int deltaX, int deltaY) {
		x += deltaX;
		y += deltaY;
		setCharacterState(CharacterState.WALKING);
	}
	
	public void stop() { setCharacterState(CharacterState.IDLE); }
	
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

	public int getStateTicks() { return stateTicks; }
	public void update() { stateTicks++; }
	
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

	public void takeDamage(int damage) {
	    currentHealth -= damage;

	    if (currentHealth <= 0) {
	        currentHealth = 0;
	        setCharacterState(CharacterState.DEAD);
	    }
	}
}
