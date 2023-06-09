package ru.itmo.rogue.model.game;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.UnitPositionUpdate;
import ru.itmo.rogue.model.state.UnitUpdate;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitUpdate {

    private final UnitFactory factory = new UnitFactory(1);


    @Test
    void sameUnit() {
        var unit = factory.getUnit();

        var update1 = new UnitUpdate(unit);
        var update2 = new UnitUpdate(unit);

        assertNotSame(update1, update2);
        assertEquals(update1, update2);

        assertSame(unit, update1.getUnit());
        assertSame(unit, update2.getUnit());
    }

    @Test
    void differentUnits() {
        var unit1 = factory.getUnit();
        var unit2 = factory.getUnit();

        var update1 = new UnitUpdate(unit1);
        var update2 = new UnitUpdate(unit2);

        assertNotEquals(update1, update2);

        assertSame(unit1, update1.getUnit());
        assertSame(unit2, update2.getUnit());
    }

    @Test
    void sameUnitMoved() {
        var unit = factory.getUnit();
        var oldPosition = new Position(1, 1);

        var update1 = new UnitPositionUpdate(unit, oldPosition);
        var update2 = new UnitPositionUpdate(unit, oldPosition);

        assertNotSame(update1, update2);
        assertEquals(update1, update2);

        assertSame(update1.getUnit(), update2.getUnit());
    }

    @Test
    void differentUnitsMoved() {
        var unit1 = factory.getUnit();
        var unit2 = factory.getUnit();

        var pos1 = new Position(1, 1);
        var pos2 = new Position(2, 2);

        var update11 = new UnitPositionUpdate(unit1, pos1);
        var update12 = new UnitPositionUpdate(unit1, pos2);
        var update22 = new UnitPositionUpdate(unit2, pos2);

        assertNotEquals(update11, update12);
        assertNotEquals(update12, update22);
        assertNotEquals(update11, update22);

        assertSame(unit1, update11.getUnit());
        assertSame(unit1, update12.getUnit());
        assertSame(unit2, update22.getUnit());
    }

}
