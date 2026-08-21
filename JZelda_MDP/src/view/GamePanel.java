package view;
import javax.swing.*;

import model.Player;
import model.Room;
import model.WorldMap;
import model.Character.CharacterState;
import model.Character.Direction;
import model.Entity;
import model.GameConfig;
import model.GameModel;
import model.GameModel.GameState;
import model.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;




public class GamePanel extends JPanel {
	
	private final GameModel model;
	private final AnimationManager animManager = new AnimationManager();
	private final TileManager tileManager = new TileManager();;
	private final ItemManager itemManager = new ItemManager();
		
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
		drawEntities(g2d);
		drawPlayer(g2d);
		
	    if (model.getGameState() == GameState.DIALOGUE) 
	    	drawDialogueBox(g2d);
	    
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
				BufferedImage image = tileManager.getTileImage(tileId);
				g2d.drawImage(image, x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE, 
						GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
			}
		}
	}
	
	public void drawEntities(Graphics2D g2d) {
		
		for (Entity entity : model.getCurrentRoom().getEntities()) {
			
			if (entity instanceof GameObject gameObject) {
				BufferedImage image = itemManager.getItemImage(gameObject.getSpriteId());
				g2d.drawImage(image, gameObject.getX(), gameObject.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
			}
				
		}
	}
	
	public void drawDialogueBox(Graphics2D g2d) {
		
		// Window
		int width = GameConfig.TILE_SIZE * 10;
		int height = GameConfig.TILE_SIZE * 3;
		int x = (GameConfig.SCREEN_WIDTH - width) / 2;
		int y = GameConfig.SCREEN_HEIGHT - height - GameConfig.TILE_SIZE / 3;
		
		drawSubWindow(g2d, x,y,width,height);
		
		g2d.setFont(FontManager.getFont(24f));
		FontMetrics metrics = g2d.getFontMetrics();
		
		x += GameConfig.TILE_SIZE / 2;
		y += GameConfig.TILE_SIZE / 1.5;
		for (String s : model.getCurrentDialogue()) {
			g2d.drawString(s,x,y);
			y += metrics.getHeight() * 1.5;
		}
	}
	
	public void drawSubWindow(Graphics2D g2d, int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,200);
		g2d.setColor(c);
		g2d.fillRoundRect(x, y, width, height, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
		
		c = new Color(255,255,255);
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRoundRect(x+5, y+5, width-10, height-10, GameConfig.TILE_SIZE-10, GameConfig.TILE_SIZE-10);
		
	}
	
}
