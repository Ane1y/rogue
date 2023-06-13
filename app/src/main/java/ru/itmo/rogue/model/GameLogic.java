package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
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
    }

    /**
     * Generates default map and updates state accordingly
     * @return Delta corresponding to changes
     */
    public Delta defaultMap() {
        return update(0);
    }

    /**
     * Updates game state, called on Model initialization, or by LevelLogic
     * @param ignored generally Logic classes have update method that accepts input, but in this case input is ignored
     * @return delta that reflects changes made by the method
     */
    public Delta update(Signal ignored) {
        var playerLevel = state.getPlayer().getLevel();
        var playerPosition = state.getPlayer().getPosition();

        int difficulty = switch (state.getMap().getTile(playerPosition)) {
            case DOOR_OUT_HARD -> hardDifficulty(playerLevel);
            case DOOR_OUT_NORMAL -> playerLevel;
            case DOOR_OUT_TREASURE_ROOM -> 0;
            default -> -1;
        };

        return update(difficulty);
    }

    private Delta update(int difficulty) {
        if (difficulty == -1) {
            return new Delta(); // Empty delta
        }

        var levelBuilder = new MapBuilder();
        var levelMap = levelBuilder
                .complexity(difficulty)
                .build();

        var delta = state.setMap(levelMap);

        // Generate Units
        var unitFactory = new UnitFactory(difficulty);
        for (int i = 0; i < state.getMap().getInitialEnemyNumber(); i++) {
            var enemy = unitFactory.getUnit();
            if (enemy == null) { // TODO: Remove when NotNull guarantee is in place
                continue;
            }
            delta.append(state.addUnit(enemy));
        }

        delta.append(state.setFocus(State.Focus.LEVEL));
        return delta;
    }

    private int hardDifficulty(int difficulty) {
        return (int)Math.floor(difficulty * 1.5);
    }


}
