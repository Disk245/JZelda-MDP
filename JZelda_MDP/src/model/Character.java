package model;

import java.awt.Rectangle;

public abstract class Character extends Entity{
	
	public enum Direction {UP, DOWN, LEFT, RIGHT};
	public enum CharacterState {IDLE, WALKING, ATTACKING, HURT, DEAD}

	private String name;
	private Direction direction = Direction.DOWN;
	private CharacterState characterState = CharacterState.IDLE;
	private int stateTicks = 0;
	private boolean colliding = false;
	protected int invincibilityFrames;
	protected int attackDuration;
	protected int attackCooldown;
	private int attackCooldownTicks = 0;
	
	// Stats
	protected int maxHealth;
	protected int currentHealth;
	protected int attackDamage;
	protected int characterSpeed;
	
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

	public void takeDamage(int damage) {
	    if (getCharacterState() == CharacterState.HURT || getCharacterState() == CharacterState.DEAD) {
	        return; // Can't be hit during hurt or death animation
	    }
	    currentHealth -= damage;
	    if (currentHealth <= 0) {
	        currentHealth = 0;
	        setCharacterState(CharacterState.DEAD);
	    } else {
	        setCharacterState(CharacterState.HURT);
	    }
	}
	
	public Rectangle getAttackArea() {
	    int size = GameConfig.TILE_SIZE;
	    Rectangle body = getSolidArea();

	    int worldX = getX() + body.x;
	    int worldY = getY() + body.y;

	    return switch (getDirection()) {
	        case UP    -> new Rectangle(worldX, worldY - size, body.width, size);
	        case DOWN  -> new Rectangle(worldX, worldY + body.height, body.width, size);
	        case LEFT  -> new Rectangle(worldX - size, worldY, size, body.height);
	        case RIGHT -> new Rectangle(worldX + body.width, worldY, size, body.height);
	    };
	}
	
	public boolean canAttack() {
	    return attackCooldownTicks == 0 && characterState != CharacterState.ATTACKING && 
	    		characterState != CharacterState.HURT && characterState != CharacterState.DEAD;
	}
	
	protected void startAttackCooldown() {
	    attackCooldownTicks = attackCooldown;
	}
	
	public void attack() {
	    if (!canAttack()) {
	        return;
	    }

	    setCharacterState(CharacterState.ATTACKING);
	    startAttackCooldown();
	}
}
