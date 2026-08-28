package view;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Player;
import model.Projectile;
import model.Room;
import model.Slime;
import model.Character.CharacterState;
import model.Enemy;
import model.Entity;
import model.EvilMage;
import model.GameConfig;
import model.GameModel;
import model.GameModel.GameState;
import model.GameObject;
import model.NPC;
import model.Character;

import java.awt.*;
import java.awt.image.BufferedImage;
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

	/**
	 * loads the image of the heart to display in the HUD
	 * 
	 * @return the heart's image
	 */
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
		drawProjectiles(g2d);
		drawPlayer(g2d);
		drawHUD(g2d);

		if (model.getGameState() == GameState.DIALOGUE)
			drawDialogueBox(g2d);

	}

	/**
	 * Handles player drawing
	 * 
	 * @param g2d the graphics object
	 */
	public void drawPlayer(Graphics2D g2d) {

		Player player = model.getPlayer();
		Animation animation = animManager.getPlayerAnimation(player.getCharacterState(), player.getDirection());

		drawCharacter(g2d, animation, player);
	}

	/**
	 * Handles the logic behin the drawing of any character
	 * 
	 * @param g2d       the graphics object
	 * @param animation the animation to show
	 * @param character the character to draw
	 */
	public void drawCharacter(Graphics2D g2d, Animation animation, Character character) {
		if (animation != null) {
			BufferedImage image;

			if (character.getCharacterState() == CharacterState.DEAD)
				image = animation.getCurrentFrameOnce(character.getStateTicks());
			else
				image = animation.getCurrentFrame(character.getStateTicks());

			int drawX = character.getX();
			int drawY = character.getY();

			int drawWidth = image.getWidth() * GameConfig.SCALE;
			int drawHeight = image.getHeight() * GameConfig.SCALE;

			if (character.getCharacterState() == CharacterState.ATTACKING
					|| character.getCharacterState() == CharacterState.DEAD) {
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

			if (image != null)
				g2d.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
		}
	}

	/**
	 * Draws the current room
	 * 
	 * @param g2d the graphics object
	 */
	public void drawRoom(Graphics2D g2d) {
		int[][] currentRoom = model.getCurrentRoom().getRoomLayout();

		for (int y = 0; y < currentRoom.length; y++) {
			for (int x = 0; x < currentRoom[0].length; x++) {
				int tileId = currentRoom[y][x];
				BufferedImage image = tileManager.getTileImage(tileId);
				g2d.drawImage(image, x * GameConfig.TILE_SIZE, y * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
						GameConfig.TILE_SIZE, null);
			}
		}
	}

	/**
	 * Handles entity drawing
	 * 
	 * @param g2d the graphics object
	 */
	public void drawEntities(Graphics2D g2d) {

		for (Entity entity : model.getCurrentRoom().getEntities()) {

			if (entity instanceof GameObject gameObject) {
				BufferedImage image = itemManager.getItemImage(gameObject.getSpriteId());
				g2d.drawImage(image, gameObject.getX(), gameObject.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
						null);
			}
			if (entity instanceof NPC npc) {
				drawNPC(g2d, npc);
			}
			if (entity instanceof Enemy enemy) {
				drawEnemy(g2d, enemy);
			}

		}
	}

	/**
	 * Handles enemy drawing
	 * 
	 * @param g2d   the graphics object
	 * @param enemy the enemy to draw
	 */
	private void drawEnemy(Graphics2D g2d, Enemy enemy) {
		Animation animation = null;
		if (enemy instanceof Slime slime) {
			animation = animManager.getSlimeAnimation(enemy.getCharacterState(), enemy.getDirection());
		} else if (enemy instanceof EvilMage mage) {
			animation = animManager.getEvilMageAnimation(enemy.getCharacterState(), enemy.getDirection());
		}

		if (animation != null)
			drawCharacter(g2d, animation, enemy);

	}

	/**
	 * Handles NPC drawing
	 * 
	 * @param g2d the graphics object
	 * @param npc the npc to draw
	 */
	public void drawNPC(Graphics2D g2d, NPC npc) {
		Animation animation = animManager.getShopkeeperAnimation(npc.getCharacterState(), npc.getDirection());
		if (animation == null)
			return;

		BufferedImage image = animation.getCurrentFrame(npc.getStateTicks());

		if (image != null) {
			g2d.drawImage(image, npc.getX(), npc.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
		}
	}

	/**
	 * Draws the dialogue box
	 * 
	 * @param g2d the graphics object
	 */
	public void drawDialogueBox(Graphics2D g2d) {

		// Window
		int width = GameConfig.TILE_SIZE * 10;
		int height = GameConfig.TILE_SIZE * 3;
		int x = (GameConfig.SCREEN_WIDTH - width) / 2;
		int y = GameConfig.SCREEN_HEIGHT - height - GameConfig.TILE_SIZE / 3;

		drawSubWindow(g2d, x, y, width, height);

		g2d.setFont(FontManager.getFont(24f));
		FontMetrics metrics = g2d.getFontMetrics();

		x += GameConfig.TILE_SIZE / 2;
		y += GameConfig.TILE_SIZE / 1.5;
		for (String s : model.getCurrentDialogue()) {
			g2d.drawString(s, x, y);
			y += metrics.getHeight() * 1.5;
		}
	}

	/**
	 * Draws the window in which to set the dialogue text
	 * 
	 * @param g2d    the graphics object
	 * @param x      the position of the window's top corner on the x axis
	 * @param y      the position of the window's top corner in the y axis
	 * @param width  the box's width
	 * @param height the box's height
	 */
	public void drawSubWindow(Graphics2D g2d, int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 200);
		g2d.setColor(c);
		g2d.fillRoundRect(x, y, width, height, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);

		c = new Color(255, 255, 255);
		g2d.setColor(c);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, GameConfig.TILE_SIZE - 10, GameConfig.TILE_SIZE - 10);

	}

	/**
	 * Draws the HUD. It takes the sprites from the items folder and the hearts from
	 * the hud folder.
	 * 
	 * @param g2d the graphics object
	 */
	private void drawHUD(Graphics2D g2d) {
		Player player = model.getPlayer();

		// Health

		int heartSize = 8 * GameConfig.SCALE;
		int iconSize = (GameConfig.TILE_SIZE * 3) / 4;
		int distance = 10;
		int iconX = distance;
		int iconY = distance;

		for (int i = 0; i < player.getCurrentHealth(); i++) {
			g2d.drawImage(heartImage, iconX, iconY, heartSize, heartSize, null);
			iconX += heartSize + distance;
		}

		// Coins

		iconX = distance;
		iconY += heartSize + distance;

		BufferedImage coinImage = itemManager.getItemImage(0);

		if (coinImage != null) {
			g2d.drawImage(coinImage, iconX, iconY, iconSize, iconSize, null);
		}

		g2d.setColor(Color.WHITE);
		g2d.setFont(FontManager.getFont(30f));

		String coinText = "x " + player.getCoins();
		int textX = iconX + iconSize + distance;
		int textY = iconY + iconSize / 2 + g2d.getFontMetrics().getAscent() / 2;

		g2d.drawString(coinText, textX, textY);

		// Items

		iconY += iconSize + distance;
		iconX = distance;

		for (GameObject item : player.getInventory()) {
			BufferedImage itemImage = itemManager.getItemImage(item.getSpriteId());

			if (itemImage != null) {
				g2d.drawImage(itemImage, iconX, iconY, iconSize, iconSize, null);
			}
			iconX += iconSize + distance;
		}

		// Timer setup

		String timerText = model.getCurrentRun().getFormattedTime();

		g2d.setFont(FontManager.getFont(30f));
		g2d.setColor(Color.WHITE);
		FontMetrics metrics = g2d.getFontMetrics();

		int margin = 20;
		int timerX = GameConfig.SCREEN_WIDTH - metrics.stringWidth(timerText) - margin;
		int timerY = GameConfig.SCREEN_HEIGHT - metrics.getDescent() - margin;
		g2d.drawString(timerText, timerX, timerY);
	}

	/**
	 * Draws projectiles on the screen
	 * 
	 * @param g2d the graphics object
	 */
	private void drawProjectiles(Graphics2D g2d) {
		for (Projectile projectile : model.getProjectiles()) {
			Animation animation = animManager.getProjectileAnimation(projectile.getShooter(),
					projectile.getDirection());
			if (animation == null) {
				continue;
			}

			BufferedImage image = animation.getCurrentFrame(projectile.getAnimationTicks());

			if (image != null) {
				g2d.drawImage(image, projectile.getX(), projectile.getY(), GameConfig.TILE_SIZE, GameConfig.TILE_SIZE,
						null);
			}
		}
	}

}
