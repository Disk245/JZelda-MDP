package model;

/**
 * A class containing the basic configuration values for the game,
 * needed for the logic to work properly.
 * For example, it contains the tile size and the scale factor, needed
 * to correctly calculate the entities position on the map.
 * Its constructor is private since it doesn't need any initialization.
 */
public class GameConfig {

    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 5;

    public static final int ROOM_COLUMNS = 16;
    public static final int ROOM_ROWS = 12;

    public static final int TILE_SIZE =
            ORIGINAL_TILE_SIZE * SCALE;

    public static final int SCREEN_WIDTH =
            ROOM_COLUMNS * TILE_SIZE;

    public static final int SCREEN_HEIGHT =
            ROOM_ROWS * TILE_SIZE;

    private GameConfig() {}
}
