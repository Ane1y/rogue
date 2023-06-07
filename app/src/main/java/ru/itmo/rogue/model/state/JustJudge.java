package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Unit;

public class JustJudge implements Judge {

    /**
     * @param attacker attacking unit
     * @param defender defending unit
     * @return if (>0) returned health loss by the defender, else returned negated health loss by the attacker
     */
    private UnitUpdate fight(Unit attacker, Unit defender) {
        return defender.changeHealth(-attacker.getStrength());
    }

    @Override
    public UnitUpdate actionResult(Unit unit, Action action, State state) {
        if (!state.getLevelMap().isFloor(action.dest())) {
            return null;
        }

        var targetUnit = state.getUnitOnPosition(action.dest());
        if (targetUnit == null) {
            var oldPos = unit.getPosition();
            unit.moveTo(action.dest());
            return new UnitPositionUpdate(unit, oldPos);
        } else if (targetUnit.isDead()) {
            // TODO: Move this functionality from Judge
            unit.getStash().addAll(targetUnit.getStash());
            targetUnit.getStash().clear();
            state.getUnits().remove(targetUnit);
            return null;
        } else {
            return fight(unit, targetUnit);
        }
    }
}
