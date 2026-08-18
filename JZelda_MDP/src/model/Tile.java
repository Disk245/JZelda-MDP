package model;

public class Tile {
	
	private int tileId;
	private boolean collision = false;
		
	public Tile(int tileId, boolean collision) {
		this.tileId = tileId;
		this.collision = collision;
	}
	

	public int getTileId() {
		return tileId;
	}
	public boolean hasCollision() {
		return collision;
	}
	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	
	
}
