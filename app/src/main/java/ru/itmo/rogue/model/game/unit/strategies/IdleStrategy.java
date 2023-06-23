package ru.itmo.rogue.model.game.unit.strategies;
import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Strategy;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class IdleStrategy implements Strategy {
    @Override
    public Action getAction(Unit unit, State state) {
        return new Action(unit.getPosition());
    }
}
