package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.unit.items.Item;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

import java.util.ArrayList;
import java.util.List;

public class InventoryLogic {

    private State state = new State();
    private List<Item> playerStash = new ArrayList<>();
    private int focusedItem = 0;

    public InventoryLogic(State state) {
        this.state = state;
        this.playerStash = state.getPlayer().getStash();
    }

    public Delta update(Signal data) {
        return null;
    }
}
