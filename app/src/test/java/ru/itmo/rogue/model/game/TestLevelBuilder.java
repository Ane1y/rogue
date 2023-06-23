package ru.itmo.rogue.model.game;

import org.junit.jupiter.api.Test;

public class TestLevelBuilder {
    @Test
    public void testBuild() {
        var levelBuilder = new LevelBuilder().complexity(10);
        var map = levelBuilder.build();
    }
}
