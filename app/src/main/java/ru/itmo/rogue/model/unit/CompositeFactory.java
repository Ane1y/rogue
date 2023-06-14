package ru.itmo.rogue.model.unit;

import java.util.*;

public class CompositeFactory extends AbstractFactory {
    private final Random random = new Random();


    private final List<FactoryProbability> listFactories = new ArrayList<>();


    public CompositeFactory(List<Position> possiblePositions, FactoryProbability unitFactory1, FactoryProbability... unitFactories) {
        super(possiblePositions);
        listFactories.add(unitFactory1);
        listFactories.addAll(List.of(unitFactories));
        for (FactoryProbability var : listFactories) {
            var.factory().setPossiblePositions(null);
        }
    }


    @Override
    public Unit getUnit() {
        int sum = getSum();
        int prob = random.nextInt(sum);
        AbstractFactory factory;
        int currBound = 0;
        for (FactoryProbability var : listFactories) {
            currBound += var.probability();
            if (prob < currBound) {
                var unit = var.factory().getUnit();
                unit.moveTo(generatePosition());
                return unit;
            }
        }
        throw new RuntimeException("Unreachable code");
    }


    @Override
    public char getAliveChar() {
        return 0;
    }

    private int getSum() {
        return listFactories.stream().map(FactoryProbability::probability).mapToInt(Integer::intValue)
                .sum();
    }
}