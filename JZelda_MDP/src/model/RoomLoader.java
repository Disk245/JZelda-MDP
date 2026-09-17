package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.gameObjects.BootsObject;
import model.gameObjects.ChestObject;
import model.gameObjects.DoorObject;
import model.gameObjects.HeartContainerObject;
import model.gameObjects.KeyObject;
import model.gameObjects.ScrollObject;
import model.gameObjects.SignObject;

/**
 * Loads the rooms into the game. Each room has its own txt file describing its
 * layout and entities.
 */
public class RoomLoader {

	/**
	 * Finds a room's txt. It uses the room's position in the map grid to identify
	 * it
	 * 
	 * @param row    the room's row
	 * @param column the room's column
	 * @return a matching room
	 */
	public static Room getRoom(int row, int column) {
		String filePath = "/resources/rooms/room" + row + column + ".txt";
		return loadRoom(filePath);
	}

	/**
	 * This method parses the room, drawing the tiles and the entities. Firstly, it
	 * looks for a valid path. Secondly, checks if the text is describing the layout
	 * or the entities. In the first case, parses the 16x12 room, adding each tile
	 * to the layout list. In the second case, it positions the entity in the right
	 * coordinates, then creates the object based on the information in the txt
	 * file.
	 * 
	 * @param filePath
	 * @return
	 */
	private static Room loadRoom(String filePath) {
		List<int[]> layoutList = new ArrayList<>();
		List<Entity> entities = new ArrayList<>();

		try (InputStream is = RoomLoader.class.getResourceAsStream(filePath)) {
			if (is == null) {
				throw new IllegalStateException("Room file not found: " + filePath);
			}

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				String line;
				boolean readingEntities = false;

				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if (line.isEmpty() || line.startsWith("//"))
						continue;

					if (line.equals("[ENTITIES]")) {
						readingEntities = true;
						continue;
					} else if (line.equals("[LAYOUT]")) {
						readingEntities = false;
						continue;
					}

					if (!readingEntities) {
						// Grid parsing
						String[] values = line.split(",");
						int[] row = new int[values.length];
						for (int i = 0; i < values.length; i++) {
							row[i] = Integer.parseInt(values[i].trim());
						}
						layoutList.add(row);
					} else {
						// Entity parsing
						String[] parts = line.split(",");
						String type = parts[0].trim();

						String id = parts[1].trim();
						int posX = Integer.parseInt(parts[2].trim()) * GameConfig.TILE_SIZE;
						int posY = Integer.parseInt(parts[3].trim()) * GameConfig.TILE_SIZE;

						if (type.equalsIgnoreCase("DoorObject")) {
							entities.add(new DoorObject(id, posX, posY));
						} else if (type.equalsIgnoreCase("ChestObject")) {
							String lootId = parts[4].trim();
							GameObject loot = null;
							switch (lootId) {
							case "boots":
								loot = new BootsObject("boots", 0, 0);
							}
							entities.add(new ChestObject(id, posX, posY, loot));
						} else if (type.equalsIgnoreCase("SignObject")) {
							entities.add(new SignObject(id, posX, posY));
						} else if (type.equalsIgnoreCase("NPC")) {
							String name = parts[4].trim();
							if (parts.length > 5) {
								entities.add(new NPC(id, posX, posY, name, parseDialogue(parts)));
							} else {
								entities.add(new NPC(id, posX, posY, name));
							}
						} else if (type.equalsIgnoreCase("KeyObject")) {
							int price = Integer.parseInt(parts[4]);
							if (parts.length > 5) {
								entities.add(new KeyObject(id, posX, posY, price, parseDialogue(parts)));
							}
						} else if (type.equalsIgnoreCase("HeartContainerObject")) {
							int price = Integer.parseInt(parts[4]);
							if (parts.length > 5) {
								entities.add(new HeartContainerObject(id, posX, posY, price, parseDialogue(parts)));
							}
						} else if (type.equalsIgnoreCase("ScrollObject")) {
							int price = Integer.parseInt(parts[4]);
							if (parts.length > 5) {
								entities.add(new ScrollObject(id, posX, posY, price, parseDialogue(parts)));
							}
						} else if (type.equalsIgnoreCase("Slime")) {
							entities.add(new Slime(id, posX, posY, "Slime"));
						} else if (type.equalsIgnoreCase("EvilMage")) {
							entities.add(new EvilMage(id, posX, posY, "EvilMage"));
						}
					}
				}
			}
		} catch (Exception exception) {
			throw new IllegalStateException("Error loading room: " + filePath, exception);
		}

		int[][] layout = layoutList.toArray(new int[0][]);
		return new Room(layout, entities);
	}

	/**
	 * Parses the dialogue of an entity
	 * 
	 * @param parts the strings in the txt containing each line of dialogue
	 * @return the dialogue lines as an array of strings
	 */
	private static String[] parseDialogue(String[] parts) {
		String[] dialogue = new String[parts.length - 5];
		for (int i = 0; i < dialogue.length; i++) {
			dialogue[i] = parts[5 + i].trim();
		}
		return dialogue;
	}
}
