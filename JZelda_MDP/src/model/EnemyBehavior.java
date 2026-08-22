package model;

import java.util.List;

import model.Character.Direction;
import model.Enemy;
import model.Player;

public interface EnemyBehavior {

    /*
     * Returns the direction the enemy will try to move,
     * sorted by the most efficient.
     */
    List<Direction> chooseDirections(Enemy enemy, Player player);
}
