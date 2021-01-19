package me.steven.mobsmainmenu.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.Difficulty;

public class DummyClientWorld extends ClientWorld {

    private static DummyClientWorld instance;

    public static DummyClientWorld getInstance() {
        if (instance == null) instance = new DummyClientWorld();
        return instance;
    }

    private DummyClientWorld() {
        super(DummyClientPlayNetworkHandler.getInstance(), new Properties(Difficulty.EASY, false, true), null, DummyDimensionType.getInstance(), 0, () -> null, null, false, 0L);
    }
}
