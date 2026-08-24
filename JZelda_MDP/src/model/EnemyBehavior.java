package model;

import java.util.List;

import model.Character.Direction;

public interface EnemyBehavior {

	void updateBehavior(Enemy enemy, Player player, GameModel model);

}
