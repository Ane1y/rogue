package ru.itmo.rogue.model;

public interface UnitStrategy {

    public Action getAction(Unit unit, State state);

}
