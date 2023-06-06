package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Movement;
import ru.itmo.rogue.model.game.unit.Position;

import java.util.*;

public class Map {

    public enum MapTile {
        FLOOR,
        WALL,
        DOOR_IN,
        DOOR_OUT_NORMAL,
        DOOR_OUT_HARD,
        DOOR_OUT_TREASURE_ROOM
    }



    private final int numberOfEnemies;
    private final MapTile[][] map;
    private Position entrance = new Position();

    public Map(int width, int height, int numberOfEnemies) {
        this.map = new Map.MapTile[width + 2][height + 2];
        this.numberOfEnemies = numberOfEnemies;
        for (int i = 0; i < getHeight(); i++) {
            Arrays.fill(map[i], MapTile.FLOOR);
        }
        Arrays.fill(map[0], MapTile.WALL);
        Arrays.fill(map[width + 1], MapTile.WALL);

        for (int i = 0; i < height + 2; i++) {
            map[i][0] = MapTile.WALL;
            map[i][height + 1] = MapTile.WALL;
        }

    }

    public MapTile getTile(int x, int y) {
        assert x >= 0 && x < getWidth();
        assert y >= 0 && y < getHeight();
        return map[x][y];
    }
    public MapTile getTile(Position pos) {
        return getTile(pos.getX(), pos.getY());
    }

    public void setTile(int x, int y, MapTile tile) {
        assert x >= 0 && x < getWidth();
        assert y >= 0 && y < getHeight();
        map[x][y] = tile;
        if (tile == MapTile.DOOR_IN) {
            entrance = new Position(x, y);
        }
    }
    public void setTile(Position pos, MapTile tile) {
        setTile(pos.getX(), pos.getY(), tile);
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
    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }
    public boolean isFree(Position pos) {
        return getTile(pos.getX(), pos.getY()) == MapTile.FLOOR;
    }

    public boolean isExit(Position pos) {
        var curTileType = getTile(pos);
        return curTileType == MapTile.DOOR_OUT_HARD ||
                curTileType == MapTile.DOOR_OUT_NORMAL ||
                curTileType == MapTile.DOOR_OUT_TREASURE_ROOM;
    }
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



    private record QueueNode(Position pos, int dist) {}

    private boolean isTileExists(Position pos) {
        return ((pos.getY() >= 0) && (pos.getY() < getHeight())
                && (pos.getX() >= 0) && (pos.getX() < getWidth()));
    }

    // returns -1 if path was not found, distance otherwise
    public int getDistance(Position from, Position to) {
        // These arrays show the 4 possible movement from a cell
        boolean[][] visited = new boolean[getWidth()][getHeight()];

        visited[from.getX()][from.getY()] = true;
        Deque<QueueNode> q = new ArrayDeque<>();

        QueueNode s = new QueueNode(from, 0);
        q.add(s);

        while (!q.isEmpty()) {
            QueueNode u = q.poll();
            Position point = u.pos;
            if (point.equals(to)) {
                return u.dist;
            }
            for (var delta : Movement.defaults) {
                Position dp = point.shift(delta);
                int dy = dp.getY();
                int dx = dp.getX();
                if (isTileExists(dp) && isFree(dp) && !visited[dx][dy]) {
                    visited[dx][dy] = true;
                    q.add(new QueueNode(dp,u.dist + 1));
                }
            }
        }

        return -1;
    }
}
