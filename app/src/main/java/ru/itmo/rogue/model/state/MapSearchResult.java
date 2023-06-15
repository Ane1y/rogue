package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.unit.Position;
import java.util.List;

public record MapSearchResult(List<Position> path, List<Position> reachableFloors, List<Position> reachableWalls) {}
