package model;

/**
 * Allows the player to purchase an object whose class implements this interface
 */
public interface Purchasable {
	int getPrice();

	/**
	 * Applies the item's effect after being purchased.
	 * 
	 * @param player the player
	 */
	void applyEffect(Player player);
}
