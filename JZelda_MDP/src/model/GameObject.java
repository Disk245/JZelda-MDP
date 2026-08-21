package model;

public abstract class GameObject extends Entity {
	
	public enum ItemType {
		COIN,				// 0
		CHEST_CLOSED,		// 1
		CHEST_OPEN,			// 2
		KEY,				// 3
		DOOR_CLOSED,		// 4
		DOOR_OPEN			// 5
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
	
	
	

