package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.ItemFactory;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.InventoryFocusUpdate;
import ru.itmo.rogue.model.state.InventoryItemUpdate;
import ru.itmo.rogue.model.state.State;


/**
 * Class that contains Inventory Logic that is active when Inventory is in focus, or when called by game logic
 * Responsible for transferring items between different Units and generating deltas if this changes affect the player
 * Class have control over the State of the game (as all Logic classes)
 */
public class InventoryLogic {

    private final State state;
    private final Unit trackedUnit;
    private int focusedItem = 0;

    /**
     * @param state state to control
     * @param trackedUnit unit to track (player)
     */
    public InventoryLogic(State state, Unit trackedUnit) {
        this.state = state;
        this.trackedUnit = trackedUnit;

        trackedUnit.getStash().add(ItemFactory.getPoison());
    }

    /**
     * Updates game state, changes item selection or applies item depending on the input
     * @param signal Controller's signal
     * @return delta that represents changes made by the method
     */
    public Delta update(Signal signal) {
        var delta = new Delta();
        switch (signal) {
            case UP -> {
                if (focusedItem > 0) {
                    focusedItem -= 1;
                }
            }
            case DOWN -> {
                if (focusedItem < trackedUnit.getStash().size() - 1) {
                    focusedItem += 1;
                }
            }
            case SELECT -> {
                var stash = trackedUnit.getStash();
                var item = stash.get(focusedItem);
                delta.append(item.apply(trackedUnit, state));
                stash.remove(focusedItem);

                // Include information for list update
                for (int i = focusedItem; i < stash.size(); i++) {
                    delta.add(new InventoryItemUpdate(i, stash.get(i).getName()));
                }

                if (focusedItem >= stash.size()) {
                    focusedItem = stash.size() - 1;
                }
            }
        }

        delta.add(new InventoryFocusUpdate(focusedItem));
        return delta;
    }

    /**
     * Transfers items from one stash to another.
     * If One of the units is the tracked unit, generates non-empty delta
     * @param from unit that will give items
     * @param to unit that will take items
     * @return Delta
     */
    public Delta transferItems(Unit from, Unit to) {
        var delta = new Delta();
        var destStash = to.getStash();
        if (to != trackedUnit) {
            destStash.addAll(from.getStash());
            return delta;
        }

        // Add all items before last one
        var index = destStash.size() > 0 ? destStash.size() - 1 : 0;
        destStash.addAll(index, from.getStash());

        for (int i = index; i < destStash.size(); i++) {
            delta.add(new InventoryItemUpdate(i, destStash.get(i).getName()));
        }

        return delta;
    }

}
