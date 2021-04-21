package me.steven.mobsmainmenu.mixin;

import me.steven.mobsmainmenu.MobsMainMenu;
import me.steven.mobsmainmenu.client.DummyClientPlayerEntity;
import me.steven.mobsmainmenu.client.DummyClientWorld;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void mobsMainMenu_resetEntity(CallbackInfo ci) {
        MobsMainMenu.livingEntity = null;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void mobsMainMenu_render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TitleScreen sc = (TitleScreen) (Object) this;
        if (MinecraftClient.getInstance() != null) {
            ClientPlayerEntity player = DummyClientPlayerEntity.getInstance();
            int height = sc.height / 4 + 132;
            int playerX = sc.width / 2 - 160;
            InventoryScreen.drawEntity(playerX, height, 30, -mouseX + playerX, -mouseY + height - 30, player);
            int entityX = sc.width / 2 + 160;
            LivingEntity livingEntity = MobsMainMenu.livingEntity;
            if (livingEntity != null) {
                try {
                    MobsMainMenu.renderEntity(entityX, height, 30, -mouseX + entityX, -mouseY + height - 30, livingEntity);
                } catch (Exception e) {
                    MobsMainMenu.livingEntity = null;
                }
            }
        }
    }

}
