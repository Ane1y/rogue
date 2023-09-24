package ru.itmo.rogue.view;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;

import java.util.List;

/**
 * Difference in units position between old and new states, represented as:
 * @param toErase list of map coordinates that should be erased on the screen (replaced with background)
 * @param toDraw list of units that should be drawn onto the map
 */
public record UnitsDelta(@NotNull List<Position> toErase, @NotNull List<UnitView> toDraw) {}
