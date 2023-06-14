package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.Position;

import java.util.*;


/**
 * Class that represents map
 * Instances SHOULD be produced by game.LevelFactory
 * Represents unmoving parts of the level (background for action)
 */
public class Map implements MapView {
    private final int initialEnemyNumber;
    private final Tile[][] map;
    private Position entrance = new Position();

    /**
     * Constructs Map that should contain 0 enemies
     * @param width free space width inside the created map
     * @param height free space height inside the created map
     */
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
        this.map = new Tile[width + 2][height + 2];
        this.initialEnemyNumber = initialEnemyNubmer;
        for (var column: map) {
            Arrays.fill(column, Tile.FLOOR);
        }
        Arrays.fill(map[0], Tile.WALL);
        Arrays.fill(map[width + 1], Tile.WALL);

        for (int i = 0; i < width + 2; i++) {
            map[i][0] = Tile.WALL;
            map[i][height + 1] = Tile.WALL;
        }

    }

    /**
     * Returns tile that is placed on position
     */
    public Tile getTile(Position pos) {
        assertValidPosition(pos);
        return map[pos.getX()][pos.getY()];
    }

    /**
     * Sets tile on position `position` to tile
     */
    public void setTile(Position pos, Tile tile) {
        assertValidPosition(pos);
        map[pos.getX()][pos.getY()] = tile;
        if (MapView.isEntrance(tile)) {
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
    public int getInitialEnemyNumber() {
        return initialEnemyNumber;
    }

    /**
     * @return true if tile on position `position` represents floor,
     */
    public boolean isFloor(Position pos) {
        return isPositionInbound(pos) &&
                MapView.isFloor(getTile(pos));
    }


    /**
     * @return true if tile on position `position` represents exit door,
     */
    public boolean isExit(Position pos) {
        return isPositionInbound(pos) &&
                MapView.isExit(getTile(pos));
    }


    /**
     * @return true if tile on position `position` represents entrance,
     */
    public boolean isEntrance(Position pos) {
        return isPositionInbound(pos) &&
                MapView.isEntrance(getTile(pos));
    }

    /**
     * @return true if tile on position `position` represents wall,
     */
    public boolean isWall(Position pos) {
        return isPositionInbound(pos) &&
                MapView.isWall(getTile(pos));
    }

    /**
     * Computes walking distance between 2 positions using BFS
     * All tiles are valid targets for destinations,
     *  But they can be reachable only if they are in contact with richable floor
     * @param from starting position
     * @param to destination
     * @return amount of steps that should be made to reach `to` from `from`, returns -1 if `to` is unreachable
     * @throws IllegalArgumentException if one of provided positions is out of bounds
     */
    public int getDistance(Position from, Position to) {
        if (!isPositionInbound(from) || !isPositionInbound(to)) {
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

                if (isPositionInbound(newPosition) && !enqueued.contains(newPosition)) {
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
        return Arrays.deepEquals(map, that.map) &&
                Objects.equals(entrance, that.entrance) &&
                Objects.equals(initialEnemyNumber, that.initialEnemyNumber);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(entrance, initialEnemyNumber);
        result = Objects.hash(result, Arrays.deepHashCode(map));
        return result;
    }

    private void assertValidPosition(Position pos) {
        assert pos.getX() >= 0 && pos.getX() < getWidth();
        assert pos.getY() >= 0 && pos.getY() < getHeight();
    }


    public boolean isPositionInbound(Position pos) {
        return ((pos.getY() >= 0) && (pos.getY() < getHeight()) &&
                (pos.getX() >= 0) && (pos.getX() < getWidth()));
    }

    private record QueuedPosition(Position position, int distance) {}
}
