package model;

import java.awt.Rectangle;

/**
 * A class describing an NPC. It contains its dialogue lines and implements the
 * Interactable interface to allow player interaction.
 */
public class NPC extends Character implements Interactable {

	private String[] dialogueLines;

	/**
	 * 
	 * @param id            an id to identify the character
	 * @param x             the position on the x axis
	 * @param y             the position on the y axis
	 * @param name          the character's name (e.g. "slime")
	 * @param dialogueLines the npc's dialogue lines. Each element of the array
	 *                      represents a line.
	 */
	public NPC(String id, int x, int y, String name, String[] dialogueLines) {
		super(id, x, y, name, 4);
		this.dialogueLines = dialogueLines;
		this.setSolidArea(new Rectangle(1 * GameConfig.SCALE, 0, 12 * GameConfig.SCALE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
	}

	public NPC(String id, int x, int y, String name) {
		super(id, x, y, name, 4);
		String[] defaultDialogue = { "Default dialogue", "Default dialogue" };
		this.dialogueLines = defaultDialogue;
		this.setSolidArea(new Rectangle(0, 0, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE));
		this.setCollisionOn(true);
	}

	@Override
	public String[] interact(Player player) {
		return dialogueLines;
	}

	public String[] getDialogue() {
		return dialogueLines;
	}

	public void setDialogue(String[] dialogue) {
		this.dialogueLines = dialogue;
	}

	@Override
	public void attack() {
		return;

	}

}
