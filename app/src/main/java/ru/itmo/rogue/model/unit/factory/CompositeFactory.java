package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.util.*;


/**
 * Composite Factory created from multiple different factories
 * Each factory is used with probability that was passed along
 * This factory OVERRIDES position of produced units
 */
public class CompositeFactory extends AbstractFactory {
    private final Random random = new Random();
    private final List<FactoryProbability> listFactories = new ArrayList<>();

    public CompositeFactory(FactoryProbability... unitFactories) {
        super();
    }

    /**
     * @param possiblePositions see: AbstractFactory
     * @param unitFactories Optional factories
     */
    public CompositeFactory(List<Position> possiblePositions,
                            FactoryProbability... unitFactories)
    {
        super(possiblePositions);
        listFactories.addAll(List.of(unitFactories));
    }

    /**
     * Adds factory to the composite
     * @param factory factory
     * @param probability probability
     */
    public void put(UnitFactory factory, int probability) {
        listFactories.add(new FactoryProbability(factory, probability));
    }

    @Override
    public Unit getUnit() {
        int sum = getSum();
        int prob = random.nextInt(sum);

        int currBound = 0;
        for (var fp : listFactories) {
            currBound += fp.probability();
            if (prob < currBound) {
                var unit = fp.factory().getUnit();
                unit.moveTo(generatePosition());
                return unit;
            }
        }
        throw new RuntimeException("Unreachable code");
    }

    @Override
    protected Strategy getStrategy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public char getAliveChar() {
        return 0;
    }

    private int getSum() {
        return listFactories.stream()
                .map(FactoryProbability::probability)
                .mapToInt(Integer::intValue)
                .sum();
    }
}