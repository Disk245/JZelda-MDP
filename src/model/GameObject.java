package model;

/**
 * The class describing a game object, such as a chest.
 * Contains the basic information required to all game objects
 */
public abstract class GameObject extends Entity {
	
	/**
	 * This enum keeps track of every type of object in the game,
	 * granting better readability.
	 */
	public enum ItemType {
		COIN,							// 0
		CHEST_CLOSED,					// 1
		CHEST_OPEN,						// 2
		KEY,							// 3
		DOOR_CLOSED,					// 4
		DOOR_OPEN,						// 5
		DUNGEON_DOOR_SOUTH_CLOSED,		// 6
		DUNGEON_DOOR_SOUTH_OPEN,		// 7
		DUNGEON_DOOR_NORTH_CLOSED,		// 8
		DUNGEON_DOOR_NORTH_OPEN	,		// 9
		SIGN,							// 10
		BOOTS,							// 11
		HEART_CONTAINER,				// 12
		SCROLL,							// 13
		HEART_DROP,						// 14
		COIN_DROP,						// 15
	}

    private int spriteId;
    protected ItemType itemType;
    
    /**
     * Creates a game object. It uses the superclass's constructor to place it
     * and identify it.
     * 
	 * @param id an id to identify the character
	 * @param x the position on the x axis
	 * @param y the position on the y axis
     * @param spriteId the id used to communicate to the view what to draw
     * @param itemType the item type, for better readability.
     */
    public GameObject(String id, int x, int y, int spriteId, ItemType itemType) {
        super(id, x, y);
        this.spriteId = spriteId;
        this.itemType = itemType;
    }

    public int getSpriteId() {
        return spriteId;
    }

    protected void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }
    
    public ItemType getItemType() { return itemType; }
}
	
	
	

