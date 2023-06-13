package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.UnitFactory;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that contains current state of the Game
 */
public class State {
    private final Unit player;
    private final List<Unit> units = new ArrayList<>();
    private Map levelMap;
    private Statistics statistics;
    private boolean running = true;

    public State() {
        this(0, 0);
    }

    public State(int previousRoomRecord, int previousLevelRecord) {
        player = UnitFactory.getPlayerUnit();
        statistics = new Statistics(previousLevelRecord, previousRoomRecord, 0, false, player);
    }

    /**
     * @return player unit
     */
    public Unit getPlayer() {
        return player;
    }

    /**
     * @return Immutable list of all units (including player)
     */
    public List<Unit> getUnits(){
        return Collections.unmodifiableList(units);
    }

    /**
     * Returns unit placed on position
     * @param position position to check
     * @return unit if it placed, null if there's no unit on position
     */
    public Unit getUnitByPosition(Position position) {
        return units.stream()
                .filter(e -> e.getPosition().equals(position))
                .findFirst().orElse(null);
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

    public void setStatistics(Statistics statistics) {
        return; // TODO: Create proper delta
    }

    /**
     * Adds unit to the current state
     * @param unit unit to be added
     */
    protected void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * Sets map
     * @param newMap map to be set
     */
    public void setMap(Map newMap) {
        statistics = statistics.nextRoom();

        levelMap = newMap;
        units.clear();
        units.add(player);
        player.moveTo(levelMap.getEntrance()); // Move player to an entrance
    }
}
