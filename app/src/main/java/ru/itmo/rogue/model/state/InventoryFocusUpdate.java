package ru.itmo.rogue.model.state;

/**
 * Update of inventory focus
 */
public class InventoryFocusUpdate {
    private final int index;

    public InventoryFocusUpdate(int index) {
        this.index = index;
    }

    /**
     * @return Index of focused element
     */
    public int getIndex() {
        return this.index;
    }

}
