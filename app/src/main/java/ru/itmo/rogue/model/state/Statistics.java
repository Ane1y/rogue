package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.Unit;

public record Statistics (
        int previousRoomRecord,
        int previousLevelRecord,
        int currentRoom,
        boolean doorsOpen,
        Unit trackedUnit
) {

    public Statistics doorsOpened() {
        return new Statistics(previousRoomRecord, previousLevelRecord, currentRoom, true, trackedUnit);
    }

    public Statistics nextRoom() {
        return new Statistics(previousRoomRecord, previousLevelRecord, currentRoom + 1, false, trackedUnit);
    }

}
