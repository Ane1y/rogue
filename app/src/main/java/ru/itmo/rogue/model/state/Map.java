package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Position;

import java.util.Arrays;
import java.util.Objects;

public class Map {

    public enum MapTile {
        FLOOR, WALL, DOOR_IN, DOOR_OUT
    }

    public Map(int width, int height) {
        this.map = new Map.MapTile[width + 2][height + 2];

        Arrays.fill(map[0], MapTile.WALL);
        Arrays.fill(map[height + 1], MapTile.WALL);

        for (int i = 0; i < height + 2; i++) {
            map[i][0] = MapTile.WALL;
            map[i][width + 1] = MapTile.WALL;
        }
    }

    public MapTile getTile(int x, int y) {
        assert x >= 0 && x < getWidth();
        assert y >= 0 && y < getHeight();
        return map[x][y];
    }

    public void setTile(int x, int y, MapTile tile) {
        assert x >= 0 && x < getWidth();
        assert y >= 0 && y < getHeight();
        map[x][y] = tile;
        if (tile == MapTile.DOOR_IN) {
            entrance = new Position(x, y);
        }
    }

    public Position getEntrance() {
        return entrance;
    }

    public int getHeight() {
        return map[0].length;
    }

    public int getWidth() {
        return map.length;
    }

    private final MapTile[][] map;

    private Position entrance = new Position();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map that)) return false;
        return Arrays.deepEquals(map, that.map) && Objects.equals(entrance, that.entrance);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(entrance);
        result = Objects.hash(result, Arrays.deepHashCode(map));
        return result;
    }
}
