package model;

public abstract class GameObject extends Entity {
	
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
		SIGN							// 10
	}

    private int spriteId;
    protected ItemType itemType;
    
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
	
	
	

