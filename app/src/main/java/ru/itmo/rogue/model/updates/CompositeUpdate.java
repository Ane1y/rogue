package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;

import java.util.*;
import java.util.stream.Collector;

public class CompositeUpdate implements StateUpdate {
    private final List<StateUpdate> updates;

    public CompositeUpdate(StateUpdate... updates) {
        this.updates = Arrays.asList(updates);
    }

    public CompositeUpdate(List<StateUpdate> updates) {
        this.updates = updates;
    }

    @Override
    public void apply(State state) {
        updates.forEach(update -> update.apply(state));
    }

    public static Collector<StateUpdate, ?, CompositeUpdate> collector() {
        return Collector.of(
                ArrayList<StateUpdate>::new,
                List::add,
                (list, newList) -> { list.addAll(newList); return list; },
                CompositeUpdate::new);
    }
}
