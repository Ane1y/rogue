package ru.itmo.rogue.view;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;

import java.util.List;

public record UnitsDelta(@NotNull List<Position> toErase, @NotNull List<UnitView> toDraw) {}
