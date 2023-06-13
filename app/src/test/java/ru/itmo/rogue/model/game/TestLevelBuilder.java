package ru.itmo.rogue.model.game;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.unit.Position;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.Format;

public class TestLevelBuilder {
    @Test
    public void testBuild() {
        var levelBuilder = new LevelBuilder().complexity(10);
        var map = levelBuilder.build();
    }

    @Test
    public void testPartitioning() {
//        var partition = new LevelBuilder().new Partition(200, 60, new Position(0, 0), 2, 1);
////        var centers = partition.getCenters();
//        for (var c : centers) {
//            System.out.println(String.format("( %d, %d)", c.x(), c.y()));
//        }
    }
}
