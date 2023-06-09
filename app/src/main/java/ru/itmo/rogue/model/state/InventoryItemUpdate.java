package ru.itmo.rogue.model.state;

/**
 * Contains update to inventory point
 * (Actual focus change is not a MUST)
 */
public class InventoryItemUpdate extends InventoryFocusUpdate {

    private final String name;

    public InventoryItemUpdate(int index, String name) {
        super(index);
        this.name = name;
    }

    /**
     * @return string that should be displayed in focused position
     */
    public String getName() {
        return this.name;
    }

}
