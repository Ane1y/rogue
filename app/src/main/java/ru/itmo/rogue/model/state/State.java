package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.game.unit.*;

import java.util.ArrayList;
import java.util.List;

public class State {
    public Focus focus = Focus.GAME;
    public final Unit player = UnitFactory.getPlayerUnit();
    public List<Unit> enemies = new ArrayList<>();
    public boolean running = true;

    public Map map;

    public enum Focus {
        GAME, LEVEL, INVENTORY
    }

}
