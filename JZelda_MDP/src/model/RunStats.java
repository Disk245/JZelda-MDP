package model;

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

	public RunStats() {
		reset();
	}

	public void reset() {
		killCount = 0;
		killScore = 0;
		heartScore = 0;
		itemScore = 0;
		timeBonus = 0;
		totalScore = 0;
		startTime = System.currentTimeMillis();
	}

	public void registerKill(int enemyPoints) {
		killCount++;
		killScore += enemyPoints;
	}

	/**
	 * Calculates the final score of the player at the end of the run.
	 * 
	 * @param player the player character
	 */
	public void calculateFinalScore(Player player) {
		heartScore = player.getCurrentHealth() * 150;
		itemScore = player.getInventory().size() * 100;
		timeBonus = (int)calculateTimeBonus();

		totalScore = heartScore + itemScore + killScore + timeBonus;
	}

	private long calculateTimeBonus() {
		long elapsedSecond = getElapsedSeconds();
		if (elapsedSecond / 60 < 2) return 0;
		long bonus = fullTimeBonus - (elapsedSecond / 60) * 10;
		return bonus >= 0 ? bonus : 0;
	}

	public void startTimer() {
		startTime = System.nanoTime();
		endTime = 0;
		timerRunning = true;
	}

	public void stopTimer() {
		if (timerRunning) {
			endTime = System.nanoTime();
			timerRunning = false;
		}
	}

	public long getElapsedSeconds() {
		long currentTime;

		if (timerRunning) {
			currentTime = System.nanoTime();
		} else {
			currentTime = endTime;
		}

		return (currentTime - startTime) / 1_000_000_000L;
	}
	
	public String getFormattedTime() {
	    long totalSeconds = getElapsedSeconds();

	    long minutes = totalSeconds / 60;
	    long seconds = totalSeconds % 60;

	    return String.format(minutes + ":" + seconds);
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