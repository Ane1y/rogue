package ru.itmo.rogue.model.state;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.unit.Position;

import static org.junit.jupiter.api.Assertions.*;

public class TestMap {

    @Test
    public void testCheckDistance() {
        Map map = new Map(20, 10, 0);

        var start = new Position(1, 1);

        assertSquareDistance(map, start, new Position(1, 2));
        assertSquareDistance(map, start, new Position(1, 6));
        assertSquareDistance(map, start, new Position(1, 8));

        assertSquareDistance(map, start, new Position(2, 1));
        assertSquareDistance(map, start, new Position(6, 1));
        assertSquareDistance(map, start, new Position(8, 1));

        assertSquareDistance(map, start, new Position(2, 2));
        assertSquareDistance(map, start, new Position(6, 6));
        assertSquareDistance(map, start, new Position(8, 8));
//        assertEquals(15, map.getDistance(new Position(1, 1), new Position(9, 8)));
    }

    public void assertSquareDistance(Map map, Position from, Position to) {
        var distance = map.getDistance(from, to);
        var movement = from.getMovement(to);
        assertEquals(movement.getLength(), distance);
    }

}
