package ru.itmo.rogue.model;

import ru.itmo.rogue.model.state.MapBuilder;
import ru.itmo.rogue.model.unit.UnitFactory;
import ru.itmo.rogue.model.state.State;

/**
 * Class that contains general Logic of the game that is active between the levels (Level, Unit and Item generation)
 * Class have control over the State of the game (as all Logic classes)
 */
public class GameLogic {

    private final State state;

    public GameLogic(State state) {
        this.state = state;
        generateNewMap(0);
    }

    /**
     * Updates game state based on type of door, called by LevelLogic
     */
    public void generateNewMap() {
        var playerLevel = state.getPlayer().getLevel();
        var playerPosition = state.getPlayer().getPosition();

        int difficulty = switch (state.getMap().getTile(playerPosition)) {
            case DOOR_OUT_HARD -> hardDifficulty(playerLevel);
            case DOOR_OUT_NORMAL -> playerLevel;
            case DOOR_OUT_TREASURE_ROOM -> 0;
            default -> -1;
        };

        if (difficulty == -1) {
            return;
        }

        generateNewMap(difficulty);
    }


    /**
     * Updates game state based on type of door, called by LevelLogic or on initialization
     */
    public void generateNewMap(int difficulty) {
        assert difficulty >= 0;

        var levelBuilder = new MapBuilder();
        var levelMap = levelBuilder
                .complexity(difficulty)
                .build();

        state.setMap(levelMap);

        // Generate Units
        var unitFactory = new UnitFactory(difficulty);
        for (int i = 0; i < state.getMap().getInitialEnemyNumber(); i++) {
            var enemy = unitFactory.getUnit();
            state.addUnit(enemy);
        }
    }

    private int hardDifficulty(int difficulty) {
        return (int)Math.floor(difficulty * 1.5);
    }


}
