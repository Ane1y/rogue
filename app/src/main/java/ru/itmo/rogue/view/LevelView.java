package ru.itmo.rogue.view;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;

import java.util.List;

public class LevelView {
    private StateView lastState;
    private final LanternaView lanternaView;

    public LevelView(LanternaView lanternaView) {
        this.lanternaView = lanternaView;
    }

    public boolean update(@NotNull StateView state) {
        if (lastState == null || lastState.getMap() != state.getMap()) {
            lanternaView.drawMap(state.getMap());
        }

        var delta = computeDelta(state);
        lanternaView.drawUnitDelta(delta);

        lastState = state;
        return true;
    }

    private UnitsDelta computeDelta(@NotNull StateView stateView) {
        if (lastState == null) {
            return new UnitsDelta(List.of(), stateView.getUnits());
        }

        List<Position> erase = lastState.getUnits()
                .stream()
                .filter(unit -> !stateView.getUnits().contains(unit))
                .map(UnitView::getPosition)
                .toList();

        List<UnitView> draw = stateView.getUnits()
                .stream()
                .filter(unit -> !lastState.getUnits().contains(unit))
                .toList();

        return new UnitsDelta(erase, draw);
    }

}
