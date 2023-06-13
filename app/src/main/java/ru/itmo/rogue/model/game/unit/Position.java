package ru.itmo.rogue.model.game.unit;

import java.io.Serializable;

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

    public Position move(Movement movement) {
        return new Position(x  + movement.getDX(), y + movement.getDY());
    }
    public double distance(Position other){
        return Math.sqrt(Math.pow(other.x - this.x,2) + Math.pow(other.y - this.y,2));
    }

    public Movement getMovement(Position to) {
        return new Movement(to.x - x, to.y - y);
    }

}
