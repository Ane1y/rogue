package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

/**
 * This judge is very just,
 * In his opinion defender should always get as much damage as attacker can deal
 */
public class JustJudge extends AbstractJudge {

    /**
     * See: AbstractJudge::resolveFight doc
     */
    @Override
    public int resolveFight(Unit attacker, Unit defender) {
        return attacker.getStrength();
    }
}
