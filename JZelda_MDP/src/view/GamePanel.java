package view;
import javax.swing.*;

import model.Player;
import model.Room;
import model.WorldMap;
import model.Character.CharacterState;
import model.Character.Direction;
import model.GameConfig;
import model.GameModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;




public class GamePanel extends JPanel {
	
	private final GameModel model;
	private final AnimationManager animManager = new AnimationManager();
	private final TileManager tileManager = new TileManager();;
		
	public GamePanel(GameModel model) {
		
		this.model = model;
		
	    setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
	    setBackground(Color.GRAY);
	    setDoubleBuffered(true);
	}
	
	
    public void updateVisuals() {
        repaint();
    }
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		drawRoom(g2d);
		drawPlayer(g2d);
	}
	
	public void drawPlayer(Graphics2D g2d) {
		Player player = model.getPlayer();
		Animation animation = animManager.getPlayerAnimation(player.getCharacterState(), player.getDirection()); 
		
		if (animation != null) {
			BufferedImage image = animation.getCurrentFrame(player.getStateTicks());
			if (image != null) g2d.drawImage(image, player.getX(), player.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);			
		}
	}
	
	public void drawRoom(Graphics2D g2d) {
		int[][] currentRoom = model.getCurrentRoom().getRoomLayout();
		
		for (int y = 0; y < currentRoom.length; y++) {
			for (int x = 0; x < currentRoom[0].length; x++) {
				int tileId = currentRoom[y][x];
				
				g2d.drawImage(tileManager.getTileImage(tileId), x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE, 
						GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
			}
		}
	}
	
}
