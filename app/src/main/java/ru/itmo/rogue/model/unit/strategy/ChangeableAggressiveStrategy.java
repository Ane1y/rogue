package ru.itmo.rogue.model.game.unit.strategies;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Strategy;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class ChangeableAggressiveStrategy implements Strategy {
    private final int MINIMAL_HEALTH = 2;
    private  Strategy currentStrategy = new AgressiveStrategy();
    @Override
    public Action getAction(Unit unit, State state){
        return currentStrategy.getAction(unit, state);
    }


    @Override
    public Strategy nextStrategy(Unit unit) {
        int unitHealth = unit.getHealth();
        if (unitHealth <= MINIMAL_HEALTH) {
            currentStrategy = new CowardStrategy();
        }
        else {
            currentStrategy = new AgressiveStrategy();
        }
        return this;
    }
}
