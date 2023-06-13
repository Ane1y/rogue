package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.UnitUpdate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionUpdate extends UnitUpdate {

    private final Position position;
    private final Fallback fallback;

    public PositionUpdate(UnitView unit, Position position) {
        this(unit, position, new Fallback(unit));
    }

    public PositionUpdate(UnitView unit, Position position, Fallback fallback) {
        super(unit);
        this.position = position;
        this.fallback = fallback;
    }

    @Override
    public void apply(State state) {
        if (state.getMap().isWall(position)) { // Wall
            applyList(state, fallback.wall(state));
            return;
        }

        var enemy = state.getUnitWithPosition(position);
        if (enemy != null && enemy != view) { // Fight
            if (enemy.isDead()) {
                applyList(state, fallback.stash(enemy, state));
            } else {
                applyList(state, fallback.fight(enemy, state));
            }
            return;
        }

        var unit = state.getUnitWithView(view);
        unit.moveTo(position);
    }

    private void applyList(State state, List<StateUpdate> updateList) {
        updateList.forEach(update -> update.apply(state));
    }

    /* Inherit this class to override behaviour */
    public static class Fallback {
        public Fallback(UnitView view) {
            this.view = view;
        }

        protected UnitView view;

        List<StateUpdate> fight(UnitView defender, StateView state) {
            return List.of(new HealthUpdate(defender, -view.getStrength()));
        }

        List<StateUpdate> wall(StateView stateView) {
            return List.of(); // Do nothing when met wall
        }

        List<StateUpdate> stash(UnitView other, StateView state) {
            var stash = other.getStash();

            var collectionStream = stash.stream()
                    .map(item -> new AddToStashUpdate(view, item));
            var additionalStream = Stream.of(new StashClearUpdate(other),   // Wipe other's stash
                    new ExperienceUpdate(view, other.getExperience()),      // Get other's exp
                    new WipeExperience(other));                             // Wipe other's exp

            return Stream.concat(collectionStream, additionalStream).collect(Collectors.toList());
        }
    }

}
