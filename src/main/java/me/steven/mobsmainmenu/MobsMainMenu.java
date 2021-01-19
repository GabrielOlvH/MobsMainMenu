package me.steven.mobsmainmenu;

import me.steven.mobsmainmenu.client.DummyClientWorld;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MobsMainMenu implements ClientModInitializer {

    public static LivingEntity livingEntity = null;
    private static final Random RANDOM = new Random();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (livingEntity == null) {
                List<EntityType<?>> collect = Registry.ENTITY_TYPE.stream()
                        .filter((e) -> e.getSpawnGroup() != SpawnGroup.MISC
                                && !e.equals(EntityType.WITHER)
                                && !e.equals(EntityType.ENDER_DRAGON)
                        ).collect(Collectors.toList());
                Entity entity = collect.get(RANDOM.nextInt(collect.size())).create(DummyClientWorld.getInstance());
                if (entity instanceof LivingEntity) livingEntity = (LivingEntity) entity;
            }
        });
    }
}
