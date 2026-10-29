package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A room. It is composed of 16 columns and 12 rows, each corresponding to a tile.
 * It also contains entities
 */
public class Room {
	
	private int[][] roomLayout;
	private List<Entity> entities;
	
	/**
	 * Creates a room wihtout entities
	 * 
	 * @param roomLayout the array representing the 16x12 tiles
	 */
	public Room(int[][] roomLayout) {
		this.roomLayout = roomLayout;
		this.entities = new ArrayList<>();
	}
	
	/**
	 * Creates a room with entities
	 * 
	 * @param roomLayout the array representing the 16x12 tiles
	 * @param entities the entities to spawn in the room
	 */
	public Room(int[][] roomLayout, List<Entity> entities) {
		this.roomLayout = roomLayout;
		this.entities = new ArrayList<>(entities);
	}

	public int[][] getRoomLayout() {
		return roomLayout;
	}

	public void setRoomLayout(int[][] roomLayout) {
		this.roomLayout = roomLayout;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
	
    /**
     * Finds a specific tile. Needed to draw them from the view.
     * 
     * @param y the tile's y position
     * @param x the tile's x position
     * @return the tile at those coordinates
     */
	public int getRoomTile(int y, int x) { return roomLayout[y][x]; }
	
	/**
	 * Finds an entity in the room
	 * @param id the searched id
	 * @return the entity whose id matches the input, none otherwise.
	 */
	public Entity searchEntity(String id) {
		for (Entity e : entities) {
			String entityId = e.getId();
			if (entityId.equals(id))
				return e;
		}
		return null;
	}
}
