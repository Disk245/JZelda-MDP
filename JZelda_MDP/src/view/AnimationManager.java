package view;

import java.util.HashMap;
import java.util.Map;

import model.Character;
import model.Character.CharacterState;
import model.Character.Direction;
import model.Enemy;
import model.Player;
import model.Projectile;

public class AnimationManager {
	
	//	public enum Direction {UP, DOWN, LEFT, RIGHT};
	//  public enum CharacterState {IDLE, WALKING, ATTACKING, HURT, DEAD}
	
	private Map<String, Animation> playerAnims = new HashMap<>();
	private Map<String, Animation> shopkeeperAnims = new HashMap<>();
	private Map<String, Animation> projectileAnims = new HashMap<>();
	private Map<String, Animation> enemySlimeAnims = new HashMap<>();
	
	public AnimationManager() {
		playerAnims.put("IDLE_DOWN", new Animation("/resources/player/player_idle_down.png", 16, 16, 1));
		playerAnims.put("IDLE_LEFT", new Animation("/resources/player/player_idle_left.png", 16, 16, 1));
		playerAnims.put("IDLE_RIGHT", new Animation("/resources/player/player_idle_right.png", 16, 16, 1));
		playerAnims.put("IDLE_UP", new Animation("/resources/player/player_idle_up.png", 16, 16, 1));
		playerAnims.put("WALKING_DOWN", new Animation("/resources/player/player_walk_down.png", 16, 16, 10));
		playerAnims.put("WALKING_LEFT", new Animation("/resources/player/player_walk_left.png", 16, 16, 10));
		playerAnims.put("WALKING_RIGHT", new Animation("/resources/player/player_walk_right.png", 16, 16, 10));
		playerAnims.put("WALKING_UP", new Animation("/resources/player/player_walk_up.png", 16, 16, 10));
		
		playerAnims.put("ATTACKING_DOWN", new Animation("/resources/player/player_attack_down.png", 16, 32, 4));
		playerAnims.put("ATTACKING_LEFT", new Animation("/resources/player/player_attack_left.png", 32, 16, 4));
		playerAnims.put("ATTACKING_RIGHT", new Animation("/resources/player/player_attack_right.png", 32, 16, 4));
		playerAnims.put("ATTACKING_UP", new Animation("/resources/player/player_attack_up.png", 16, 32, 4));
		
		playerAnims.put("DEAD_DOWN", new Animation("/resources/player/player_death_down.png", 16, 32, 8));
		playerAnims.put("DEAD_LEFT", new Animation("/resources/player/player_death_left.png", 32, 16, 8));
		playerAnims.put("DEAD_RIGHT", new Animation("/resources/player/player_death_right.png", 32, 16, 8));
		playerAnims.put("DEAD_UP", new Animation("/resources/player/player_death_up.png", 16, 32, 8));
		
		shopkeeperAnims.put("IDLE_DOWN", new Animation("/resources/entities/shopkeeper_idle_down.png", 16, 16, 1));
		shopkeeperAnims.put("IDLE_LEFT", new Animation("/resources/entities/shopkeeper_idle_left.png", 16, 16, 1));
		shopkeeperAnims.put("IDLE_RIGHT", new Animation("/resources/entities/shopkeeper_idle_right.png", 16, 16, 1));
		shopkeeperAnims.put("IDLE_UP", new Animation("/resources/entities/shopkeeper_idle_up.png", 16, 16, 1));
		
		projectileAnims.put("PLAYER_UP", new Animation("/resources/entities/projectiles/player_projectile_up.png", 16, 16, 1));
		projectileAnims.put("PLAYER_LEFT", new Animation("/resources/entities/projectiles/player_projectile_left.png", 16, 16, 1));
		projectileAnims.put("PLAYER_RIGHT", new Animation("/resources/entities/projectiles/player_projectile_right.png", 16, 16, 1));
		projectileAnims.put("PLAYER_DOWN", new Animation("/resources/entities/projectiles/player_projectile_down.png", 16, 16, 1));
	}
	
	public Animation getPlayerAnimation(CharacterState state, Direction dir) {
        String key = state.name() + "_" + dir.name();
        return playerAnims.get(key);
    }

	public Animation getShopkeeperAnimation(CharacterState state, Direction dir) {
        String key = state.name() + "_" + dir.name();
        return shopkeeperAnims.get(key);
	}
	
	public Animation getProjectileAnimation(Character shooter, Direction direction) {
		
	    String type;

	    if (shooter instanceof Player) 
	        type = "PLAYER";
	    else if (shooter instanceof Enemy) 
	        type = "ENEMY";
	    else 
	        return null;
	    

	    String key = type + "_" + direction.name();
	    return projectileAnims.get(key);
	}
	

	
}
