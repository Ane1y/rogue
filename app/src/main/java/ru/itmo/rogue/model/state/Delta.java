package ru.itmo.rogue.model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model changes that should be reflected in the View
 */
public class Delta {
    private State.Focus focus;
    private List<UnitUpdate> unitUpdates;
    private List<InventoryFocusUpdate> inventoryChanges;
    private Map map = null;

    /**
     * Adds new UnitUpdate to the Delta
     * @param unitUpdate update of unit's statistics or position
     */
    public void add(UnitUpdate unitUpdate) {
        if (unitUpdates == null) {
            unitUpdates = new ArrayList<>();
        }
        unitUpdates.add(unitUpdate);
    }

    /**
     * Adds new player's inventory update to the Delta
     * @param inventoryChange inventory focus update or list update
     */
    public void add(InventoryFocusUpdate inventoryChange) {
        if (inventoryChanges == null) {
            inventoryChanges = new ArrayList<>();
        }

        inventoryChanges.add(inventoryChange);
    }

    /**
     * Sets new map
     * @param map new map to be displayed by View
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Sets new focus value
     * @param focus new focus to be reflected in view
     */
    public void setFocus(State.Focus focus) {
        this.focus = focus;
    }

    /**
     * Appends other delta to current delta
     * Changes made to the other delta are prevalent,
     * so if THAT delta contains update to the focus, it will override value of THIS delta
     * Same is true for the map
     * @param that other delta
     */
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

    /**
     * @return list of unit changes
     */
    public List<UnitUpdate> getUnitChanges() {
        if (unitUpdates == null) {
            return List.of();
        }
        return unitUpdates;
    }

    /**
     * @return list of player Inventory changes
     */
    public List<InventoryFocusUpdate> getInventoryChanges() {
        if (inventoryChanges == null) {
            return List.of();
        }
        return inventoryChanges;
    }

    /**
     * Returns map update
     * @return null if map wasn't changed,
     *          new Map object otherwise
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns update to focus
     * @return null if there was no update,
     *          actual focus value State.Focus
     */
    public State.Focus getFocus() {
        return focus;
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
        return Objects.hash(unitUpdates, inventoryChanges, focus, map);
    }
}
