package ru.itmo.rogue.model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Delta {
    private State.Focus focus;
    private List<UnitUpdate> unitUpdates;
    private List<InventoryFocusUpdate> inventoryChanges;
    // not null only at the new level
    private Map map = null;

    public void add(UnitUpdate unitUpdate) {
        if (unitUpdates == null) {
            unitUpdates = new ArrayList<>();
        }
        unitUpdates.add(unitUpdate);
    }

    public void add(InventoryFocusUpdate inventoryChange) {
        if (inventoryChanges == null) {
            inventoryChanges = new ArrayList<>();
        }

        inventoryChanges.add(inventoryChange);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void append(Delta that) {
        if (that.focus != null) {
            this.focus = that.focus;
        }

        if (that.map != null) {
            this.map = that.map;
        }

        if (that.unitUpdates != null) {
            unitUpdates.addAll(that.unitUpdates);
        }

        if (that.inventoryChanges != null) {
            inventoryChanges.addAll(that.inventoryChanges);
        }
    }

    public List<UnitUpdate> getUnitChanges() {
        if (unitUpdates == null) {
            return List.of();
        }
        return unitUpdates;
    }

    public List<InventoryFocusUpdate> getInventoryChanges() {
        if (inventoryChanges == null) {
            return List.of();
        }
        return inventoryChanges;
    }

    public State.Focus getFocus() {
        return focus;
    }

    public void setFocus(State.Focus focus) {
        this.focus = focus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delta delta = (Delta) o;
        return Objects.equals(focus, delta.focus) &&
                Objects.equals(map, delta.map) &&
                Objects.equals(unitUpdates, delta.unitUpdates) &&
                Objects.equals(inventoryChanges, delta.inventoryChanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitUpdates, inventoryChanges);
    }
}
