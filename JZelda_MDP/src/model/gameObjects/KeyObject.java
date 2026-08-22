package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.Purchasable;

public class KeyObject extends GameObject implements Interactable,Purchasable{
	
	int price;
	String[] dialogue = {
		"Oh, the key?",
		"It costs " + price + " coins"
	};
	
	public KeyObject(String id, int x, int y, int price, String[] dialogue) {
		super(id, x, y, 3, ItemType.KEY);
		this.setSolidArea(new Rectangle(3 * GameConfig.SCALE, 1 * GameConfig.SCALE, 4 * GameConfig.SCALE, 16 * GameConfig.SCALE));
		this.setCollisionOn(true);
		this.price = price;
		this.dialogue = dialogue;
	}

	@Override
	public String[] interact() {
		return dialogue;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String[] getDialogue() {
		return dialogue;
	}

	public void setDialogue(String[] dialogue) {
		this.dialogue = dialogue;
	}

	@Override
	public void ApplyEffect(Player player) {
		return;
	}
	
	
}
