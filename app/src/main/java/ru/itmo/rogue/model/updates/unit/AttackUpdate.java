package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class AttackUpdate extends UnitUpdate {
    private final UnitView defenderView;

    private final int damage;

    public AttackUpdate(UnitView attacker, UnitView defender) {
        this(attacker, defender, attacker.getStrength());
    }

    public AttackUpdate(UnitView attacker, UnitView defender, int damage) {
        super(attacker);
        this.defenderView = defender;
        this.damage = damage;
    }

    @Override
    public void userApply(State state) {
        if (defenderView.isDead()) {
            return; // Cannot attack dead unit
        }

        var attacker = state.getUnitWithView(view);
        var defender = state.getUnitWithView(defenderView);
        defender.changeHealth(-damage);
        if (defender.isDead()) { // Transfer experience immediately
            attacker.increaseExperience(defender.getExperience());
            defender.wipeExperience();
        }
    }
}
