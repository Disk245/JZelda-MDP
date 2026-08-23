package model;

import java.awt.Rectangle;

import model.Character.Direction;

public class Projectile extends Entity {
	
	// IN FUTURO DISTINGUERE TRA PROIETTILI GIOCATORE E NEMICI
	
    private final Direction direction;
    private final int speed;
    private final int damage;
    private int lifeTicks;
    private int animationTicks;
    private boolean expired;
    private Character shooter;

    public Projectile(String id, int x, int y, Direction direction, int speed, int damage, Character shooter) {

        super(id, x, y);
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        this.lifeTicks = 120;
        this.shooter = shooter;

        setSolidArea(new Rectangle( 4 * GameConfig.SCALE, 4 * GameConfig.SCALE, 8 
        		* GameConfig.SCALE, 8 * GameConfig.SCALE));
    }

    public void update() {
        switch (direction) {
            case UP    -> y -= speed;
            case DOWN  -> y += speed;
            case LEFT  -> x -= speed;
            case RIGHT -> x += speed;
        }

        animationTicks++;
        lifeTicks--;

        if (lifeTicks <= 0) {
            expired = true;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDamage() {
        return damage;
    }

    public int getAnimationTicks() {
        return animationTicks;
    }

    public boolean isExpired() {
        return expired;
    }

    public void expire() {
        expired = true;
    }
    
    public Character getShooter() { return shooter; }
    
}
