package model;

/**
 * Allows the player to purchase an object whose class implements this interface
 */
public interface Purchasable {
	int getPrice();
	void ApplyEffect(Player player);
}
