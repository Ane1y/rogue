package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains current state of the Game
 */
public class State {
    private Focus focus = Focus.LEVEL;
    private final Unit player;
    private final List<Unit> units = new ArrayList<>();
    private final AbstractJudge rdj = new JustJudge();
    private Map levelMap;
    private Statistics statistics;
    private boolean running = true;

    public State() {
        this(0, 0);
    }

    public State(int highestRoom, int highestLevel) {
        player = UnitFactory.getPlayerUnit();
        statistics = new Statistics(highestRoom, 0, highestLevel, 0, player);
    }

    /**
     * Focused part of the game (part that should receive commands)
     */
    public enum Focus {
        LEVEL,  // Level Logic part
        INVENTORY // Inventory Logic part
    }

    /**
     * @return current focus
     */
    public Focus getFocus() {
        return focus;
    }

    /**
     * @return player unit
     */
    public Unit getPlayer() {
        return player;
    }

    /**
     * @return list of all units
     */
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

    /**
     * @return current Judge object
     */
    public AbstractJudge getJudge() {
        return rdj;
    }

    /**
     * @return current Map
     */
    public Map getLevelMap() {
        return levelMap;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * @return state of the running flag
     */
    public boolean running() {
        return running;
    }

    /**
     * Sets running flag as False, game should stop after it
     */
    public void stop() {
        running = false;
    }

    // Setters that produce Delta

    public Delta setStatistics(Statistics statistics) {
        this.statistics = statistics;
        return null; // TODO: Create proper delta
    }

    /**
     * @param newFocus focus that should be set now
     * @return delta that reflects the change
     */
    public Delta setFocus(Focus newFocus) {
        var delta = new Delta();
        focus = newFocus;
        delta.setFocus(focus);
        return delta;
    }

    /**
     * Toggles focus (LEVEL -> INVENTORY and vise versa)
     * @return delta that reflects the change
     */
    public Delta toggleFocus() {
        var delta = new Delta();
        focus = (focus == Focus.LEVEL) ? Focus.INVENTORY : Focus.LEVEL;
        delta.setFocus(focus);
        return delta;
    }

    /**
     * Adds unit to the current state
     * @param unit unit to be added
     * @return delta that reflects the change
     */
    public Delta addUnit(Unit unit) {
        var delta = new Delta();
        delta.add(new UnitUpdate(unit));
        units.add(unit);
        return delta;
    }

    /**
     * Sets map, creates corresponding delta that includes new player position
     * @param newMap map to be set
     * @return delta that reflects the change
     */
    public Delta setMap(Map newMap) {
        statistics = statistics.nextRoom();

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
