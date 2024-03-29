package ru.itmo.rogue.model;

import ru.itmo.rogue.model.items.ItemFactory;
import ru.itmo.rogue.model.state.MapBuilder;
import ru.itmo.rogue.model.unit.*;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.factory.*;

import java.util.Random;

/**
 * Class that contains general Logic of the game that is active between the levels (Level, Unit and Item generation)
 * Class have control over the State of the game (as all Logic classes)
 */
public class GameLogic {

    private final State state;

    private Random random = new Random();

    public GameLogic(State state) {
        this.state = state;
        generateNewMap(1, Factories.treasury());
    }

    /**
     * Updates game state based on type of door, called by LevelLogic
     */
    public void generateNewMap() {
        var playerLevel = state.getPlayer().getLevel();
        var playerPosition = state.getPlayer().getPosition();

        switch (state.getMap().getTile(playerPosition)) {
            case DOOR_OUT_HARD -> generateNewMap(playerLevel, Factories.hardRoom(playerLevel));
            case DOOR_OUT_NORMAL -> generateNewMap(playerLevel, Factories.defaultRoom(playerLevel));
            case DOOR_OUT_TREASURE_ROOM -> generateNewMap(0, Factories.treasury());
        };
    }
    
    public void generateNewMap(int difficulty, UnitFactory unitFactory) {
        MapBuilder mapBuilder = new MapBuilder();

        if (difficulty == 0) {
            mapBuilder.loadFromDisk(
                    String.format("./app/src/main/resources/complex%d.map",
                    random.nextInt(10)));
        } else {
            mapBuilder.width(87).height(32);
        }

        var levelMap = mapBuilder.build();
        state.setMap(levelMap);

        var reachability = levelMap.getDistance(levelMap.getEntrance(), new Position(levelMap.getWidth() - 1, levelMap.getHeight() - 1));
        unitFactory.setPositions(reachability.reachableFloors());

        for (int i = 0; i < numberOfEnemies(difficulty); i++) {
            state.addUnit(unitFactory.getUnit());
        }
    }

    private int hardDifficulty(int difficulty) {
        return (int)Math.floor(difficulty * 1.5);
    }


    private int numberOfEnemies(int complexity) {
        if (complexity == 0) {
            return state.getPlayer().getLevel();
        }

        return 2 + (int) Math.ceil(Math.log(complexity));
    }
}
