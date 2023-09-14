package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.model.*;
import com.github.alexthe666.alexsmobs.entity.*;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.item.ItemTabIcon;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AMItemstackRenderer extends BlockEntityWithoutLevelRenderer {

    public static int ticksExisted = 0;
    private static final ModelShieldOfTheDeep SHIELD_OF_THE_DEEP_MODEL = new ModelShieldOfTheDeep();
    private static final ResourceLocation SHIELD_OF_THE_DEEP_TEXTURE = new ResourceLocation("alexsmobs:textures/armor/shield_of_the_deep.png");

    private Map<String, Entity> renderedEntites = new HashMap();
    private List<EntityType> blockedRenderEntities = new ArrayList<>();

    public AMItemstackRenderer() {
        super(null, null);
    }

    public static void incrementTick() {
        ticksExisted++;
    }

    private static float getScaleFor(EntityType type, List<Pair<EntityType, Float>> mobIcons) {
        for (Pair<EntityType, Float> pair : mobIcons) {
            if (pair.getFirst() == type) {
                return pair.getSecond();
            }
        }
        return 1.0F;
    }

    public static void drawEntityOnScreen(PoseStack matrixstack, int posX, int posY, float scale, boolean follow, double xRot, double yRot, double zRot, float mouseX, float mouseY, Entity entity) {
        float f = (float) Math.atan(-mouseX / 40.0F);
        float f1 = (float) Math.atan(mouseY / 40.0F);
        matrixstack.scale(scale, scale, scale);
        entity.setOnGround(false);
        float partialTicks = Minecraft.getInstance().getFrameTime();
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(20.0F);
        float partialTicksForRender = Minecraft.getInstance().isPaused() ? 0 : partialTicks;
        int tick;
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().isPaused()) {
            tick = ticksExisted;
        } else {
            tick = Minecraft.getInstance().player.tickCount;
        }
        if (follow) {
            float yaw = f * 45.0F;
            entity.setYRot(yaw);
            entity.tickCount = tick;
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).yBodyRot = yaw;
                ((LivingEntity) entity).yBodyRotO = yaw;
                ((LivingEntity) entity).yHeadRot = yaw;
                ((LivingEntity) entity).yHeadRotO = yaw;
            }

            quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
            quaternion.mul(quaternion1);
        }

        matrixstack.mulPose(quaternion);
        matrixstack.mulPose(Vector3f.XP.rotationDegrees((float) (-xRot)));
        matrixstack.mulPose(Vector3f.YP.rotationDegrees((float) yRot));
        matrixstack.mulPose(Vector3f.ZP.rotationDegrees((float) zRot));
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicksForRender, matrixstack, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        entity.setYRot(0.0F);
        entity.setXRot(0.0F);
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).yBodyRot = 0.0F;
            ((LivingEntity) entity).yHeadRotO = 0.0F;
            ((LivingEntity) entity).yHeadRot = 0.0F;
        }
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        int tick;
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().isPaused()) {
            tick = ticksExisted;
        } else {
            tick = Minecraft.getInstance().player.tickCount;
        }
        if (itemStackIn.getItem() == AMItemRegistry.SHIELD_OF_THE_DEEP.get()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.4F, -0.75F, 0.5F);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-180));
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(SHIELD_OF_THE_DEEP_TEXTURE), false, itemStackIn.hasFoil());
            SHIELD_OF_THE_DEEP_MODEL.renderToBuffer(matrixStackIn, vertexconsumer, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.popPose();
        }
        if (itemStackIn.getItem() == AMItemRegistry.FALCONRY_GLOVE.get()) {
            matrixStackIn.translate(0.5F, 0.5f, 0.5f);
            if (transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
                Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(AMItemRegistry.FALCONRY_GLOVE_HAND.get()), transformType, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
            } else {
                Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(AMItemRegistry.FALCONRY_GLOVE_INVENTORY.get()), transformType, transformType == ItemTransforms.TransformType.GROUND ? combinedLightIn : 240, combinedOverlayIn, matrixStackIn, bufferIn, 0);
            }
        }

        if (itemStackIn.getItem() == AMItemRegistry.TAB_ICON.get()) {
            Entity fakeEntity = null;
            List<Pair<EntityType, Float>> mobIcons = AMMobIcons.getMobIcons();
            int entityIndex = (tick / 40) % (mobIcons.size());
            float scale = 1.0F;
            int flags = 0;
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                if (ItemTabIcon.hasCustomEntityDisplay(itemStackIn)) {
                    flags = itemStackIn.getTag().getInt("DisplayMobFlags");
                    String index = ItemTabIcon.getCustomDisplayEntityString(itemStackIn);
                    EntityType local = ItemTabIcon.getEntityType(itemStackIn.getTag());
                    scale = getScaleFor(local, mobIcons);
                    if (itemStackIn.getTag().getFloat("DisplayMobScale") > 0) {
                        scale = itemStackIn.getTag().getFloat("DisplayMobScale");
                    }
                    if (this.renderedEntites.get(index) == null && !blockedRenderEntities.contains(local)) {
                        try {
                            Entity entity = local.create(level);
                            if (entity instanceof EntityBlobfish) {
                                ((EntityBlobfish) entity).setDepressurized(true);
                            }
                            this.renderedEntites.put(local.getDescriptionId(), entity);
                            fakeEntity = entity;
                        } catch (Exception e) {
                            blockedRenderEntities.add(local);
                            AlexsMobs.LOGGER.error("Could not render item for entity: " + local);
                        }
                    } else {
                        fakeEntity = this.renderedEntites.get(local.getDescriptionId());
                    }
                } else {
                    EntityType type = mobIcons.get(entityIndex).getFirst();
                    scale = mobIcons.get(entityIndex).getSecond();
                    if (type != null) {
                        if (this.renderedEntites.get(type.getDescriptionId()) == null && !blockedRenderEntities.contains(type)) {
                            try {
                                Entity entity = type.create(level);
                                if (entity instanceof EntityBlobfish) {
                                    ((EntityBlobfish) entity).setDepressurized(true);
                                }
                                this.renderedEntites.put(type.getDescriptionId(), entity);
                                fakeEntity = entity;
                            } catch (Exception e) {
                                blockedRenderEntities.add(type);
                                AlexsMobs.LOGGER.error("Could not render item for entity: " + type);
                            }
                        } else {
                            fakeEntity = this.renderedEntites.get(type.getDescriptionId());
                        }
                    }
                }
            }

            if (fakeEntity instanceof EntityElephant) {
                if (flags == 99) {
                    ((EntityElephant) fakeEntity).setTusked(true);
                    ((EntityElephant) fakeEntity).setColor(null);
                } else if (flags == 98) {
                    ((EntityElephant) fakeEntity).setTusked(false);
                    ((EntityElephant) fakeEntity).setColor(DyeColor.BROWN);
                } else {
                    ((EntityElephant) fakeEntity).setTusked(false);
                    ((EntityElephant) fakeEntity).setColor(null);
                }
            }
            if (fakeEntity instanceof EntityBaldEagle) {
                if (flags == 98) {
                    ((EntityBaldEagle) fakeEntity).setCap(true);
                } else {
                    ((EntityBaldEagle) fakeEntity).setCap(false);
                }
            }
            if (fakeEntity instanceof EntityLaviathan) {
                RenderLaviathan.renderWithoutShaking = true;
                matrixStackIn.translate(0, 0.3F, 0);
            }
            if (fakeEntity instanceof EntityGiantSquid) {
                matrixStackIn.translate(0, 0.5F, 0.3F);
            }
            if (fakeEntity != null) {
                MouseHandler mouseHelper = Minecraft.getInstance().mouseHandler;
                double mouseX = (mouseHelper.xpos() * (double) Minecraft.getInstance().getWindow().getGuiScaledWidth()) / (double) Minecraft.getInstance().getWindow().getScreenWidth();
                double mouseY = mouseHelper.ypos() * (double) Minecraft.getInstance().getWindow().getGuiScaledHeight() / (double) Minecraft.getInstance().getWindow().getScreenHeight();
                matrixStackIn.translate(0.5F, 0F, 0);
                matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180F));
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180F));
                if (transformType != ItemTransforms.TransformType.GUI) {
                    mouseX = 0;
                    mouseY = 0;
                }
                try {
                    drawEntityOnScreen(matrixStackIn, 0, 0, scale, true, 0, -45, 0, (float) mouseX, (float) mouseY, fakeEntity);
                } catch (Exception e) {

                }
            }
            if (fakeEntity instanceof EntityLaviathan) {
                RenderLaviathan.renderWithoutShaking = false;
            }
        }
    }

}
