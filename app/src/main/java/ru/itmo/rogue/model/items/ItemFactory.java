package ru.itmo.rogue.model.items;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

import ru.itmo.rogue.model.items.*;

public class ItemFactory {

    private static class ItemVariant {

        ItemVariant(Class<? extends Item> itemClass) {
            this(itemClass, null);
        }

        ItemVariant(Class<? extends Item> itemClass, Integer param) {
            this.itemClass = itemClass;
            this.param = param;
        }

        Class<? extends Item> itemClass;
        int param;
    }
    private final Random random = new Random();

    private final Map<ItemVariant, Integer /* probability */> itemsVariants = Map.of(
            new ItemVariant(BombStone.class, 3), 2,
            new ItemVariant(HealthStone.class, 1), 5,
            new ItemVariant(HealthStone.class, 2), 10,
            new ItemVariant(StrengthStone.class, 1), 1,
            new ItemVariant(ConfusionSpell.class, 3), 3,
            new ItemVariant(ConfusionSpell.class, 5), 2
    );

    public Item getItem() {
        int upperBound = itemsVariants.values().stream().mapToInt(Integer::intValue).sum();
        int rand = random.nextInt(upperBound);

        int currBound = 0;
        for (var entry : itemsVariants.entrySet()) {
            currBound += entry.getValue();
            if (rand < currBound) {
                var clazz = entry.getKey().itemClass;
                var param = entry.getKey().param;

                return constructItem(clazz, param);
            }
        }

        throw new RuntimeException("Unreachable code");
    }

    private Item constructItem(Class<? extends Item> clazz, Integer param) {
        try {
            if (param == null) {
                var constructor = clazz.getDeclaredConstructor();
                return constructor.newInstance();
            }

            var constructor = clazz.getDeclaredConstructor(int.class);
            return constructor.newInstance(param);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException("Item doesn't have expected constructor", e);
        } catch (InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Exception was thrown during item's instantiation", e);
        }
    }

    public static Item getPoison() {
        return new PoisonStone();
    }
}
