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
	 * Loads a path in which to seprate tiles. It takes only a tileset with one row.
	 * The method cycles through each column, adding the rectangle defined by the
	 * parameters to the array. The tiles can then be connected to their logic
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
		
		BufferedImage[] tileSet = new BufferedImage[tileSheetLength];
        for (int id = 0; id < tileSheetLength; id++) {
        	tileSet[id] = tileSheet.getSubimage( tileWidth * id, 0, tileWidth, tileHeight);
        }
        return tileSet;		
		}
		catch (IOException e) {
			throw new ImageLoadingException("Could not load the tileset: " + path);
		}
	}
	
	public BufferedImage getTileImage(int id) { return tileImages[id]; }
	
}
