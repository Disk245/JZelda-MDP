package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameConfig;
import model.Tile;

public class TileManager {
	private BufferedImage[] tileImages;
	
	public TileManager() {
		tileImages = loadTiles("/resources/tiles/tileset.png", GameConfig.ORIGINAL_TILE_SIZE, GameConfig.ORIGINAL_TILE_SIZE);
	}
	
	/**
	 * Loads a path in which to seprate tiles.
	 * The method cycles through each column, adding the rectangle defined by the
	 * parameters to the array. 
	 * Then, it switches to the second row and repeats the process until completion.
	 * The tiles can then be connected to their logic
	 * counterpart coming from the model.
	 * @param path the path of the tileset
	 * @param tileWidth the width of each tile
	 * @param tileHeight theh eight of each tile
	 * @return a set of usable ties, accessible by id
	 */
	private BufferedImage[] loadTiles(String path, int tileWidth, int tileHeight) {
		
		BufferedImage tileSheet;
		try {
			tileSheet = ImageIO.read(getClass().getResourceAsStream(path));
		 	
		int tileSheetLength = tileSheet.getWidth() / tileWidth;
		int tileSheetHeight = tileSheet.getHeight() / tileHeight;
		
		BufferedImage[] tileSet = new BufferedImage[tileSheetLength * tileSheetHeight];
		int id = 0;
		for (int row = 0; row < tileSheetHeight; row++) {
			for (int column = 0; column < tileSheetLength; column++) {
				tileSet[id] = tileSheet.getSubimage( tileWidth * column, tileHeight * row, tileWidth, tileHeight);
				id++;
			}
		}
        return tileSet;		
		}
		catch (IOException e) {
			throw new ImageLoadingException("Could not load the tileset: " + path);
		}
	}
	
	public BufferedImage getTileImage(int id) { return tileImages[id]; }
	
}
