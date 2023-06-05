package ru.itmo.rogue.model.state;

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

    private final MapTile[][] map;
    private final int width;
    private final int height;
    private Position entrance = new Position();

    public Map(int w, int h) {

        this.height = h + 2;
        this.width = w + 2;
        this.map = new Map.MapTile[height][width];

        for (int i = 0; i < width; i++) {
            Arrays.fill(map[i], MapTile.FLOOR);
        }
        Arrays.fill(map[0], MapTile.WALL);
        Arrays.fill(map[h + 1], MapTile.WALL);

        for (int i = 0; i < h + 2; i++) {
            map[i][0] = MapTile.WALL;
            map[i][w + 1] = MapTile.WALL;
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

    public Position getEntrance() {
        return entrance;
    }

    public int getHeight() {
        return map[0].length;
    }

    public int getWidth() {
        return map.length;
    }

    public boolean isFree(Position pos) {
        return getTile(pos.getX(), pos.getY()) == MapTile.FLOOR;
    }

    public boolean isDoorOut(Position pos) {
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



    private record queueNode(Position pos, int dist) {}

    private boolean isCellValid(int row, int col) {
        return ((row >= 0) && (row < height) && (col >= 0) && (col < width));
    }

    // returns -1 if path was not found, distance otherwise
    public int checkDistance(Position from, Position to) {
        // These arrays show the 4 possible movement from a cell
        int[] rowNum = {-1, 0, 0, 1};
        int[] colNum = {0, -1, 1, 0};

        boolean[][] visited = new boolean[height][width];

        visited[from.getX()][from.getY()] = true;
        Deque<queueNode> q = new LinkedList<>();

        queueNode s = new queueNode(from, 0);
        q.add(s);

        while (!q.isEmpty()) {
            queueNode u = q.poll();
            Position point = u.pos;
            if (point.getX() == to.getX() && point.getY() == to.getY())
                return u.dist;
            for (int i = 0; i < 4; i++) {
                int row = point.getX() + rowNum[i];
                int col = point.getY() + colNum[i];

                if (isCellValid(row, col) && map[row][col] == MapTile.FLOOR && !visited[row][col]) {
                    visited[row][col] = true;
                    q.add(new queueNode(new Position(row, col),u.dist + 1 ));
                }
            }
        }

        return -1;
    }
}
