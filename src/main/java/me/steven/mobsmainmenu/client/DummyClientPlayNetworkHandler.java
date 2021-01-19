package me.steven.mobsmainmenu.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;

public class DummyClientPlayNetworkHandler extends ClientPlayNetworkHandler {

    private static DummyClientPlayNetworkHandler instance;

    public static DummyClientPlayNetworkHandler getInstance() {
        if (instance == null) instance = new DummyClientPlayNetworkHandler();
        return instance;
    }

    private DummyClientPlayNetworkHandler() {
        super(MinecraftClient.getInstance(), null, new ClientConnection(NetworkSide.CLIENTBOUND), MinecraftClient.getInstance().getSession().getProfile());
    }
}
