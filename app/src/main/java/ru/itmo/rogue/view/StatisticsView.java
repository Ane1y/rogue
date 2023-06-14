package ru.itmo.rogue.view;

import ru.itmo.rogue.model.state.StateView;

public class StatisticsView {
    private final LanternaView lanternaView;

    public StatisticsView(LanternaView lanternaView) {
        this.lanternaView = lanternaView;
    }

    boolean update(StateView state) {
        var statistics = state.getStatistics();
        lanternaView.drawStatistics(statistics);
        return true;
    }

}
