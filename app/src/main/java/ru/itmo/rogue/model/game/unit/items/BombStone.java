package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class BombStone implements Item{
    private final String name = "BombStone";

    int change = -1;

    public BombStone(int change){
        this.change = change;
    }
    @Override
    public void apply(Unit unit, State state) {
        for(Unit enemy: state.getEnemies()){
            enemy.changeHealth(change);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
