package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.Map;
import ru.itmo.rogue.model.state.State;

public class MapReplaceUpdate implements StateUpdate {

    private Map map;

    public MapReplaceUpdate(Map newMap) {
        this.map = newMap;
    }

    @Override
    public void apply(State state) {
        state.setMap(map);
    }
}
