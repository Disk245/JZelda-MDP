package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RoomLoader {

	public static Room getRoom(int row, int column) {
	    String filePath = "/resources/rooms/room" + row + column + ".txt";
	    return loadRoom(filePath);
	}

    // Private method to parse rooms
    private static Room loadRoom(String filePath) {
        List<int[]> layoutList = new ArrayList<>();
        List<Entity> entities = new ArrayList<>();

        try (InputStream is = RoomLoader.class.getResourceAsStream(filePath)) {
            if (is == null) {
                throw new IllegalStateException(
                        "Room file not found: "
                        + filePath
                    );
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                boolean readingEntities = false;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("//")) continue;

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
                        
                        if (type.equalsIgnoreCase("DoorObject")) {
                            String id = parts[1].trim();
                            int tileX = Integer.parseInt(parts[2].trim());
                            int tileY = Integer.parseInt(parts[3].trim());
                            
                            int posX = tileX * GameConfig.TILE_SIZE;
                            int posY = tileY * GameConfig.TILE_SIZE;
                            
                            entities.add(new DoorObject(id, posX, posY));
                        }
                        
                        else if (type.equalsIgnoreCase("ChestObject")) {
                        	String id = parts[1].trim();
                            int tileX = Integer.parseInt(parts[2].trim());
                            int tileY = Integer.parseInt(parts[3].trim());
                            
                            int posX = tileX * GameConfig.TILE_SIZE;
                            int posY = tileY * GameConfig.TILE_SIZE;

                            entities.add(new ChestObject(id,posX,posY,null));
                        }
                        
                        else if (type.equalsIgnoreCase("SignObject")) {
							String id = parts[1].trim();
							int tileX = Integer.parseInt(parts[2].trim());
							int tileY = Integer.parseInt(parts[3].trim());
							
							int posX = tileX * GameConfig.TILE_SIZE;
							int posY = tileY * GameConfig.TILE_SIZE;	
							
							entities.add(new SignObject(id,posX,posY));
                        }
                    }
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Error loading room: "
                            + filePath,
                    exception
            );
        }

        int[][] layout = layoutList.toArray(new int[0][]);
        return new Room(layout, entities);
    }
}