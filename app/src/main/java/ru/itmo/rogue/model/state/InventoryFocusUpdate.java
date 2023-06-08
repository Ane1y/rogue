package ru.itmo.rogue.model.state;

public class InventoryFocusUpdate {
    private int index;

    public InventoryFocusUpdate(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

}
