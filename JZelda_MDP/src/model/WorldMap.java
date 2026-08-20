package model;

public class WorldMap {
	
	private Room[][] map;
	
	public WorldMap() {
		this.map = new Room[4][3];
		createMap();
	}
	
	public Room[][] getMap() { return map; }
	
	public void createMap() {
		map[3][0] = RoomStorage.createRoom30();
		
	}
	
	public Room getRoom(int y, int x) { return map[y][x]; }
}
