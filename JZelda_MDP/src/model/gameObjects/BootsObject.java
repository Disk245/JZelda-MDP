package model.gameObjects;

import model.GameObject;
import model.Player;
import model.Purchasable;

public class BootsObject extends GameObject implements Purchasable{
	
	private int price;
	private int speedBonus;

	public BootsObject(String id, int x, int y) {
		super(id, x, y, 11, ItemType.BOOTS);
		this.speedBonus = 2;
	}


	public int getPrice() {
		return 0;
	}

	@Override
	public void ApplyEffect(Player player) {
		player.setCharacterSpeed(player.getCharacterSpeed() + speedBonus);
		
	}
	
	

}
