package model;

import java.awt.Rectangle;

public class Player extends Character{

	
	
	public Player(String id, int x, int y, String name, int characterSpeed) {
		super(id, x, y, name, characterSpeed);
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 10 * GameConfig.SCALE, 12 * GameConfig.SCALE, 6 * GameConfig.SCALE));
	}
	
	public void interact(Entity entity) {
		if (entity instanceof Interactable i) {
			i.interact();
		}
	}
}
