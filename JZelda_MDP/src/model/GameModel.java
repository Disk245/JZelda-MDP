package model;

import java.util.Observable;


@SuppressWarnings("deprecation")
public class GameModel extends Observable implements Runnable{
	
	public enum GameState {MENU,NICKNAME,CREDITS,OPTIONS,PLAY,PAUSE,DEFEAT}
	
	GameState gameState = GameState.MENU;
	
	
	Thread gameThread;
	
	
	
	
	
	
	
	public GameState getGameState() { return gameState; }
	public void setGameState(GameState state) {
		this.gameState = state;
		setChanged();
		notifyObservers(state);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}


	@Override
	public void run() {
		
		while(gameThread != null) {
			System.out.println("Game loop running");
		}
		
	}
}
