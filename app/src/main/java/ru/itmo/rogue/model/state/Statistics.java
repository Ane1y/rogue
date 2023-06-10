package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

public record Statistics (
        int highestRoomCount,
        int currentRoom,
        int highestLevel,
        int currentLevel,
        Unit trackedUnit
) {

    public Statistics nextLevel() {
        return setLevel(currentLevel + 1);
    }

    public Statistics nextRoom() {
        var room = currentRoom + 1;
        if (room > highestRoomCount) {
            return new Statistics(room, room, highestLevel, currentLevel, trackedUnit);
        } else {
            return new Statistics(highestRoomCount, room, highestLevel, currentLevel, trackedUnit);
        }
    }

    public Statistics setLevel(int level) {
        if (level > highestLevel) {
            return new Statistics(highestRoomCount, currentRoom, level, level, trackedUnit);
        } else {
            return new Statistics(highestRoomCount, currentRoom, highestLevel, level, trackedUnit);
        }
    }

}
