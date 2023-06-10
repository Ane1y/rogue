package ru.itmo.rogue.model.state;

/**
 * Contains update to inventory point, DOES NOT lead to focus change
 */
public class InventoryItemUpdate extends InventoryUpdate {

    private final int index;
    private final String name;

    public InventoryItemUpdate(int index, String name) {
        assert index >= 0;
        this.index = index;
        this.name = name;
    }

    /**
     * Returns index of the element to name/rename
     * @return non-negative value
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return string that should be displayed in [index] position
     */
    public String getName() {
        return this.name;
    }

}
