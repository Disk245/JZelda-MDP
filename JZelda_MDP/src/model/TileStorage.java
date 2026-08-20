package model;

public class TileStorage {

	private static final Tile[] tiles = {
		    new Tile(0, false), 	// GRASS
		    new Tile(1, true),  	// TREE
		    new Tile(2, false),  	// SAND
		    new Tile(3, true),  	// CLIFF BOTTOM LEFT
		    new Tile(4, true),		// CLIFF BOTTOM
		    new Tile(5, true),		// CLIFF BOTTOM RIGHT		
		    new Tile(6, true),		// CLIFF RIGHT
		    new Tile(7, true),		// CLIFF TOP RIGHT
		    new Tile(8, true),		// CLIFF TOP
		    new Tile(9, true),		// CLIFF TOP LEFT
		    new Tile(10, true),		// CLIFF LEFT
		    new Tile(11, true),		// WATER 1
		    new Tile(12, true),		// WATER 2
		    new Tile(13, true),		// WATER SHORE
		    new Tile(14, true),		// ROCK
		    new Tile(15, true),		// CLIFF DIAGONAL RIGHT
		    new Tile(16, true),		// CLIFF DIAGONAL LEFT
		    new Tile(17, false),	// GRASS 2
		    new Tile(18, true),		// DUNGEON WALL
		    new Tile(19, true),		// DUNGEON FLOOR
		};
	
	private TileStorage() {}
	
	public static Tile getTile(int tileId) { return tiles[tileId]; }
}
