package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Position;

import java.util.List;

public abstract class Factories {
    public static UnitFactory defaultRoom(int difficulty) {
        return new CompositeFactory() {{
            put(new AggressiveFactory(difficulty), 3);
            put(new CowardFactory(difficulty), 3);
            put(new SlimeFactory(), 1);
        }};
    }

    public static UnitFactory treasury() {
        return new CompositeFactory() {{
           put(new ChestFactory(), 12);
           put(new AggressiveFactory(0), 6); // Should generate Dead enemies
           put(new SlimeFactory(), 1);
        }};
    }

    public static UnitFactory hardRoom(int difficulty) {
        return new CompositeFactory() {{
            put(new ChestFactory(), 1);
            put(new AggressiveFactory(difficulty), 10);
            put(new CowardFactory(difficulty), 2);
        }};
    }

}
