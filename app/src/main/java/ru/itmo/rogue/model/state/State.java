package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.game.unit.*;

import java.util.ArrayList;
import java.util.List;


public class State {
    private Focus focus = Focus.LEVEL;
    private final Unit player = UnitFactory.getPlayerUnit();
    private final List<Unit> units = new ArrayList<>(); // player must be first
    private final Judge rdj = new JustJudge();
    private Map levelMap;
    private boolean running = true;

    public enum Focus {
        LEVEL, INVENTORY
    }

    public Focus getFocus() {
        return focus;
    }

    public Unit getPlayer() {
        return player;
    }

    public List<Unit> getUnits(){
        return units;
    }

    /**
     * Returns unit placed on position
     * @param position position to check
     * @return unit if it placed, null if there's no unit on position
     */
    public Unit getUnitOnPosition(Position position) {
        if (player.getPosition().equals(position)) {
            return player;
        }
        return units.stream()
                .filter(e -> e.getPosition().equals(position))
                .findFirst().orElse(null);
    }

    public Judge getJudge() {
        return rdj;
    }

    public Map getLevelMap() {
        return levelMap;
    }

    public boolean running() {
        return running;
    }

    public void stop() {
        running = false;
    }

    // Setters that produce Delta

    public Delta setFocus(Focus newFocus) {
        var delta = new Delta();
        focus = newFocus;
        delta.setFocus(focus);
        return delta;
    }

    /**
     * Toggles focus
     * @return Delta with new Focus
     */
    public Delta toggleFocus() {
        var delta = new Delta();
        focus = (focus == Focus.LEVEL) ? Focus.INVENTORY : Focus.LEVEL;
        delta.setFocus(focus);
        return delta;
    }

    public Delta addUnit(Unit unit) {
        var delta = new Delta();
        delta.add(new UnitUpdate(unit));
        units.add(unit);
        return delta;
    }

    /**
     * Sets map, creates corresponding delta that includes new player position
     * @param newMap
     * @return
     */
    public Delta setMap(Map newMap) {
        levelMap = newMap;
        units.clear();
        units.add(player);
        player.moveTo(levelMap.getEntrance()); // Move player to an entrance

        var delta = new Delta();
        delta.setMap(newMap);
        delta.add(new UnitUpdate(player));
        return delta;
    }
}
