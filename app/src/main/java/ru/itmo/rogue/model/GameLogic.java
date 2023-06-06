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
        switch (state.getPlayerTile()) {
            case DOOR_OUT_HARD -> complexity = (int)Math.floor(complexity * 1.5);
            case DOOR_OUT_TREASURE_ROOM -> complexity = 0;
        }

        var levelBuilder = new LevelBuilder()
                .complexity(complexity);
        levelBuilder.build();
        delta.setMap(state.levelMap);

        state.units.clear();
        // unit generation
        state.units.add(state.player);
        state.player.moveTo(state.levelMap.getEntrance());
        delta.add(new UnitUpdate(state.player));
        var unitFactory = new UnitFactory(complexity);
        for (int i = 0; i < state.levelMap.getNumberOfEnemies(); i++) {
            var newMonster = unitFactory.getUnit();
            state.units.add(newMonster);
            delta.add(new UnitUpdate(newMonster));
        }

        state.focus = State.Focus.LEVEL;
        delta.setFocus(state.focus);
        return delta;
    }

    private int numberOfUnit(int complexity) {
        return (int)Math.log(complexity);
    }

}
