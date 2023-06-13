package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Unit;

public abstract class AbstractJudge {

    /**
     * Unit may want to do some action, but Judge determines what result that action will have
     */
    public enum ActionResult {
        NOTHING,    // Action has no result (unit stepped into the wall)
        MOVE,       // ... results in move
        FIGHT,      // ... results in move
        MOVE_AND_COLLECT // ... results in move and item collection (stepped on dead enemy or chest)
    }

    /**
     * @param unit acting unit
     * @param action pending action
     * @param state current state
     * @return null if nothing
     */
    public ActionResult actionResult(Unit unit, Action action, State state) {
        var map = state.getLevelMap();
        var destination = action.dest();
        if (unit.getPosition().equals(action.dest())) {
            return ActionResult.NOTHING;
        }
        // Doors are open only if all enemies are dead
        boolean doorsOpen = state.getUnits().stream().allMatch(u -> u == state.getPlayer() || u.isDead());
        boolean walkable = map.isFloor(destination) || (map.isExit(destination) && doorsOpen);

        if (!walkable) {
            return ActionResult.NOTHING;
        }

        var targetUnit = state.getUnitOnPosition(action.dest());
        if (targetUnit == null) {
            return ActionResult.MOVE;
        }

        if (targetUnit.isDead()) {
            return ActionResult.MOVE_AND_COLLECT;
        }

        return ActionResult.FIGHT;
    }

    /**
     * @param attacker attacking unit
     * @param defender defending unit
     * @return positive value - damage to defender
     *          negative value - negated damage to attacker
     */
    public abstract int resolveFight(Unit attacker, Unit defender);
}
