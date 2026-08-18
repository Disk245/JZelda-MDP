package view;

import java.util.HashMap;
import java.util.Map;

import model.Character.CharacterState;
import model.Character.Direction;

public class AnimationManager {
	
	//	public enum Direction {UP, DOWN, LEFT, RIGHT};
	//  public enum CharacterState {IDLE, WALKING, ATTACKING, HURT, DEAD}
	
	private Map<String, Animation> playerAnims = new HashMap<>();
	
	public AnimationManager() {
		playerAnims.put("IDLE_DOWN", new Animation("/resources/player/player_idle_down.png", 16, 16, 1));
		playerAnims.put("IDLE_LEFT", new Animation("/resources/player/player_idle_left.png", 16, 16, 1));
		playerAnims.put("IDLE_RIGHT", new Animation("/resources/player/player_idle_right.png", 16, 16, 1));
		playerAnims.put("IDLE_UP", new Animation("/resources/player/player_idle_up.png", 16, 16, 1));
		playerAnims.put("WALKING_DOWN", new Animation("/resources/player/player_walk_down.png", 16, 16, 10));
		playerAnims.put("WALKING_LEFT", new Animation("/resources/player/player_walk_left.png", 16, 16, 10));
		playerAnims.put("WALKING_RIGHT", new Animation("/resources/player/player_walk_right.png", 16, 16, 10));
		playerAnims.put("WALKING_UP", new Animation("/resources/player/player_walk_up.png", 16, 16, 10));
	}
	
	public Animation getPlayerAnimation(CharacterState state, Direction dir) {
        String key = state.name() + "_" + dir.name();
        return playerAnims.get(key);
    }
	

	
}
