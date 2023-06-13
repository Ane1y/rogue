package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.Position;

import java.util.Random;

public class MapBuilder {
    public static final int DEFAULT_WIDTH = 16;
    public static final int DEFAULT_HEIGHT = 12;
    public static final int DEFAULT_COMPLEXITY = 12;
    public static final int DEFAULT_EXIT_COUNT = 12;
    public static final int MIN_ROOM_WIDTH = 3;
    public static final int MIN_ROOM_HEIGHT = 3;
    public static final int MAX_ROOM_WIDTH = 100;
    public static final int MAX_ROOM_HEIGHT = 20;

    private String filename;
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private int complexity = DEFAULT_COMPLEXITY;
    private int exits = DEFAULT_EXIT_COUNT;
    private EntrySide entrySide = EntrySide.WEST;

    /**
     * Map will be loaded from disk and not generated, overrides all other options
     * @param filename name of the file that contains map
     */
    public MapBuilder loadFromDisk(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Changes width of generated map, by default, see: DEFAULT_WIDTH
     * @param width width of generated map
     */
    public MapBuilder width(int width) {
        assert width >= MIN_ROOM_WIDTH && width <= MAX_ROOM_WIDTH;
        this.width = width;
        return this;
    }


    /**
     * Changes height of generated map, by default, see: DEFAULT_HEIGHT
     * @param height width of generated map
     */
    public MapBuilder height(int height) {
        assert height >= MIN_ROOM_HEIGHT && height <= MAX_ROOM_HEIGHT;
        this.height = height;
        return this;
    }

    /**
     * Changes complexity of generated map, by default, see: DEFAULT_COMPLEXITY
     * @param c complexity of generated map, must be not negative
     */
    public MapBuilder complexity(int c) {
        assert c > 0;
        this.complexity = c;
        return this;
    }

    /**
     * Changes side on which entrance is placed, by default is WEST
     * @param side side of the entrance
     */
    public MapBuilder entrySide(EntrySide side) {
        this.entrySide = side;
        return this;
    }

    /**
     * Sets amount of exists from the room
     * @param count amount of doors, must be in a range [1; 3]
     */
    public MapBuilder exists(int count) {
        assert count <= 3 && count > 0;
        this.exits = count;
        return this;
    }

    /**
     * Options for entrance approximate position
     */
    public enum EntrySide {
        NORTH, SOUTH, WEST, EAST
    }

    /**
     * Generates map with set parameters
     * @return Map object
     */
    public Map build() {
        if (filename != null) {
            return buildFromDisk();
        }
        assert exits > 0;
        assert complexity >= 0;

        var map = new Map(width, height, this.getNumberOfEnemies());

        if (entrySide == EntrySide.EAST) { // Placeholder options // TODO: Replace with actual
            var eastPos = new Position(width + 1, height / 2);
            map.setTile(eastPos, MapView.Tile.DOOR_IN);
        } else {
            var westPos = new Position(0, height / 2);
            map.setTile(westPos, MapView.Tile.DOOR_IN);
        }

        map.setTile(generateDoor(map), MapView.Tile.DOOR_OUT_NORMAL);
        map.setTile(generateDoor(map), MapView.Tile.DOOR_OUT_HARD);

        return map;
    }

    /**
     * @return number of enemies for set complexity
     */
    public int getNumberOfEnemies() {
        return numberOfEnemies(complexity);
    }

    private static int numberOfEnemies(int complexity) {
        if (complexity == 0) {
            return 0;
        }

        return 2 + (int) Math.ceil(Math.log(complexity));
    }

    private Map buildFromDisk() {
        return new Map(width, height, numberOfEnemies(complexity)); // TODO: Replace with actual loading
    }

    // generates door and checks if the player can reach ot from entrance
    private Position generateDoor(Map map) {
        Position doorPos = getRandomPosition();
        while(map.getDistance(doorPos, map.getEntrance()) == -1) {
            doorPos = getRandomPosition();
        }
        return doorPos;
    }
    private Position getRandomPosition() {
        Random rand = new Random();
        return new Position(rand.nextInt(width), rand.nextInt(height));
    }
}