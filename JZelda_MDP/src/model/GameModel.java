package model;

import java.util.Observable;


@SuppressWarnings("deprecation")
public class GameModel extends Observable{
	
	public enum GameState {MENU,NICKNAME,CREDITS,OPTIONS,PLAY,PAUSE,DEFEAT}
	
	GameState gameState = GameState.MENU;
	
	
	
	
	
	
	
	
	
	
	
	public void setGameState(GameState state) {
		this.gameState = state;
		setChanged();
		notifyObservers(state);
	}
}
