package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.*;

/**
 * Update to unit's position
 * [It's the most complex update on the list]
 * This update, will TRY to move unit to a certain position
 * Move can fail in 2 cases:
 *   1) There's a wall or a closed door, NOTHING happens
 *   2) There's an alive unit, FIGHT will be triggered (via AttackUpdate)
 * If there's a dead unit its Items and Experience will be collected (via StashTransferUpdate)
 */
public class PositionUpdate extends UnitUpdate {

    private final Position position;

    public PositionUpdate(UnitView unit, Position position) {
        super(unit);
        this.position = position;
    }

    @Override
    public void apply(State state) {
        if (state.getMap().isWall(position)) { // Wall
            return;
        }

        var enemy = state.getUnitWithPosition(position);
        if (enemy != null && enemy != view) { // Fight
            if (!enemy.isDead()) {
                new AttackUpdate(view, enemy);
                return;
            }

            new StashTransferUpdate(view, enemy).apply(state);
        }

        var unit = state.getUnitWithView(view);
        unit.moveTo(position);
    }

}
