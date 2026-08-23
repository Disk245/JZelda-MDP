package model;

import java.awt.Rectangle;

public abstract class Entity {

	protected String id;
	protected int x;
	protected int y;
	private Rectangle solidArea;
	private boolean collisionOn = true;
	
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
		return collisionOn;
	}

	public void setCollisionOn(boolean collision) {
		collisionOn = collision;
	}

	public Rectangle getSolidArea() {
		return solidArea;
	}

	public void setSolidArea(Rectangle solidArea) {
		this.solidArea = solidArea;
	}
	
    /**
     * Places the area in the entity's real coordinates
     * @return the area correctly placed
     */
	public Rectangle getWorldArea() {
	    if (solidArea == null) 
	        return new Rectangle(x, y, 0, 0);
	    return new Rectangle( x + solidArea.x, y + solidArea.y, 
	    		solidArea.width, solidArea.height);
	}

}
