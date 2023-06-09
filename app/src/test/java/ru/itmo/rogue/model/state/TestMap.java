package ru.itmo.rogue.model.state;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.unit.Position;

import static org.junit.jupiter.api.Assertions.*;

public class TestMap {

    @Test
    public void testCheckDistance() {
        Map map = new Map(20, 10);

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
    }

    @Test
    public void testUnreachable() {
        Map map = new Map(20, 10);

        var start = new Position(1, 1);

        // Should throw exception (out of bounds):
        assertThrows(
                IllegalArgumentException.class,
                () -> map.getDistance(start, new Position(25, 5)));

        // Should be REACHABLE (walls are reachable if in reachable are next to reachable floor)
        assertSquareDistance(map, start, new Position(14, 11));
        assertSquareDistance(map, start, new Position(21, 5));

        // Should be REACHABLE:
        assertNotEquals(-1, map.getDistance(start, new Position(10, 1)));
        assertNotEquals(-1, map.getDistance(start, new Position(10, 5)));

        // Place wall in the middle:
        for (int i = 0; i < map.getHeight(); i++) {
            map.setTile(new Position(5, i), Map.MapTile.WALL);
        }

        // Should be unreachable:
        assertEquals(-1, map.getDistance(start, new Position(10, 1)));
        assertEquals(-1, map.getDistance(start, new Position(10, 5)));

        // Placed wall should be REACHABLE:
        assertSquareDistance(map, start, new Position(5, 1));
        assertSquareDistance(map, start, new Position(5, 5));

        // This wall should be unreachable because it bordered with 3 other wall and map border
        assertEquals(-1, map.getDistance(start, new Position(5, 0)));
    }

    private void assertSquareDistance(Map map, Position from, Position to) {
        var distance = map.getDistance(from, to);
        var movement = from.getMovement(to);
        assertEquals(movement.getLength(), distance);
    }

}
