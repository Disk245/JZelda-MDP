package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character{

	private int coins;
	private List<GameObject> inventory;
	
	public Player(String id, int x, int y, String name, int characterSpeed) {
		super(id, x, y, name, characterSpeed);
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 10 * GameConfig.SCALE, 12 * GameConfig.SCALE, 6 * GameConfig.SCALE));
		this.inventory = new ArrayList<>();
		this.coins = 0;
	}
	
	public String[] interact(Entity entity) {
		if (entity instanceof Interactable i) {
			return i.interact();
		}
		return null;
	}
}
