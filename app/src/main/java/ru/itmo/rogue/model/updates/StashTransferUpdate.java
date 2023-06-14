package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;

public class StashTransferUpdate implements StateUpdate {

    private final UnitView sourceView;
    private final UnitView destinationView;

    public StashTransferUpdate(UnitView source, UnitView destination) {
        this.sourceView = source;
        this.destinationView = destination;
    }

    @Override
    public void apply(State state) {
        var source = state.getUnitWithView(sourceView);
        var destination = state.getUnitWithView(destinationView);

        source.getStash().forEach(destination::addItem);
        source.removeAllItems();

        destination.increaseExperience(source.getExperience());
        source.wipeExperience();
    }
}
