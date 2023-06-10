package ru.itmo.rogue.model.state;

/**
 * Update of inventory focus
 */
public class InventoryFocusUpdate extends InventoryUpdate {
    private final int index;

    public InventoryFocusUpdate(int index) {
        assert index >= -1;
        this.index = index;
    }

    /**
     * Item index to focus
     * @return -1 to remove any focus, > -1 - index
     */
    public int getIndex() {
        return this.index;
    }

}
