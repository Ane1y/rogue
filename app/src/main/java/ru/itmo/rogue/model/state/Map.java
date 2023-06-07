package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Movement;
import ru.itmo.rogue.model.game.unit.Position;

import java.util.*;

public class Map {
    private final int initialEnemyNubmer;
    private final MapTile[][] map;
    private Position entrance = new Position();

    public enum MapTile {
        FLOOR,
        WALL,
        DOOR_IN,
        DOOR_OUT_NORMAL,
        DOOR_OUT_HARD,
        DOOR_OUT_TREASURE_ROOM
    }

    static public boolean isEntrance(MapTile tile) {
        return tile.equals(MapTile.DOOR_IN);
    }

    static public boolean isExit(MapTile tile) {
        return tile.equals(MapTile.DOOR_OUT_NORMAL) ||
                tile.equals(MapTile.DOOR_OUT_HARD) ||
                tile.equals(MapTile.DOOR_OUT_TREASURE_ROOM);
    }

    static public boolean isFloor(MapTile tile) {
        return tile.equals(MapTile.FLOOR);
    }

    static public boolean isWall(MapTile tile) {
        return tile.equals(MapTile.WALL);
    }

    public Map(int width, int height) {
        this(width, height, 0);
    }


    /**
     * Produces map with free space of size width * height surrounded by wall
     * @param width width of free space
     * @param height height of free space
     * @param initialEnemyNubmer number of enemies that should be placed on the map
     */
    public Map(int width, int height, int initialEnemyNubmer) {
        this.map = new Map.MapTile[width + 2][height + 2];
        this.initialEnemyNubmer = initialEnemyNubmer;
        for (var column: map) {
            Arrays.fill(column, MapTile.FLOOR);
        }
        Arrays.fill(map[0], MapTile.WALL);
        Arrays.fill(map[width + 1], MapTile.WALL);

        for (int i = 0; i < width + 2; i++) {
            map[i][0] = MapTile.WALL;
            map[i][height + 1] = MapTile.WALL;
        }

    }

    /**
     * Returns tile that is placed on position
     */
    public MapTile getTile(Position pos) {
        assertValidPosition(pos);
        return map[pos.getX()][pos.getY()];
    }

    /**
     * Sets tile on position `position` to tile
     */
    public void setTile(Position pos, MapTile tile) {
        assertValidPosition(pos);
        map[pos.getX()][pos.getY()] = tile;
        if (isEntrance(tile)) {
            entrance = pos;
        }
    }

    /**
     * @return position of the entrance
     */
    public Position getEntrance() {
        return entrance;
    }

    /**
     * @return factual height of the map (including border walls)
     */
    public int getHeight() {
        return map[0].length;
    }

    /**
     * @return factual width of the map (including border walls)
     */
    public int getWidth() {
        return map.length;
    }

    /**
     * @return number of enemies that should be placed on the map
     */
    public int getInitialEnemyNubmer() {
        return initialEnemyNubmer;
    }

    /**
     * @return true if tile on position `position` represents floor,
     */
    public boolean isFloor(Position pos) {
        return isFloor(getTile(pos));
    }


    /**
     * @return true if tile on position `position` represents exit door,
     */
    public boolean isExit(Position pos) {
        return isExit(getTile(pos));
    }


    /**
     * @return true if tile on position `position` represents entrance,
     */
    public boolean isEntrance(Position pos) {
        return isEntrance(getTile(pos));
    }

    /**
     * @return true if tile on position `position` represents wall,
     */
    public boolean isWall(Position pos) {
        return isWall(getTile(pos));
    }

    /**
     * Computes walking distance between 2 positions using BFS
     * All tiles are valid targets for destinations,
     *  But they can be reachable only if they are in contact with richable floor
     * @param from starting position
     * @param to destination
     * @return amount of steps that should be made to reach `to` from `from`, returns -1 if `to` is unreachable
     */
    // returns -1 if path was not found, distance otherwise
    public int getDistance(Position from, Position to) {
        if (!positionIsInbound(from) || !positionIsInbound(to)) {
            throw new IllegalArgumentException("Given coordinate is out of bound");
        }

        Set<Position> enqueued = new HashSet<>();
        Queue<QueuedPosition> queue = new ArrayDeque<>();

        queue.add(new QueuedPosition(from, 0));
        enqueued.add(from);

        while (!queue.isEmpty()) {
            var currentPosition = queue.poll();

            if (currentPosition.position.equals(to)) {
                return currentPosition.distance;
            }

            if (!isFloor(currentPosition.position)) {
                continue; // We can step anywhere only from floor
            }

            for (var movement : Movement.defaults) {
                var newPosition = currentPosition.position.move(movement);

                if (positionIsInbound(newPosition) && !enqueued.contains(newPosition)) {
                    enqueued.add(newPosition);
                    queue.add(new QueuedPosition(newPosition, currentPosition.distance + 1));
                }
            }
        }

        return -1;
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

    private void assertValidPosition(Position pos) {
        assert pos.getX() >= 0 && pos.getX() < getWidth();
        assert pos.getY() >= 0 && pos.getY() < getHeight();
    }

    private boolean positionIsInbound(Position pos) {
        return ((pos.getY() >= 0) && (pos.getY() < getHeight()) &&
                (pos.getX() >= 0) && (pos.getX() < getWidth()));
    }

    private record QueuedPosition(Position position, int distance) {}
}
