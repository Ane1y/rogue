package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.items.Item;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class AddToStashUpdate extends UnitUpdate {

    private final Item item;

    private final static int STASH_MAX_SIZE = 30;

    public AddToStashUpdate(UnitView unit, Item item) {
        super(unit);
        this.item = item;
    }

    @Override
    public void userApply(State state) {
        var stash = state.getUnitWithView(view).getStash();
        var size = stash.size();
        if (size == 0) {
            stash.add(item);
        } else if (size < STASH_MAX_SIZE) {
            stash.add(size - 1, item);
        }
    }
}
