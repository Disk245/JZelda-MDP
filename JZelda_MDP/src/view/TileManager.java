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
	
	private BufferedImage[] loadTiles(String path, int tileWidth, int tileHeight) {
		
		BufferedImage tileSheet;
		try {
			tileSheet = ImageIO.read(getClass().getResourceAsStream(path));
		 	
		int tileSheetLength = tileSheet.getWidth() / tileWidth;
		
		BufferedImage[] result = new BufferedImage[tileSheetLength];
        for (int id = 0; id < tileSheetLength; id++) {
        	result[id] = tileSheet.getSubimage( tileWidth * id, 0, tileWidth, tileHeight);
        }
        return result;		
		}
		catch (IOException e) {
			throw new ImageLoadingException("Could not load the tileset: " + path);
		}
	}
	
	public BufferedImage getTileImage(int id) { return tileImages[id]; }
	
}
