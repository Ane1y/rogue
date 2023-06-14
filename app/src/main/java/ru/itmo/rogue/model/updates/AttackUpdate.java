package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;

public class AttackUpdate implements StateUpdate {

    private final UnitView attackerView;
    private final UnitView defenderView;

    private final int damage;

    public AttackUpdate(UnitView attacker, UnitView defender) {
        this(attacker, defender, attacker.getStrength());
    }

    public AttackUpdate(UnitView attacker, UnitView defender, int damage) {
        this.attackerView = attacker;
        this.defenderView = defender;
        this.damage = damage;
    }

    @Override
    public void apply(State state) {
        if (defenderView.isDead()) {
            return; // Cannot attack dead unit
        }

        var attacker = state.getUnitWithView(attackerView);
        var defender = state.getUnitWithView(defenderView);
        defender.changeHealth(-damage);
        if (defender.isDead()) { // Transfer experience immediately
            attacker.increaseExperience(defender.getExperience());
            defender.wipeExperience();
        }
    }
}
