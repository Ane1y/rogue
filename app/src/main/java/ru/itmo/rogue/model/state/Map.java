package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Movement;
import ru.itmo.rogue.model.game.unit.Position;

import java.util.*;


/**
 * Class that represents map
 * Instances SHOULD be produced by game.LevelFactory
 * Represents unmoving parts of the level (background for action)
 */
public class Map {
    private final int initialEnemyNumber;
    private final MapTile[][] map;
    private Position entrance = new Position();

    /**
     * Tiles that make up the map
     */
    public enum MapTile {
        FLOOR,
        WALL,
        DOOR_IN,
        DOOR_OUT_NORMAL,
        DOOR_OUT_HARD,
        DOOR_OUT_TREASURE_ROOM
    }

    /**
     * Checks if given Tile is an Entrance tile
     */
    static public boolean isEntrance(MapTile tile) {
        return tile.equals(MapTile.DOOR_IN);
    }

    /**
     * Checks if given Tile is an Exit tile
     */
    static public boolean isExit(MapTile tile) {
        return tile.equals(MapTile.DOOR_OUT_NORMAL) ||
                tile.equals(MapTile.DOOR_OUT_HARD) ||
                tile.equals(MapTile.DOOR_OUT_TREASURE_ROOM);
    }

    /**
     * Checks if given Tile is a Floor tile
     */
    static public boolean isFloor(MapTile tile) {
        return tile.equals(MapTile.FLOOR);
    }

    /**
     * Checks if given Tile is a Wall tile
     */
    static public boolean isWall(MapTile tile) {
        return tile.equals(MapTile.WALL);
    }

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
        this.map = new Map.MapTile[width + 2][height + 2];
        this.initialEnemyNumber = initialEnemyNubmer;
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
    public int getInitialEnemyNumber() {
        return initialEnemyNumber;
    }


    public boolean positionIsInbound(Position pos) {
        return ((pos.getY() >= 0) && (pos.getY() < getHeight()) &&
                (pos.getX() >= 0) && (pos.getX() < getWidth()));
    }
    /**
     * @return true if tile on position `position` represents floor,
     */
    public boolean isFloor(Position pos) {
        return positionIsInbound(pos) && isFloor(getTile(pos));
    }


    /**
     * @return true if tile on position `position` represents exit door,
     */
    public boolean isExit(Position pos) {
        return positionIsInbound(pos) && isExit(getTile(pos));
    }


    /**
     * @return true if tile on position `position` represents entrance,
     */
    public boolean isEntrance(Position pos) {
        return positionIsInbound(pos) && isEntrance(getTile(pos));
    }

    /**
     * @return true if tile on position `position` represents wall,
     */
    public boolean isWall(Position pos) {
        return positionIsInbound(pos) && isWall(getTile(pos));
    }

    public boolean isBorderWall(Position pos) {
        return ((pos.getY() > 0) && (pos.getY() < getHeight() - 1) &&
                (pos.getX() > 0) && (pos.getX() < getWidth() - 1));
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
    // returns -1 if path was not found, distance otherwise
    public ReachableObjects getDistance(Position from, Position to) {
        if (!positionIsInbound(from) || !positionIsInbound(to)) {
            throw new IllegalArgumentException("Given coordinate is out of bound");
        }

        Set<Position> reachableFloors = new HashSet<>();
        Set<Position> reachableWalls = new HashSet<>();

        Queue<QueuedPosition> queue = new ArrayDeque<>();

        queue.add(new QueuedPosition(from, 0));
        reachableFloors.add(from);

        while (!queue.isEmpty()) {
            var currentPosition = queue.poll();

            if (currentPosition.position.equals(to)) {
                return new ReachableObjects(currentPosition.distance, reachableFloors, reachableWalls);
            }

            if (!isFloor(currentPosition.position)) {
                continue; // We can step anywhere only from floor
            }

            for (var movement : Movement.defaults) {
                var newPosition = currentPosition.position.move(movement);

                if (positionIsInbound(newPosition) && !reachableFloors.contains(newPosition)) {
                    if (isFloor(newPosition)) {
                        reachableFloors.add(newPosition);
                        queue.add(new QueuedPosition(newPosition, currentPosition.distance + 1));
                    } else if (isWall(newPosition)) {
                        reachableWalls.add(newPosition);
                    }
                }
            }
        }

        return new ReachableObjects(-1, reachableFloors, reachableWalls);
    }

    public List<Position> getPossiblePath(Position from, Position to) {
        if (!positionIsInbound(from) || !positionIsInbound(to)) {
            throw new IllegalArgumentException("Given coordinate is out of bound");
        }

        java.util.Map<Position, Position> visited = new HashMap<>();

        Queue<Position> queue = new ArrayDeque<>();
        queue.add(from);
        visited.put(from, null);

        Position curPos = from;
        while (!queue.isEmpty()) {
            curPos = queue.poll();
            if (curPos.equals(to)) {
                break;
            }

            for (var movement : Movement.defaults) {
                var newPos = curPos.move(movement);

                if (positionIsInbound(newPos) && !visited.containsKey(newPos)) {
                        visited.put(newPos,curPos);
                        queue.add(newPos);
                }
            }
        }
        List<Position> path = new ArrayList<>();
        path.add(curPos);
        while (!curPos.equals(from)) {
            curPos = visited.get(curPos);
            path.add(curPos);
        }
        return path;
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
        int result = Objects.hash(entrance);
        result = Objects.hash(result, Arrays.deepHashCode(map));
        return result;
    }

    private void assertValidPosition(Position pos) {
        assert pos.getX() >= 0 && pos.getX() < getWidth();
        assert pos.getY() >= 0 && pos.getY() < getHeight();
    }

    public record ReachableObjects(int distance, Set<Position> reachableFloors, Set<Position> reachableWalls) {}
    private record QueuedPosition(Position position, int distance) {}
}
