package ru.itmo.rogue.model.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Delta {
    private final List<UnitChange> unitChanges = new ArrayList<>();
    private final List<InventoryChange> inventoryChanges = new ArrayList<>();

    public void add(UnitChange unitChange) {
        this.unitChanges.add(unitChange);
    }

    public void add(InventoryChange inventoryChange) {
        this.inventoryChanges.add(inventoryChange);
    }

    public void append(Delta that) {
        unitChanges.addAll(that.unitChanges);
        inventoryChanges.addAll(that.inventoryChanges);
    }

    public List<UnitChange> getUnitChanges() {
        return this.unitChanges;
    }

    public List<InventoryChange> getInventoryChanges() {
        return this.inventoryChanges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delta delta = (Delta) o;
        return Objects.equals(unitChanges, delta.unitChanges) && Objects.equals(inventoryChanges, delta.inventoryChanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitChanges, inventoryChanges);
    }
}
