package view;
import javax.swing.*;

import model.Player;
import model.Character.CharacterState;
import model.Character.Direction;
import model.GameModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;




public class GamePanel extends JPanel {
	
	private GameModel model;
	private AnimationManager animManager = new AnimationManager();
	
	private static final int ORIGINAL_TILE_SIZE = 16;
	private static final int COLUMNS = 16;
	private static final int ROWS = 12;
	private static final int SCALE = 5;
	private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
	private static final int SCREEN_WIDTH = COLUMNS * TILE_SIZE;
	private static final int SCREEN_HEIGHT = ROWS * TILE_SIZE;
	
	public GamePanel(GameModel model) {
		this.model = model;
		
	    setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));

	        setBackground(Color.GRAY);
	        setDoubleBuffered(true);
	}
	
	
    public void updatePlayer() {
		Player player = model.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();
        CharacterState playerState = player.getCharacterState();
        Direction playerDirection = player.getDirection();
        repaint();
    }
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		drawPlayer(g2d);
		// Roba per disegnare mappa, entità, hud
	}
	
	public void drawPlayer(Graphics2D g2d) {
		Player player = model.getPlayer();
		Animation animation = animManager.getPlayerAnimation(player.getCharacterState(), player.getDirection()); 
		
		if (animation != null) {
			BufferedImage image = animation.getCurrentFrame(player.getStateTicks());
			if (image != null) g2d.drawImage(image, player.getX(), player.getY(), TILE_SIZE, TILE_SIZE, null);
				
		}

	}
	
}
