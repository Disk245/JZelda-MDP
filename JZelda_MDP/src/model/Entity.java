package model;

import java.awt.Rectangle;

public abstract class Entity {

	private String id;
	protected int x;
	protected int y;
	private Rectangle solidArea;
	private boolean CollisionOn = true;
	
	public Entity(String id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCollisionOn() {
		return CollisionOn;
	}

	public void setCollisionOn(boolean collisionOn) {
		CollisionOn = collisionOn;
	}

	public Rectangle getSolidArea() {
		return solidArea;
	}

	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}

}
