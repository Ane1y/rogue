package ru.itmo.rogue.model.game;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.state.LevelBuilder;

public class TestLevelBuilder {
    @Test
    public void testBuild() {
        var levelBuilder = new LevelBuilder().complexity(10);
        var map = levelBuilder.build();
    }
}
