package ru.itmo.rogue.model.unit.strategy;
import ru.itmo.rogue.model.unit.Action;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class IdleStrategy implements Strategy {
    @Override
    public Action getAction(Unit unit, State state) {
        return new Action(unit.getPosition());
    }
}
