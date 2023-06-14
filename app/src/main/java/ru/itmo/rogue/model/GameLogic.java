package ru.itmo.rogue.model;

import ru.itmo.rogue.model.items.ItemFactory;
import ru.itmo.rogue.model.state.MapBuilder;
import ru.itmo.rogue.model.unit.*;
import ru.itmo.rogue.model.state.State;

import java.util.Random;

/**
 * Class that contains general Logic of the game that is active between the levels (Level, Unit and Item generation)
 * Class have control over the State of the game (as all Logic classes)
 */
public class GameLogic {

    private final State state;

    public GameLogic(State state) {
        this.state = state;
        generateNewMap(1);
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
    
    public void generateNewMap(int difficulty) {
        var levelBuilder = new MapBuilder()
                .width(87)
                .height(32)
                .complexity(difficulty);

        if (difficulty == 0) {
            levelBuilder.loadFromDisk("./app/src/main/resources/simple.map");
        }

        var levelMap = levelBuilder.build();

        state.setMap(levelMap);

        var reachability = levelMap.getDistance(levelMap.getEntrance(), new Position(levelMap.getWidth() - 1, levelMap.getHeight() - 1));
        // Generate Units
        var random = new Random();
        var itemFactory = new ItemFactory();
        var aggressiveFactory = new AggressiveFactory(difficulty);
        var idleFactory = new ChestFactory(difficulty);
        var cowardFactory = new CowardFactory(difficulty);
        var unitFactory = new CompositeFactory(reachability.reachableFloors(),
                new FactoryProbability(aggressiveFactory, 3),
                new FactoryProbability(idleFactory, 1),
                new FactoryProbability(cowardFactory, 3)
                );
        for (int i = 0; i < numberOfEnemies(difficulty); i++) {
            var enemy = unitFactory.getUnit();

            var items = random.nextInt(1, 5);
            for (int j = 0; j < items; j++) {
                enemy.addItem(itemFactory.getItem());
            }

            state.addUnit(enemy);
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
