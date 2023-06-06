package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.state.Map;

import java.util.Random;

public class LevelBuilder {
    // TODO: Implement

    private String filename;
    private int width = 16;
    private int height = 12;
    private int exits = 1;
    private int complexity = 1;
    private EntrySide entrySide = EntrySide.WEST;
    private int numberOfEnemies = 0;
    public LevelBuilder loadFromDisk(String filename) {
        this.filename = filename;
        return this;
    }

    public LevelBuilder width(int width) {
        this.width = width;
        return this;
    }

    public LevelBuilder height(int height) {
        this.height = height;
        return this;
    }

    public LevelBuilder complexity(int c) {
        this.complexity = c;
        return this;
    }

    public LevelBuilder entrySide(EntrySide side) {
        this.entrySide = side;
        return this;
    }

    /**
     * Sets amount of exists from the room
     * @param count amount of doors, must be in a range [1; 3]
     */
    public LevelBuilder exists(int count) {
        assert count <= 3 && count > 0;
        this.exits = count;
        return this;
    }

    public enum EntrySide {
        NORTH, SOUTH, WEST, EAST
    }

    // TODO: Create a proper Map type maybe

    public Map build() {
        if (filename != null) {
            return buildFromDisk();
        }
        assert exits != 0;
        assert complexity >= 0;

        var map = new Map(width, height);

        if (entrySide == EntrySide.EAST) { // Placeholder options // TODO: Replace with actual
            map.setTile(width + 1, height / 2, Map.MapTile.DOOR_IN);
        } else {
            map.setTile(0, height / 2, Map.MapTile.DOOR_IN);
        }

        map.setTile(generateDoor(map), Map.MapTile.DOOR_OUT_NORMAL);
        map.setTile(generateDoor(map), Map.MapTile.DOOR_OUT_HARD);

        numberOfEnemies = (int)Math.log(complexity);

        return map;
    }
    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }

    private Map buildFromDisk() {
        return new Map(width, height); // TODO: Replace with actual loading
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
        return new Position(rand.nextInt(height), rand.nextInt(width));
    }
}
