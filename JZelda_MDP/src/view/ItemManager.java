package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameConfig;

public class ItemManager {

	private BufferedImage[] itemImages;
	
	public ItemManager() {
		itemImages = loadItems("/resources/objects/itemset.png", GameConfig.ORIGINAL_TILE_SIZE, GameConfig.ORIGINAL_TILE_SIZE);
	}
	
	/**
	 * Loads a path in which to separate items. It takes only a sheet with one row.
	 * The method cycles through each column, adding the rectangle defined by the
	 * parameters to the array. The tiles can then be connected to their logic
	 * counterpart coming from the model.
	 * @param path the path of the itemset
	 * @param tileWidth the width of each item
	 * @param tileHeight the height of each item
	 * @return a set of usable items, accessible by id
	 */
	private BufferedImage[] loadItems(String path, int tileWidth, int tileHeight) {
		
		BufferedImage itemSheet;
		try {
			itemSheet = ImageIO.read(getClass().getResourceAsStream(path));
		 	
		int itemSheetLength = itemSheet.getWidth() / tileWidth;
		
		BufferedImage[] itemSet = new BufferedImage[itemSheetLength];
        for (int id = 0; id < itemSheetLength; id++) {
        	itemSet[id] = itemSheet.getSubimage( tileWidth * id, 0, tileWidth, tileHeight);
        }
        return itemSet;		
		}
		catch (IOException e) {
			throw new ImageLoadingException("Could not load the itemset: " + path);
		}
	}
	
	public BufferedImage getItemImage(int id) { return itemImages[id]; }
	
}

