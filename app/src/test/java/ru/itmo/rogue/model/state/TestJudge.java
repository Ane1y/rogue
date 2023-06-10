package ru.itmo.rogue.model.state;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;

public class TestJudge {

    private static final Position target = new Position(1, 2);
    private static final AbstractJudge judge = new JustJudge();

    private State getStateWith(Map.MapTile tile) {
        var state = new State();
        var map = new Map(3, 4);
        map.setTile(target, tile);
        state.setMap(map);
        return state;
    }

    private State getStateWith(Unit unit) {
        var state = getStateWith(Map.MapTile.FLOOR);
        unit.moveTo(target);
        state.addUnit(unit);
        return state;
    }

    @Test
    void stepOnWall() {
        var state = getStateWith(Map.MapTile.WALL);
        var result = judge.actionResult(state.getPlayer(), new Action(target), state);
        assertEquals(AbstractJudge.ActionResult.NOTHING, result);
    }

    @ParameterizedTest
    @EnumSource(
            value = Map.MapTile.class,
            names = {"FLOOR", "DOOR_OUT_NORMAL", "DOOR_OUT_HARD", "DOOR_OUT_TREASURE_ROOM"}
    )
    void stepOnExitWalkable(Map.MapTile tile) {
        var state = getStateWith(tile);
        var result = judge.actionResult(state.getPlayer(), new Action(target), state);
        assertEquals(AbstractJudge.ActionResult.MOVE, result);
    }

    @ParameterizedTest
    @EnumSource(
            value = Map.MapTile.class,
            names = {"DOOR_OUT_NORMAL", "DOOR_OUT_HARD", "DOOR_OUT_TREASURE_ROOM"}
    )
    void stepOnClosedExit(Map.MapTile tile) {
        var factory = new UnitFactory(1);
        var unit = factory.getUnit();
        unit.changeHealth(10);

        var state = getStateWith(tile);
        state.addUnit(unit); // Doors should be closed when there are living enemies

        var result = judge.actionResult(state.getPlayer(), new Action(target), state);
        assertEquals(AbstractJudge.ActionResult.NOTHING, result);
    }

    @Test
    void stepOnEntrance() {
        var state = getStateWith(Map.MapTile.DOOR_IN);
        var result = judge.actionResult(state.getPlayer(), new Action(target), state);

        assertEquals(AbstractJudge.ActionResult.NOTHING, result);
    }

    @Test
    void stepOnLivingEnemy() {
        var factory = new UnitFactory(1);
        var unit = factory.getUnit();
        unit.changeHealth(10); // Ensure its alive

        var state = getStateWith(unit);
        var result = judge.actionResult(state.getPlayer(), new Action(target), state);

        assertEquals(AbstractJudge.ActionResult.FIGHT, result);
    }

    @Test
    void stepOnDeadEnemy() {
        var factory = new UnitFactory(1);
        var unit = factory.getUnit();
        unit.changeHealth(-unit.getHealth()); // Ensure its dead

        var state = getStateWith(unit);
        var result = judge.actionResult(state.getPlayer(), new Action(target), state);

        assertEquals(AbstractJudge.ActionResult.MOVE_AND_COLLECT, result);
    }

}
