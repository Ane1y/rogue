package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public class BombStone implements Item {
    private final String name = "Bomb";
    private final int change;

    public BombStone() {
        this(1);
    };

    public BombStone(int change) {
        this.change = -change;
    }

    @Override
    public Delta apply(Unit unit, State state) {
        Delta delta = new Delta();
        for (Unit enemy : state.getUnits()) {
            if (enemy != unit) {
                delta.add(enemy.changeHealth(change));
            }
        }
        return delta;
    }

    @Override
    public String getName() {
        return name;
    }
}
