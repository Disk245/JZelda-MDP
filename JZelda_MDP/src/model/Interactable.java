package model;

/**
 * An interface granting the property of being able to
 * interact with the class that implements it.
 */
public interface Interactable {
	
	/**
	 * Allows interaction with the player
	 * 
	 * @param player the player
	 * @return an array of strings that represent the dialogue lines
	 */
	String[] interact(Player player);
}
