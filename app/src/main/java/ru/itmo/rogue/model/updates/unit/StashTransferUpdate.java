package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.UnitUpdate;


/**
 * Takes items in stash of one unit and transfers them to the other unit
 * If `destination` unit is dead before the application, transfer won't happen
 */
public class StashTransferUpdate extends UnitUpdate {
    private final UnitView sourceView;

    public StashTransferUpdate(UnitView destination, UnitView source) {
        super(destination);
        this.sourceView = source;
    }

    @Override
    public void userApply(State state) {
        var source = state.getUnitWithView(sourceView);
        var destination = state.getUnitWithView(view);

        source.getStash().forEach(destination::addItem);
        source.removeAllItems();

        destination.increaseExperience(source.getExperience());
        source.wipeExperience();
    }
}
