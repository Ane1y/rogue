package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.items.*;

import java.util.Random;

public class ItemFactory {
    private final Random random = new Random();

    public Item getItem()
    {
        int rand = random.nextInt();
        if(rand == 0)
        {
            return new BombStone();
        }
        else if(rand == 1) {
            return new HealthStone();
        }
            else{
                return new StrengthStone();
            }

    }
    public Item getPoison(){
        return new PoisonStone();
    }
}
