package me.steven.mobsmainmenu;

import com.mojang.blaze3d.systems.RenderSystem;
import me.steven.mobsmainmenu.client.DummyClientWorld;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.Quaternion;
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
                        ).collect(Collectors.toList());
                Entity entity = collect.get(RANDOM.nextInt(collect.size())).create(DummyClientWorld.getInstance());
                if (entity instanceof LivingEntity) livingEntity = (LivingEntity) entity;
            }
        });
    }

    public static void renderEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float)Math.atan(mouseX / 40.0F);
        float g = (float)Math.atan(mouseY / 40.0F);
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)x, (float)y, 1050.0F);
        RenderSystem.scalef(1.0F, 1.0F, -1.0F);
        MatrixStack matrices = new MatrixStack();
        matrices.translate(0.0D, 0.0D, 1000.0D);
        matrices.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vector3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        quaternion.hamiltonProduct(quaternion2);
        matrices.multiply(quaternion);
        float h = entity.bodyYaw;
        float i = entity.yaw;
        float j = entity.pitch;
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.bodyYaw = 180.0F + f * 20.0F;
        entity.yaw = 180.0F + f * 40.0F;
        entity.pitch = -g * 20.0F;
        entity.headYaw = entity.yaw;
        entity.prevHeadYaw = entity.yaw;
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> {
            double width = entity.getBoundingBox().getXLength();
            double height = entity.getBoundingBox().getYLength();
            if(width > 0.6) {
                width *= 1f/((float)width/0.6f);
                height = entity.getBoundingBox().getYLength()*(width/entity.getBoundingBox().getXLength());
            }
            if(height > 2.0) {
                width *= 1f/(height/2f);
            }
            matrices.scale((float) (width/entity.getBoundingBox().getXLength()), (float) (width/entity.getBoundingBox().getXLength()), (float) (width/entity.getBoundingBox().getXLength()));
            entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrices, immediate, 15728880);
        });
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.bodyYaw = h;
        entity.yaw = i;
        entity.pitch = j;
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        RenderSystem.popMatrix();
    }
}
