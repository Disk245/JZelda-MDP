package model;

/**
 * Contains the stats of a single run.
 */
public class RunStats {

	private int killCount;
	private int killScore;

	private int heartScore;
	private int itemScore;
	private int timeBonus;
	private int totalScore;

	private long startTime;
	private long endTime;
	private boolean timerRunning;

	private int fullTimeBonus = 1000;

	/**
	 * Creates a new run with zeroed stats and a stopped timer.
	 */
	public RunStats() {
	}

	public void registerKill(int enemyPoints) {
		killCount++;
		killScore += enemyPoints;
	}

	/**
	 * Calculates the final score of the player at the end of the run. Each heart
	 * gives 150 points. Each item gives 100 points. A shorter time gives more
	 * points.
	 * 
	 * @param player the player character
	 */
	public void calculateFinalScore(Player player) {
		heartScore = player.getCurrentHealth() * 150;
		itemScore = player.getInventory().size() * 100;
		timeBonus = (int) calculateTimeBonus();

		totalScore = heartScore + itemScore + killScore + timeBonus;
	}

	/**
	 * Calculates the time bonus, scaling down by 10 points each 10 secnds. It does
	 * not grant any bonus for completion shorter than 1 minute, because it can't be
	 * obtained without cheating. Points start scaling down from the 2 minute mark.
	 * 
	 * @return the bonus points granted to the player
	 */
	private long calculateTimeBonus() {
		long elapsedSeconds = getElapsedSeconds();
		if (elapsedSeconds < 60)
			return 0;
		if (elapsedSeconds <= 120)
			return fullTimeBonus;
		long bonus = fullTimeBonus - ((elapsedSeconds - 120) / 10) * 10;
		return bonus >= 0 ? bonus : 0;
	}

	/**
	 * Starts the run timer
	 */
	public void startTimer() {
		startTime = System.nanoTime();
		endTime = 0;
		timerRunning = true;
	}

	/**
	 * Stops the run timer
	 */
	public void stopTimer() {
		if (timerRunning) {
			endTime = System.nanoTime();
			timerRunning = false;
		}
	}

	/**
	 * Calculates the amount of seconds passed since the game start
	 * 
	 * @return the elapsed seconds
	 */
	public long getElapsedSeconds() {
		long currentTime;

		if (timerRunning) {
			currentTime = System.nanoTime();
		} else {
			currentTime = endTime;
		}

		return (currentTime - startTime) / 1_000_000_000L;
	}

	/**
	 * Formats the time in a mm:ss format
	 * 
	 * @return the formatted time
	 */
	public String getFormattedTime() {
		long totalSeconds = getElapsedSeconds();

		long minutes = totalSeconds / 60;
		long seconds = totalSeconds % 60;

		return String.format("%02d:%02d", minutes, seconds);
	}

	public int getKillCount() {
		return killCount;
	}

	public int getKillScore() {
		return killScore;
	}

	public int getHeartScore() {
		return heartScore;
	}

	public int getItemScore() {
		return itemScore;
	}

	public int getTimeBonus() {
		return timeBonus;
	}

	public int getTotalScore() {
		return totalScore;
	}
}
