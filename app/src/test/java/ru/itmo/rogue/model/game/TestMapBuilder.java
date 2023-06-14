package ru.itmo.rogue.model.game;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.state.MapBuilder;

public class TestMapBuilder {
    @Test
    public void testBuild() {
        var levelBuilder = new MapBuilder().complexity(10);
        var map = levelBuilder.build();
    }
}
