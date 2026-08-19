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
	
	
}
