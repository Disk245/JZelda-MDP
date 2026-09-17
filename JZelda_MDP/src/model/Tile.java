package model;

/**
 * A single tile. It has and ID to communicate to the view what to draw, and can
 * have collisions or not.
 */
public class Tile {

	private int tileId;
	private boolean collision = false;

	/**
	 * Creates a tile
	 * 
	 * @param tileId    the tile's id
	 * @param collision whether or not the tile has collisions.
	 */
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
