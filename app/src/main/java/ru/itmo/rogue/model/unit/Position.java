package ru.itmo.rogue.model.unit;

import java.io.Serializable;


/**
 * Class to describe absolute position on the map
 * @param x horizontal coordinate
 * @param y vertical coordinate (0 is "higher" than 10)
 */
public record Position (int x, int y) implements Serializable {

    public Position() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Creates new Position by "moving" this position
     */
    public Position move(Movement movement) {
        return new Position(x  + movement.getDX(), y + movement.getDY());
    }

    /**
     * Computes distance between two positions
     */
    public double distance(Position other){
        return Math.sqrt(Math.pow(other.x - this.x,2) + Math.pow(other.y - this.y,2));
    }

    /**
     * Returns movement that is needed to create `to` from this position
     */
    public Movement getMovement(Position to) {
        return new Movement(to.x - x, to.y - y);
    }

}
