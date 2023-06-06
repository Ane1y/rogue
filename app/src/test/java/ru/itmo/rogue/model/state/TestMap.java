package ru.itmo.rogue.model.state;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.unit.Position;

public class TestMap {

    @Test
    void testCheckDistance() {
        Map map = new Map(20, 10);
        Assertions.assertEquals(15, map.getDistance(new Position(1, 1), new Position(9, 8)));

    }
}
