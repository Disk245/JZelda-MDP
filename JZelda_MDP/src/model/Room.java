package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
	
	private int[][] map;
	private List<Entity> entities;
	
	public Room(int[][] map) {
		this.map = map;
		this.entities = new ArrayList<>();
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	
}
