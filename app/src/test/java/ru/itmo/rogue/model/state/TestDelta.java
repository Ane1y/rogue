package ru.itmo.rogue.model.state;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.UnitFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TestDelta {

    @Test
    void emptyDelta() {
        var delta = new Delta();

        assertDoesNotThrow(delta::getInventoryChanges);
        assertDoesNotThrow(delta::getUnitChanges);

        var inventoryChanges = delta.getInventoryChanges();
        var unitChanges = delta.getUnitChanges();

        assertNotNull(inventoryChanges);
        assertNotNull(unitChanges);
        assertEquals(0, inventoryChanges.size());
        assertEquals(0, unitChanges.size());

        assertNull(delta.getFocus());
        assertNull(delta.getMap());
    }

    @Test
    void emptyDeltaHasImmutableLists() {
        var delta = new Delta();

        var inventoryChanges = delta.getInventoryChanges();
        var unitChages = delta.getUnitChanges();

        var invChange = new InventoryFocusUpdate(1);
        var unitChange = new UnitUpdate(UnitFactory.getPlayerUnit());

        assertThrows(UnsupportedOperationException.class, () -> inventoryChanges.add(invChange));
        assertThrows(UnsupportedOperationException.class, () -> unitChages.add(unitChange));
    }

    @Test
    void appendDelta() {
        var delta1 = new Delta();
        delta1.setFocus(State.Focus.LEVEL);

        var delta2 = new Delta();
        delta2.setFocus(State.Focus.INVENTORY);

        assertEquals(State.Focus.LEVEL, delta1.getFocus());
        assertEquals(State.Focus.INVENTORY, delta2.getFocus());

        delta1.append(delta2);
        assertEquals(State.Focus.INVENTORY, delta1.getFocus());
    }
}
