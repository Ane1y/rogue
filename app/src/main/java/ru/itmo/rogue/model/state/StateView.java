package ru.itmo.rogue.model.state;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;

import java.util.List;

public interface StateView {

    UnitView getPlayer();

    @NotNull
    List<UnitView> getUnits();

    default UnitView getUnitWithPosition(Position position) {
        return getUnits().stream()
                .filter(u -> u.getPosition().equals(position))
                .findFirst().orElse(null);
    }

    MapView getMap();

    Statistics getStatistics();

    boolean doorsClosed();

    boolean running();

    StateView copy();

}
