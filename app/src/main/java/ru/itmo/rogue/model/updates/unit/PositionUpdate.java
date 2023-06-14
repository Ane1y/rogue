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
    public void userApply(State state) {
        if (!state.getMap().isPositionInbound(position)) {
            return;
        }

        var map = state.getMap();
        boolean isWall = map.isWall(position);
        boolean isDoor = map.isExit(position);
        if (isWall || (isDoor && state.doorsClosed())) { // Wall or closed door
            return;
        }

        var enemy = state.getUnitWithPosition(position);
        if (enemy != null && enemy != view) { // Fight
            if (!enemy.isDead()) {
                new AttackUpdate(view, enemy).apply(state);
                return;
            }

            new StashTransferUpdate(enemy, view).apply(state);
        }

        var unit = state.getUnitWithView(view);
        unit.moveTo(position);
    }

}
