package model;

import java.util.List;

public class Enemy extends Character implements EnemyBehavior{

	private int attackRange;
	private EnemyBehavior behavior;
	
	
	public Enemy(String id, int x, int y, String name, int characterSpeed) {
		super(id, x, y, name, characterSpeed);
		
		
	}


	public EnemyBehavior getBehavior() {
		return behavior;
	}


	public void setBehavior(EnemyBehavior behavior) {
		this.behavior = behavior;
	}


	@Override
	public List<Direction> chooseDirections(Enemy enemy, Player player) {
		// TODO Auto-generated method stub
		return null;
	}

}
