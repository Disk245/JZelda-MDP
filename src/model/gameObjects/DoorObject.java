package model.gameObjects;

import java.awt.Rectangle;

import model.GameConfig;
import model.GameObject;
import model.Interactable;
import model.Player;
import model.GameObject.ItemType;

/**
 * A door that can be opened either by interacting or if a condition is
 * satisfied. Implements the Interactable interface to allow player interaction,
 */
public class DoorObject extends GameObject implements Interactable {

	private boolean conditionSatisfied;

	/**
	 * Generates a door, setting the coordinates to place it correctly. It also sets
	 * its area as a full tile and its collisions as true, blocking player passage.
	 * 
	 * @param id the id needed to identify the entity
	 * @param x  position on the x axis
	 * @param y  position on the y axis
	 */
	public DoorObject(String id, int x, int y) {
		super(id, x, y, 9, ItemType.DOOR_CLOSED);

		this.setSolidArea(new Rectangle(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
		this.conditionSatisfied = true;
	}

	/**
	 * Opens the door, changing its sprite id to the one representing the open
	 * version and disabling collisions.
	 */
	public void openDoor() {
		this.itemType = ItemType.DOOR_OPEN;
		this.setCollisionOn(false);
		this.setSpriteId(8);
	}

	/**
	 * Opens a locked door if the opening ocndition is satisfied.
	 */
	@Override
	public String[] interact(Player player) {
		if (conditionSatisfied)
			openDoor();
		return null;
	}

	public boolean isConditionSatisfied() {
		return conditionSatisfied;
	}

	public void setConditionSatisfied(boolean conditionSatisfied) {
		this.conditionSatisfied = conditionSatisfied;
	}

}
