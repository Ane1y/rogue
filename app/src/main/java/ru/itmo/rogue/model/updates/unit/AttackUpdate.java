package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;


/**
 * This interaction applies attack of one unit on the other.
 * If attacking unit is dead before the moment of the update's application,
 *      it won't be applied
 * Attack damage can be specified
 * As the result of the attack defending unit loses health (unless negative damage is specified)
 * If defending unit dies (health drops below 1) as the result of the attack,
 *      attacking unit will receive experience bonus
 */
public class AttackUpdate extends UnitUpdate {
    private final UnitView defenderView;

    private final int damage;

    /**
     * Creates AttackUpdate, this constructor assumes attacker's strength as attack damage
     */
    public AttackUpdate(UnitView attacker, UnitView defender) {
        this(attacker, defender, attacker.getStrength());
    }


    /**
     * Creates AttackUpdate
     */
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
