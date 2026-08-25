package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Pickable;
import model.Player;
import model.GameObject.ItemType;

public class CoinObject extends GameObject implements Pickable{

	public CoinObject(String id, int x, int y) {
		super(id, x, y, 15, ItemType.COIN_DROP);
		this.setSolidArea(new Rectangle(3 * GameConfig.SCALE, 1 * GameConfig.SCALE, 12 * GameConfig.SCALE, 12 * GameConfig.SCALE));
		this.setCollisionOn(false);
	}

	@Override
	public void pickup(Player player) {
		player.setCoins(player.getCoins() + 5);
		System.out.println("MONEY ADDED");
		
	}

}
