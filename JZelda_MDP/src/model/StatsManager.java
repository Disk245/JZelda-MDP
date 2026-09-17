package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the game stats, saved in a txt file, and updates them.
 */
public class StatsManager {

	private final Path STATS_PATH = Path.of("src", "data", "global_stats.txt");
	private Map<String, Integer> statsMap;
	private String highScoreNickname = "";
	private String fastestRunNickname = "";

	/**
	 * When intialising the StatsManager, if a stats file is already present, it
	 * loads its contents. If there isn't one, it gets created with default values.
	 */
	public StatsManager() {
		if (Files.exists(STATS_PATH)) {
			statsMap = retrieveFromFile();
		} else {
			statsMap = new HashMap<>();
		}
	}

	/**
	 * Retrieves stats data from a file. It extracts the high score holder, the
	 * fastest completionist, high score, total deaths, kills and victories.
	 * 
	 * @return a map containing each stat and its value
	 */
	public Map<String, Integer> retrieveFromFile() {
		Map<String, Integer> loadedStats = new HashMap<>();

		try {
			List<String> lines = Files.readAllLines(STATS_PATH);
			for (String line : lines) {
				String[] parts = line.split("=", 2);
				String key = parts[0].trim();
				if (key.equals("highScoreNickname")) {
					highScoreNickname = parts[1];
					continue;
				}
				if (key.equals("fastestRunNickname")) {
					fastestRunNickname = parts[1];
					continue;
				}
				int value = Integer.parseInt(parts[1].trim());

				loadedStats.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadedStats;

	}

	/**
	 * Writes the updated stats to file
	 */
	public void writeToFile() {
		try {
			Files.createDirectories(STATS_PATH.getParent());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try (BufferedWriter statsWriter = Files.newBufferedWriter(STATS_PATH)) {
			for (var entry : statsMap.entrySet()) {
				statsWriter.write(entry.getKey() + "=" + entry.getValue());
				statsWriter.newLine();
			}
			statsWriter.write("highScoreNickname=" + highScoreNickname);
			statsWriter.newLine();
			statsWriter.write("fastestRunNickname=" + fastestRunNickname);
			statsWriter.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Registers a run, updating the new stats in the map
	 * 
	 * @param runStats the object containing all the current run's stats
	 * @param nickname the nickname of the player
	 */
	public void registerRun(RunStats runStats, String nickname) {
		statsMap.put("totalKills", statsMap.getOrDefault("totalKills", 0) + runStats.getKillCount());

		int highScore = getValue("highScore");
		if ((highScore == 0 && highScoreNickname.isEmpty()) || runStats.getTotalScore() > highScore) {
			statsMap.put("highScore", runStats.getTotalScore());
			highScoreNickname = nickname;
		}

	}

	/**
	 * Updates the death count
	 */
	public void registerDeath() {
		statsMap.put("totalDeaths", statsMap.getOrDefault("totalDeaths", 0) + 1);
	}

	/**
	 * Updates the win count and the completion time, if lower than the previous
	 * 
	 * @param runStats the holder of this run's stats
	 * @param nickname the player's nickname
	 */
	public void registerVictory(RunStats runStats, String nickname) {
		statsMap.merge("totalWins", 1, Integer::sum);

		int elapsedSeconds = (int) runStats.getElapsedSeconds();
		int fastestRun = statsMap.getOrDefault("fastestRun", 0);

		if ((fastestRun == 0 && fastestRunNickname.isEmpty()) || elapsedSeconds < fastestRun) {
			statsMap.put("fastestRun", elapsedSeconds);
			fastestRunNickname = nickname;
		}
	}

	public int getValue(String key) {
		return this.statsMap.getOrDefault(key, 0);
	}

	public String getHighScoreNickname() {
		return highScoreNickname;
	}

	public String getFastestRunNickname() {
		return fastestRunNickname;
	}

	/**
	 * Resets the stats to their defautl values
	 */
	public void resetStats() {
		highScoreNickname = "";
		fastestRunNickname = "";
		for (String key : statsMap.keySet()) {
			statsMap.put(key, 0);
		}
	}

}
