package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.Purchasable;

public class HeartContainerObject extends GameObject implements Interactable,Purchasable{
	
	private int price;
	private String[] dialogue = {
			"Good choice.",
			"It will improve your health",
			"by 2 points. It costs " + price + " coins"
	};

	public HeartContainerObject(String id, int x, int y, int price, String[] dialogue) {
		super(id, x, y, 12, ItemType.HEART_CONTAINER);
		this.price = price;
		this.dialogue = dialogue;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE,0,14 * GameConfig.SCALE ,GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
	}

	@Override
	public String[] interact(Player player) {
		return dialogue;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public void ApplyEffect(Player player) {
		System.out.println(player.getMaxHealth());
		player.setMaxHealth(player.getMaxHealth() + 2);
		player.setCurrentHealth(player.getCurrentHealth() + 2);
		System.out.println(player.getMaxHealth());
		
	}
	
	
}
