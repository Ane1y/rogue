package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.game.unit.*;

import java.util.ArrayList;
import java.util.List;

public class State {
    public Focus focus = Focus.GAME;
    public final Unit player = UnitFactory.getPlayerUnit();
    // player is the first
    public final List<Unit> units = new ArrayList<>();
    public boolean running = true;
    public Judge rdj = new JustJudge();
    public Map.MapTile doorOut = Map.MapTile.DOOR_IN;
    public Map levelMap;

    public enum Focus {
        GAME, LEVEL, INVENTORY
    }

    public Unit getUnitOnPosition(Position position) {
        if (player.getPosition().equals(position)) {
            return player;
        }
        return units.stream()
                .filter(e -> e.getPosition().equals(position))
                .findFirst().orElse(null);
    }


    public void changeFocus() {
        if (focus == Focus.GAME) {
            return;
        }
        focus = (focus == Focus.LEVEL) ? Focus.INVENTORY : Focus.LEVEL;
    }

    public List<Unit> getUnits(){
        return units;
    }
}
