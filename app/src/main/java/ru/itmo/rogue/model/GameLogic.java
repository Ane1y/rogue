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

    public Delta newMap() {
        state.levelMap = new LevelBuilder()
                .complexity(1)
                .build();

        var delta = new Delta();
        delta.setMap(state.levelMap);
        return delta;
    }

    public Delta update(Signal cmd) {
        var delta = new Delta();
        int playerLevel = state.player.getLevel();

        int difficulty = switch (state.levelMap.getTile(state.player.getPosition())) {
            case DOOR_OUT_HARD -> hardDifficulty(playerLevel);
            case DOOR_OUT_NORMAL -> playerLevel;
            case DOOR_OUT_TREASURE_ROOM -> 0;
            default -> -1;
        };

        if (difficulty == -1) {
            return delta;
        }

        var levelBuilder = new LevelBuilder();
        state.levelMap = levelBuilder
                .complexity(playerLevel)
                .build();
        delta.setMap(state.levelMap);


        // Generate Units
        state.units.clear();
        state.units.add(state.player); // Add player -- must be first in list
        state.player.moveTo(state.levelMap.getEntrance());
        delta.add(new UnitUpdate(state.player));

        var unitFactory = new UnitFactory(difficulty);
        for (int i = 0; i < state.levelMap.getInitialEnemyNubmer(); i++) {
            var enemy = unitFactory.getUnit();
            if (enemy == null) { // TODO: Remove when NotNull guarantee is in place
                continue;
            }
            state.units.add(enemy);
            delta.add(new UnitUpdate(enemy));
        }

        state.focus = State.Focus.LEVEL;
        delta.setFocus(state.focus);
        return delta;
    }

    private int hardDifficulty(int difficulty) {
        return (int)Math.floor(difficulty * 1.5);
    }


}
