package model;

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
