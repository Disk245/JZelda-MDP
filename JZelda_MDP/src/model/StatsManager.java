package model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import view.ImageButton;

public class StatsManager {

	private final Path STATS_PATH = Path.of("src", "data", "global_stats.txt");
	private Map<String, Integer> statsMap;

	public StatsManager() {
		if (Files.exists(STATS_PATH)) {
			statsMap = retrieveFromFile();
		} else {
			statsMap = new HashMap<>();
		}
	}

	public Map<String, Integer> retrieveFromFile() {
		Map<String, Integer> loadedStats = new HashMap<>();

		try {
			List<String> lines = Files.readAllLines(STATS_PATH);
			for (String line : lines) {
				String[] parts = line.split("=");
				String key = parts[0].trim();
				int value = Integer.parseInt(parts[1].trim());

				loadedStats.put(key, value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadedStats;

	}

	public void writeToFile() {
		try (BufferedWriter statsWriter = Files.newBufferedWriter(STATS_PATH)) {
			for (var entry : statsMap.entrySet()) {
				statsWriter.write(entry.getKey() + "=" + entry.getValue());
				statsWriter.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void registerRun(RunStats runStats) {
		statsMap.put("totalKills", statsMap.getOrDefault("totalKills", 0) + runStats.getKillCount());

		if (statsMap.getOrDefault("highScore", 0) < runStats.getTotalScore()) {
			statsMap.put("highScore", runStats.getTotalScore());
		}

	}

	public void registerDeath() {
		statsMap.put("totalDeaths", statsMap.getOrDefault("totalDeaths", 0) + 1);
	}

	public void registerVictory(RunStats runStats) {
	    statsMap.merge("totalWins", 1, Integer::sum);

	    int elapsedSeconds = (int) runStats.getElapsedSeconds();
	    int fastestRun = statsMap.getOrDefault("fastestRun", 0);

	    if (fastestRun == 0 || elapsedSeconds < fastestRun) {
	        statsMap.put("fastestRun", elapsedSeconds);
	    }
	}

	public int getValue(String key) {
		return this.statsMap.getOrDefault(key, 0);
	}
	
	public void resetStats() {
		for (String key : statsMap.keySet()) {
			statsMap.put(key, 0);
		}
	}

}
