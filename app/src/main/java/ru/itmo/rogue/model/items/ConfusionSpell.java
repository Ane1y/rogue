package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.unit.strategy.ConfusionStrategy;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.StrategyUpdate;

import java.util.List;
import java.util.Random;

public class ConfusionSpell implements Item {

    private final int duration ;

    private static final Random random = new Random();

    public ConfusionSpell() {
        this.duration = 5;
    }

    public ConfusionSpell(int duration) {
        this.duration = duration;
    }

    @Override
    public StateUpdate apply(UnitView unit, StateView state) {
        // Randomly selects unit from the list
        // TODO: Maybe select closest one?
        int unitCount = state.getUnits().size();
        UnitView targetUnit;

        if (unitCount == 1) {
            targetUnit = state.getPlayer(); // If there's no enemies confuse the player
        } else {
            var unitIndex = random.nextInt(unitCount - 1);
            targetUnit = state.getUnits().get(unitIndex + 1);
        }

        var unitStrategy = targetUnit.getStrategy();
        var confusionStrategy = new ConfusionStrategy(unitStrategy, duration);

        return new StrategyUpdate(targetUnit, confusionStrategy);
    }

    @Override
    public String getName() {
        return "Confusion Spell";
    }
}
