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
		    new Tile(18, false),	// SAND 2
		    new Tile(19, true),		// DUNGEON CORNER TOP LEFT
		    new Tile(20, true),		// DUNGEON CORNER WALL TOP LEFT
		    new Tile(21, true),		// DUNGEON WALL TOP
		    new Tile(22, true),		// DUNGEON CORNER WALL TOP RIGHT
		    new Tile(23, true),		// DUNGEON CORNER TOP RIGHT
		    new Tile(24, true),		// DUNGEON WALL TOP LEFT
		    new Tile(25, true),		// DUNGEON WALL LEFT
		    new Tile(26, false),		// DUNGEON DUNGEON FLOOR TILE
		    new Tile(27, true),		// DUNGEON WALL RIGHT
		    new Tile(28, true),		// DUNGEON WALL TOP RIGHT
		    new Tile(29, true),		// DUNGEON CORNER BOTTOM LEFT
		    new Tile(30, true),		// DUNGEON CORNER WALL BOTTOM LEFT
		    new Tile(31, true),		// DUNGEON WALL BOTTOM
		    new Tile(32, true),		// DUNGEON CORNER WALL BOTTOM RIGHT
		    new Tile(33, true),		// DUNGEON CORNER BOTTOM RIGHT
		    new Tile(34, true),		// DUNGEON DOOR CLOSED SOUTH
		    new Tile(35, false),	// DUNGEON DOOR CLOSED NORTH
		    new Tile(36, false),	// DUNGEON DOOR OPEN SOUTH
		    new Tile(37, false),	// DUNGEON DOOR OPEN NORTH
		    new Tile(38, false),	// SAND GRASS MESH
		    new Tile(39, false),	// CARPET TOP LEFT
		    new Tile(40, false),	// CARPET TOP
		    new Tile(41, false),	// CARPET TOP RIGHT
		    new Tile(42, false),	// CARPET MID LEFT
		    new Tile(43, false),	// CARPET MID
		    new Tile(44, false),	// CARPET MID RIGHT
		    new Tile(45, false),	// CARPET BOTTOM LEFT
		    new Tile(46, false),	// CARPET BOTTOM
		    new Tile(47, false),	// CARPET BOTTOM RIGHT
		    new Tile(48, true),		// SIGN
		};
	
	private TileStorage() {}
	
	public static Tile getTile(int tileId) { return tiles[tileId]; }
}
