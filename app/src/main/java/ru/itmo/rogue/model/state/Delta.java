package ru.itmo.rogue.model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Delta {
    private final State.Focus focus = State.Focus.LEVEL;
    private final List<UnitUpdate> unitUpdates = new ArrayList<>();
    private final List<UnitPositionUpdate> inventoryChanges = new ArrayList<>();
    // not null only at the new level
    private Map map = null;
    public void add(UnitUpdate unitUpdate) {
        this.unitUpdates.add(unitUpdate);
    }

    public void add(UnitPositionUpdate inventoryChange) {
        this.inventoryChanges.add(inventoryChange);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }
    public void append(Delta that) {
        unitUpdates.addAll(that.unitUpdates);
        inventoryChanges.addAll(that.inventoryChanges);
    }

    public List<UnitUpdate> getUnitChanges() {
        return this.unitUpdates;
    }

    public List<UnitPositionUpdate> getInventoryChanges() {
        return this.inventoryChanges;
    }

    public State.Focus getFocus() {
        return focus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delta delta = (Delta) o;
        return Objects.equals(unitUpdates, delta.unitUpdates) && Objects.equals(inventoryChanges, delta.inventoryChanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitUpdates, inventoryChanges);
    }
}
