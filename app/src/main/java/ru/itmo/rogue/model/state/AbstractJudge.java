package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Unit;

public abstract class AbstractJudge {

    public enum ActionResult {
        NOTHING,
        MOVE,
        FIGHT,
        MOVE_AND_COLLECT // Includes movement
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
        boolean walkable = map.isFloor(destination) || map.isExit(destination);

        if (!walkable) {
            return ActionResult.NOTHING;
        }

        var targetUnit = state.getUnitOnPosition(action.dest());
        if (targetUnit == null) {
            return ActionResult.MOVE;
        } else if (targetUnit.isDead()) {
            return ActionResult.MOVE_AND_COLLECT;
        } else {
            return ActionResult.FIGHT;
        }
    }

    /**
     * @param attacker attacking unit
     * @param defender defending unit
     * @return positive value - damage to defender
     *          negative value - negated damage to attacker
     */
    public abstract int resolveFight(Unit attacker, Unit defender);
}
