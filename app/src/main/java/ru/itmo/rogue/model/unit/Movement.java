package ru.itmo.rogue.model.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Movement(int x, int y) {

    public Movement() {
        this(0, 0);
    }

    public int getDX() {
        return x;
    }

    public int getDY() {
        return y;
    }

    public double getStraightLength() {
        return Math.sqrt(x * x + y * y);
    }

    public int getLength() {
        return x + y;
    }


    static public final Movement NONE  = new Movement();
    static public final Movement UP    = new Movement(0, -1);
    static public final Movement DOWN  = new Movement(0, +1);
    static public final Movement LEFT  = new Movement(-1, 0);
    static public final Movement RIGHT = new Movement(+1, 0);

    static public final List<Movement> defaults
            = new ArrayList<>(Arrays.asList(UP, RIGHT, DOWN, LEFT));
}
