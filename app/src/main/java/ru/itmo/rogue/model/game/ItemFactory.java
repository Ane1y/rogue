package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.items.*;

import java.util.Random;

public class ItemFactory {
    private final Random random = new Random();

    public Item getItem() {
        int rand = random.nextInt(10);
        if (rand < 2) {
            return new BombStone();
        } else if (rand < 6) {
            return new HealthStone();
        } else if (rand < 8) {
            return new StrengthStone();
        } else {
            return new ConfusionSpell();
        }

    }
    public static Item getPoison() {
        return new PoisonStone();
    }
}
