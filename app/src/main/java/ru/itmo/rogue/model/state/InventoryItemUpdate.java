package ru.itmo.rogue.model.state;

public class InventoryItemUpdate extends InventoryFocusUpdate {

    private final String name;

    public InventoryItemUpdate(int index, String name) {
        super(index);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
