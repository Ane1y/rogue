package ru.itmo.rogue.model.unit;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPositionAndMovement {

    @Test
    void trivial() {
        Position position = new Position(1, 2);

        assertEquals(position.getX(), position.x());
        assertEquals(position.getY(), position.y());

        Movement movement = new Movement(2, 3);
        assertEquals(movement.getDX(), movement.x());
        assertEquals(movement.getDY(), movement.y());
    }

    @Test
    void movementTest() {
        var posFrom = new Position(1, 1);
        var posTo = new Position(3, 4);
        var movement = new Movement(2, 3);

        assertNotEquals(posFrom, posTo);
        var moved = posFrom.move(movement);
        assertEquals(posTo, moved);

        var rMovement = posFrom.getMovement(posTo);
        assertEquals(movement, rMovement);
    }

    @Test
    void distanceTest() {
        var pos1 = new Position(2, 7);
        var pos2 = new Position(4, 3);
        var move = pos1.getMovement(pos2);

        assertEquals(Math.sqrt(2 * 2 + 7 * 7), pos1.distance(new Position(0, 0)));
        assertEquals(pos2.distance(pos1), move.getStraightLength());
    }

}
