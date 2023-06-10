package ru.itmo.rogue.model.state;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.game.unit.items.Item;

import java.util.List;

/**
 * Common class for inventory updates
 */
public record InventoryUpdate (int index, @NotNull String name, boolean focused) {

    public InventoryUpdate(int index, @NotNull String name) {
        this(index, name, false);
    }

    public static InventoryUpdate erase(int index) {
        return new InventoryUpdate(index, "", false);
    }

    public static InventoryUpdate focus(int index, @NotNull List<Item> stash) {
        return new InventoryUpdate(index, stash.get(index).getName(), true);
    }

    public static InventoryUpdate unfocus(int index, @NotNull List<Item> stash) {
        return new InventoryUpdate(index, stash.get(index).getName(), false);
    }

}
