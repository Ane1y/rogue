package ru.itmo.rogue.view;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.state.Statistics;

public class StatisticsView {
    private final LanternaView lanternaView;
    private Statistics lastStatistics;

    public StatisticsView(LanternaView lanternaView) {
        this.lanternaView = lanternaView;
    }
}
