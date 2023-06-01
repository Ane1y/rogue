package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.Unit;

public interface Judge {

    /**
     * @param attacker attacking unit
     * @param defender defending unit
     * @return if (>0) returned health loss by the defender, else returned negated health loss by the attacker
     */
    int make_decision(Unit attacker, Unit defender);

}
