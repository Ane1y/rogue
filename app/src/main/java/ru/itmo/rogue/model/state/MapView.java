package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.Position;

public interface MapView {

    /**
     * Tiles that make up the map
     */
    enum Tile {
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
    static boolean isEntrance(Tile tile) {
        return tile.equals(Tile.DOOR_IN);
    }

    /**
     * Checks if given Tile is an Exit tile
     */
    static boolean isExit(Tile tile) {
        return tile.equals(Tile.DOOR_OUT_NORMAL) ||
                tile.equals(Tile.DOOR_OUT_HARD) ||
                tile.equals(Tile.DOOR_OUT_TREASURE_ROOM);
    }

    /**
     * Checks if given Tile is a Floor tile
     */
    static boolean isFloor(Tile tile) {
        return tile.equals(Tile.FLOOR);
    }

    /**
     * Checks if given Tile is a Wall tile
     */
    static boolean isWall(Tile tile) {
        return tile.equals(Tile.WALL);
    }



    Tile getTile(Position pos);


    /**
     * @return position of the entrance
     */
    Position getEntrance();

    /**
     * @return Map height including border walls)
     */
    int getHeight();

    /**
     * @return Map width (including border walls)
     */
    int getWidth();

    /**
     * @return number of enemies that should be placed on the map
     */
    public int getInitialEnemyNumber();

    /**
     * Computes walking distance between two points
     * (Point can be floor or reachable door or wall)
     */
    int getDistance(Position from, Position to);

    /**
     * @return true if tile on position `position` represents floor,
     */
    public boolean isFloor(Position pos);

    /**
     * @return true if tile on position `position` represents exit door,
     */
    public boolean isExit(Position pos);


    /**
     * @return true if tile on position `position` represents entrance,
     */
    boolean isEntrance(Position pos);
    /**
     * @return true if tile on position `position` represents wall,
     */
    public boolean isWall(Position pos);

}
