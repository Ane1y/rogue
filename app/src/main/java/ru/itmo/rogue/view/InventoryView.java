package ru.itmo.rogue.view;

import ru.itmo.rogue.model.items.Item;
import ru.itmo.rogue.model.state.InventoryUpdate;
import ru.itmo.rogue.model.state.StateView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryView {

    private final LanternaView lanternaView;
    private StateView lastState;

    private int focusedItem = -1;

    public InventoryView(LanternaView lanternaView) {
        this.lanternaView = lanternaView;
    }

    public boolean update(StateView state) {
        List<InventoryUpdate> updates = new ArrayList<>();
        var stash = state.getPlayer().getStash();
        if (lastState == null && stash.size() > 0) {
            focusedItem = 0;
            for (int i = 0; i < state.getPlayer().getStash().size(); i++) {
                updates.add(new InventoryUpdate(i, stash.get(i).getName(), i == focusedItem));
            }
        } else {
            int idx = 0;
            var oldStash = getStash();
            while (idx < stash.size() &&
                    idx < oldStash.size() &&
                    stash.get(idx).equals(oldStash.get(idx))) {
                idx++;
            }

            while (idx < stash.size()) {
                updates.add(new InventoryUpdate(idx, stash.get(idx).getName(), idx == focusedItem));
                idx++;
            }

            while (idx < oldStash.size()) {
                updates.add(InventoryUpdate.erase(idx));
                idx++;
            }
        }

        lanternaView.drawInventory(updates);
        lastState = state;
        return true;
    }

    private List<Item> getStash() {
        if (lastState == null) {
            return Collections.emptyList();
        }
        return lastState.getPlayer().getStash();
    }

    public void focusDown() {
        if (focusedItem >= getStash().size() - 1) {
            return;
        }

        int previousFocusedItem = focusedItem;
        focusedItem += 1;

        List<InventoryUpdate> updates = new ArrayList<>();
        if (previousFocusedItem != -1) {
            updates.add(InventoryUpdate.unfocus(previousFocusedItem, getStash()));
        }
        updates.add(InventoryUpdate.focus(focusedItem, getStash()));

        lanternaView.drawInventory(updates);
    }

    public void focusUp() {
        int previousFocusedItem = focusedItem;
        if (focusedItem <= 0) {
            return;
        }

        focusedItem -= 1;
        // Add update to the focus:

        List<InventoryUpdate> updates = new ArrayList<>();
        updates.add(InventoryUpdate.unfocus(previousFocusedItem, getStash()));
        updates.add(InventoryUpdate.focus(focusedItem, getStash()));

        lanternaView.drawInventory(updates);
    }

    public int getFocusedItem() {
        return focusedItem;
    }



}
