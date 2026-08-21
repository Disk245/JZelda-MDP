package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
	
	private int[][] roomLayout;
	private List<Entity> entities;
	
	public Room(int[][] roomLayout) {
		this.roomLayout = roomLayout;
		this.entities = new ArrayList<>();
	}
	
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
	
	public int getRoomTile(int y, int x) { return roomLayout[y][x]; }
	
	public Entity searchEntity(String id) {
		for (Entity e : entities) {
			String entityId = e.getId();
			if (entityId.equals(id))
				return e;
		}
		return null;
	}
}
