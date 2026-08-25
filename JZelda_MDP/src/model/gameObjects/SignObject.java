package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.GameObject.ItemType;

public class SignObject extends GameObject implements Interactable{
	
	private  String[] dialogue = {
			"You can use your coins here",
			"to buy items (with R).",
			"You might not be able",
			"to buy everything.",
			"Choose wisely!"
	};

	public SignObject(String id, int x, int y) {
		super(id, x, y, 10, ItemType.SIGN);
		
		this.setSolidArea(new Rectangle(0, GameConfig.TILE_SIZE / 2, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE / 2));
	}

	@Override
	public String[] interact(Player player) {
		return dialogue;
	}
	
	public String[] getDialogue() { return dialogue; }

	public void setDialogue(String[] dialogue) {
		this.dialogue = dialogue;
	}
	
}
