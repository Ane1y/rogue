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
        return defender.health(attacker.getStrength());
    }

    @Override
    public UnitUpdate actionResult(Unit unit, Action action, State state) {
        if (state.levelMap.free(action.dest())) {
            var standingUnit = state.getUnitOnPosition(action.dest());
            if (standingUnit == null) {
                var oldPos = unit.getPosition().copy();
                // no one there
                unit.moveTo(action.dest());
                return new UnitPositionUpdate(unit, oldPos);
            } else if (standingUnit.dead()) {
                var lyingUnit = standingUnit; // извините
                unit.getStash().addAll(lyingUnit.getStash());
                lyingUnit.getStash().clear();
                state.units.remove(lyingUnit);
            } else {
                return fight(unit, standingUnit);
            }
        }
        return null;
    }
}
