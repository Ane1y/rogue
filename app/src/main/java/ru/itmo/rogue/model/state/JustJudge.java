package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

public class JustJudge extends AbstractJudge {

    @Override
    public int resolveFight(Unit attacker, Unit defender) {
        return attacker.getStrength();
    }
}
