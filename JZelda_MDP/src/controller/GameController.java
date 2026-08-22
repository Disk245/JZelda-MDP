package controller;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import model.Character.Direction;
import model.GameModel;
import model.GameModel.GameState;
import model.WorldMap;
import view.FontManager;
import view.GamePanel;
import view.GameScreenPanel;

public class GameController implements KeyListener, Runnable{
	private GameModel model;
	private GameScreenPanel view;
	private Thread gameThread;
	private int FPS = 60;
	
	public GameController(GameModel model, GameScreenPanel view){
		this.model = model;
		this.view = view;
		
	    view.setFocusable(true);
	    view.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		System.out.println("Input registered: " + e.getKeyChar());
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {model.startPlayerMovement(Direction.UP); }
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {model.startPlayerMovement(Direction.DOWN); }
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {model.startPlayerMovement(Direction.LEFT); }
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {model.startPlayerMovement(Direction.RIGHT); }
		
		if (code == KeyEvent.VK_E) { model.interact(); }
		if (code == KeyEvent.VK_R && model.getGameState() == GameState.DIALOGUE) { 
			model.BuyItem(model.getPlayer(), model.getCurrentShopItem());
		}
		
		if (code == KeyEvent.VK_SPACE) { model.handleAttack(); }
		
		
		
		// Cheat and debugging
		if(code == KeyEvent.VK_P) 
		{ 
			if (model.getPlayer().isCollisionOn()) 
				model.getPlayer().setCollisionOn(false); 
			else if (!model.getPlayer().isCollisionOn())
				model.getPlayer().setCollisionOn(true);
			System.out.println("Collision status: " + model.getPlayer().isCollisionOn());
		}
		
		if(code == KeyEvent.VK_O) 
		{ 
			if (model.getPlayer().getCharacterSpeed() == 4) 
				model.getPlayer().setCharacterSpeed(model.getPlayer().getCharacterSpeed() + 10); 
			else 
				model.getPlayer().setCharacterSpeed(model.getPlayer().getCharacterSpeed() - 10);
			System.out.println("Speed incremented: " + model.getPlayer().getCharacterSpeed());
		}
		
		if(code == KeyEvent.VK_L) {
			WorldMap worldMap = model.getWorldMap();
			if (worldMap.getKillCounter() < 10)
				worldMap.registerEnemyKill(10);
			else
				worldMap.resetKillCounter();
			System.out.println(worldMap.getKillCounter());
		}
		
		if(code == KeyEvent.VK_K) { 
			model.getPlayer().addCoins(200); 
			System.out.println("Money added. New money: " + model.getPlayer().getCoins());
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {model.stopPlayerMovement(Direction.UP); }
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {model.stopPlayerMovement(Direction.DOWN); }
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {model.stopPlayerMovement(Direction.LEFT); }
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {model.stopPlayerMovement(Direction.RIGHT); }
		
	}

	
	public void startGameThread() {
		if (gameThread != null) {
			return;
		}
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
			if (model.getGameState() == GameState.PLAY) {
				
				model.updateGame();
			}
			
			try {
				double remainingTime =
						nextDrawTime - System.nanoTime();
				
				remainingTime /= 1000000;
				
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
