package model;

public class WorldMap {
	
	private Room[][] map;
	
	public WorldMap() {
		this.map = new Room[4][3];
		createMap();
	}
	
	public Room[][] getMap() { return map; }
	
	public void createMap() {
		map[3][0] = RoomLoader.getRoom(3,0);
		map[3][1] = RoomLoader.getRoom(3,1);
		map[3][2] = RoomLoader.getRoom(3,2);
		map[2][1] = RoomLoader.getRoom(2,1);
		map[2][0] = RoomLoader.getRoom(2,0);
		map[2][2] = RoomLoader.getRoom(2,2);
		map[1][0] = RoomLoader.getRoom(1,0);
		map[1][1] = RoomLoader.getRoom(1,1);
		map[1][2] = RoomLoader.getRoom(1,2);
		map[0][1] = RoomLoader.getRoom(0,1);
		
	}
	
	public Room getRoom(int y, int x) { return map[y][x]; }
}
