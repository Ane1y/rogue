package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Control;
import ru.itmo.rogue.model.game.unit.Item;

public class InventoryLogic extends AbstractModel<Control.Signal, State.Delta> {

    private State state;
    private Item[] playerStash;
    private int focusedItem;

    public InventoryLogic(State state) {
        this.state = state;
        this.playerStash = state.player.getStash();
    }

    @Override
    public boolean update(Control.Signal data) {
        return false;
    }
}
