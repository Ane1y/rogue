package ru.itmo.rogue.model.game.unit;

public class Position {

    private int x;
    private int y;

    public Position() {
        this(0, 0);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public double distance(Position other){
        return Math.sqrt(Math.pow(other.x - this.x,2) + Math.pow(other.y - this.y,2));
    }

    public Position copy() {
        return new Position(x, y);
    }
}
