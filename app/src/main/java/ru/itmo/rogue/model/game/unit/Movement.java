package ru.itmo.rogue.model.game.unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movement {
    static public final List<Position> shifts = new ArrayList<>(
            Arrays.asList(
                    new Position(-1, 0),
                    new Position(0, -1),
                    new Position(0, 1),
                    new Position(1, 0)
            ));
}
