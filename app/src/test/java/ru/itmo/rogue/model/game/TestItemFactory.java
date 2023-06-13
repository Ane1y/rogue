package ru.itmo.rogue.model.game;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.items.ItemFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestItemFactory {

    @Test
    void nothrowTest() {
        var factory = new ItemFactory();

        for (int i = 0; i < 10000; i++) {
            assertDoesNotThrow(factory::getItem);
        }
    }

}
