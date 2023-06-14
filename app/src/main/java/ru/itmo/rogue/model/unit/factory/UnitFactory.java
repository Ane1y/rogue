package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;

import java.util.List;

public interface UnitFactory {

    /**
     * Allows to provide positions for unit factory to generate units
     */
    void setPositions(List<Position> positions);


    Unit getUnit();

}
