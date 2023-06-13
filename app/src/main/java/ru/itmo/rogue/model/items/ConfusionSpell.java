package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.ConfusionStrategy;
import ru.itmo.rogue.model.state.State;

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
    public Delta apply(Unit unit, State state) {
        // Randomly selects unit from the list
        // TODO: Maybe select closest one?
        int unitCount = state.getUnits().size();
        Unit targetUnit;

        if (unitCount == 1) {
            targetUnit = state.getPlayer(); // If there's no enemies confuse the player
        } else {
            var unitIndex = random.nextInt(unitCount - 1);
            targetUnit = state.getUnits().get(unitIndex + 1);
        }

        var unitStrategy = targetUnit.getStrategy();
        var confusionStrategy = new ConfusionStrategy(unitStrategy, duration);
        targetUnit.setStrategy(confusionStrategy);
        return new Delta();
    }

    @Override
    public String getName() {
        return "Confusion Spell";
    }
}
