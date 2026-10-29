package model;

import model.gameObjects.DoorObject;

/**
 * Contains the layout of the  world map's grid, 
 * and the counter that opens the boss room.
 */
public class WorldMap {

	private Room[][] map;
	private int killCounter;

	/**
	 * Initialises a new WorldMap, a grid of 4 rows and 3 columns.
	 */
	public WorldMap() {
		this.map = new Room[4][3];
		createMap();
	}

	public Room[][] getMap() {
		return map;
	}

	/**
	 * Generates the world map and initializes the kill counter.
	 * Accesses the roomloader to load each map, then puts it
	 * in the right position in the grid.
	 */
	public void createMap() {
		map[3][0] = RoomLoader.getRoom(3, 0);
		map[3][1] = RoomLoader.getRoom(3, 1);
		map[3][2] = RoomLoader.getRoom(3, 2);
		map[2][1] = RoomLoader.getRoom(2, 1);
		map[2][0] = RoomLoader.getRoom(2, 0);
		map[2][2] = RoomLoader.getRoom(2, 2);
		map[1][0] = RoomLoader.getRoom(1, 0);
		map[1][1] = RoomLoader.getRoom(1, 1);
		map[1][2] = RoomLoader.getRoom(1, 2);
		map[0][1] = RoomLoader.getRoom(0, 1);

		this.killCounter = 0;

	}

	public Room getRoom(int y, int x) {
		return map[y][x];
	}

	public void registerEnemyKill() {
		killCounter++;
	}

	public void registerEnemyKill(int amount) {
		killCounter += amount;
	}

	public void resetKillCounter() {
		killCounter = 0;
	}

	public int getKillCounter() {
		return killCounter;
	}

	/**
	 * Checks whether or not 10 kills have been reached.
	 * If true, unlocks the final door.
	 */
	public void unlockFinalDoor() {
		if (killCounter >= 10) {
			Entity door = map[1][1].searchEntity("dungeondoor");
			if (door instanceof DoorObject dungeonDoor)
				dungeonDoor.openDoor();
		}
	}
	
}
