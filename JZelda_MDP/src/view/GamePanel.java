package view;
import javax.imageio.ImageIO;
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
import model.NPC;
import model.Character;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import java.io.IOException;




public class GamePanel extends JPanel {
	
	private final GameModel model;
	private final AnimationManager animManager = new AnimationManager();
	private final TileManager tileManager = new TileManager();;
	private final ItemManager itemManager = new ItemManager();
	
	private BufferedImage heartImage;
		
	public GamePanel(GameModel model) {
		
		this.model = model;
		
	    setPreferredSize(new Dimension(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT));
	    setBackground(Color.GRAY);
	    setDoubleBuffered(true);
	    
	    this.heartImage = loadHeartImage();
	}
	
	
    public void updateVisuals() {
        repaint();
    }
    
    private BufferedImage loadHeartImage() {
        try {
            return ImageIO.read(getClass().getResourceAsStream("/resources/hud/heart.png"));
        } catch (IOException exception) {
            throw new ImageLoadingException("Could not load HUD heart image");
        }
    }
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		drawRoom(g2d);
		drawEntities(g2d);
		drawPlayer(g2d);
		drawHUD(g2d);
		
	    if (model.getGameState() == GameState.DIALOGUE) 
	    	drawDialogueBox(g2d);
	    
	}
	
	public void drawPlayer(Graphics2D g2d) {
		
		Player player = model.getPlayer();
		Animation animation = animManager.getPlayerAnimation(player.getCharacterState(), player.getDirection()); 
		
		drawCharacter(g2d, animation, player);
	}
	
	public void drawCharacter(Graphics2D g2d, Animation animation, Character character) {
		if (animation != null) {
			BufferedImage image = animation.getCurrentFrame(character.getStateTicks());
			
			int drawX = character.getX();
			int drawY = character.getY();
			
			int drawWidth = image.getWidth() * GameConfig.SCALE;
			int drawHeight = image.getHeight() * GameConfig.SCALE;
			
			if (character.getCharacterState() == CharacterState.ATTACKING) {
			    switch (character.getDirection()) {
			        case UP:
			            drawY -= drawHeight - GameConfig.TILE_SIZE;
			            break;

			        case LEFT:
			            drawX -= drawWidth - GameConfig.TILE_SIZE;
			            break;

			        case DOWN:
			        	break;
			        case RIGHT:
			            break;
			    }
			}
			
			if (image != null) g2d.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);			
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
			if (entity instanceof NPC npc) {
				drawNPC(g2d, npc);
			}
				
		}
	}
	
	public void drawNPC(Graphics2D g2d, NPC npc) {
	    Animation animation = animManager.getShopkeeperAnimation(npc.getCharacterState(), npc.getDirection());
	    if (animation == null) 
	        return;

	    BufferedImage image = animation.getCurrentFrame(npc.getStateTicks());

	    if (image != null) {
	        g2d.drawImage(image, npc.getX(), npc.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
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
	
	private void drawHUD(Graphics2D g2d) {
		Player player = model.getPlayer();
		
		int heartSize = 8 * GameConfig.SCALE;
		
		int iconSize = (GameConfig.TILE_SIZE * 3) / 4;
		int distance = 10;
		
		int iconX = distance;
		int iconY = distance;
		
		for (int i = 0; i < player.getCurrentHealth(); i++) {
			g2d.drawImage(heartImage, iconX, iconY, heartSize, heartSize, null);
			iconX += heartSize + distance;
		}
		
		iconY += heartSize + distance;
		iconX = distance;
		
		for (GameObject item : player.getInventory()) {
			BufferedImage itemImage = itemManager.getItemImage(item.getSpriteId());
			
			if (itemImage != null) {
				g2d.drawImage(itemImage, iconX, iconY, iconSize, iconSize, null);
			}
			iconX += iconSize + distance;
		}
	}
	
}
