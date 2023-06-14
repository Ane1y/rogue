package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.unit.strategy.Strategy;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class StrategyUpdate extends UnitUpdate {
    private final Strategy strategy;

    public StrategyUpdate(UnitView unit, Strategy strategy)
    {
        super(unit);
        this.strategy = strategy;
    }
    @Override
    public void apply(State state) {
        state.getUnitWithView(view).setStrategy(strategy);
    }
}
