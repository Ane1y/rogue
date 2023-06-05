package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.LevelBuilder;
import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitUpdate;

public class GameLogic {

    private final State state;
    public GameLogic(State state) {
        this.state = state;
    }

    public Delta update(Signal cmd) {
        var delta = new Delta();
        int complexity = state.player.getLevel();
        // map generation
        var levelBuilder = new LevelBuilder();
        levelBuilder.complexity(complexity);
        state.levelMap = levelBuilder.build();
        delta.setMap(state.levelMap);

        // unit generation
        state.units.add(state.player);

        var unitFactory = new UnitFactory(complexity);
        var numberOfEnemies = (int)Math.log(state.player.getLevel());
        for (int i = 0; i < numberOfEnemies; i++) {
            var newMonster = unitFactory.getUnit();
            state.units.add(newMonster);
            delta.add(new UnitUpdate(newMonster));
        }

        state.focus = State.Focus.LEVEL;
        return delta;
    }

    private void constructNewState() {

    }
}
