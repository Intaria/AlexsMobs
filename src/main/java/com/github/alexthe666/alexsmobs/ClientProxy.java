package com.github.alexthe666.alexsmobs;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.ClientLayerRegistry;
import com.github.alexthe666.alexsmobs.client.event.ClientEvents;
import com.github.alexthe666.alexsmobs.client.particle.*;
import com.github.alexthe666.alexsmobs.client.render.*;
import com.github.alexthe666.alexsmobs.client.render.item.AMItemRenderProperties;
import com.github.alexthe666.alexsmobs.client.render.item.CustomArmorRenderProperties;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderCapsid;
import com.github.alexthe666.alexsmobs.entity.*;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.github.alexthe666.alexsmobs.inventory.AMMenuRegistry;
import com.github.alexthe666.alexsmobs.item.*;
import com.github.alexthe666.alexsmobs.tileentity.AMTileEntityRegistry;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.*;
import java.util.concurrent.Callable;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = AlexsMobs.MODID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    //public static final Map<Integer, SoundBearMusicBox> BEAR_MUSIC_BOX_SOUND_MAP = new HashMap<>();
    public static List<UUID> currentUnrenderedEntities = new ArrayList<UUID>();
    public CameraType prevPOV = CameraType.FIRST_PERSON;
    public boolean initializedRainbowBuffers = false;
    private int pupfishChunkX = 0;
    private int pupfishChunkZ = 0;
    private int singingBlueJayId = -1;
    private ItemStack[] transmuteStacks = new ItemStack[3];

    @OnlyIn(Dist.CLIENT)
    public static Callable<BlockEntityWithoutLevelRenderer> getTEISR() {
        return AMItemstackRenderer::new;
    }

    public void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientLayerRegistry::onAddLayers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::setupParticles);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::onBakingCompleted);
    }

    public void clientInit() {
        initRainbowBuffers();
        ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        EntityRenderers.register(AMEntityRegistry.ROADRUNNER.get(), RenderRoadrunner::new);
        EntityRenderers.register(AMEntityRegistry.GAZELLE.get(), RenderGazelle::new);
        EntityRenderers.register(AMEntityRegistry.CROCODILE.get(), RenderCrocodile::new);
        EntityRenderers.register(AMEntityRegistry.HUMMINGBIRD.get(), RenderHummingbird::new);
        EntityRenderers.register(AMEntityRegistry.ORCA.get(), RenderOrca::new);
        EntityRenderers.register(AMEntityRegistry.SUNBIRD.get(), RenderSunbird::new);
        EntityRenderers.register(AMEntityRegistry.HAMMERHEAD_SHARK.get(), RenderHammerheadShark::new);
        EntityRenderers.register(AMEntityRegistry.SHARK_TOOTH_ARROW.get(), RenderSharkToothArrow::new);
        EntityRenderers.register(AMEntityRegistry.LOBSTER.get(), RenderLobster::new);
        EntityRenderers.register(AMEntityRegistry.CAPUCHIN_MONKEY.get(), RenderCapuchinMonkey::new);
        EntityRenderers.register(AMEntityRegistry.TOSSED_ITEM.get(), RenderTossedItem::new);
        EntityRenderers.register(AMEntityRegistry.WARPED_TOAD.get(), RenderWarpedToad::new);
        EntityRenderers.register(AMEntityRegistry.MOOSE.get(), RenderMoose::new);
        EntityRenderers.register(AMEntityRegistry.RACCOON.get(), RenderRaccoon::new);
        EntityRenderers.register(AMEntityRegistry.BLOBFISH.get(), RenderBlobfish::new);
        EntityRenderers.register(AMEntityRegistry.SEAL.get(), RenderSeal::new);
        EntityRenderers.register(AMEntityRegistry.COCKROACH_EGG.get(), (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });

        EntityRenderers.register(AMEntityRegistry.SHOEBILL.get(), RenderShoebill::new);
        EntityRenderers.register(AMEntityRegistry.ELEPHANT.get(), RenderElephant::new);
        EntityRenderers.register(AMEntityRegistry.SOUL_VULTURE.get(), RenderSoulVulture::new);
        EntityRenderers.register(AMEntityRegistry.SNOW_LEOPARD.get(), RenderSnowLeopard::new);
        EntityRenderers.register(AMEntityRegistry.CROW.get(), RenderCrow::new);
        EntityRenderers.register(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get(), RenderAlligatorSnappingTurtle::new);
        EntityRenderers.register(AMEntityRegistry.MUNGUS.get(), RenderMungus::new);
        EntityRenderers.register(AMEntityRegistry.EMU.get(), RenderEmu::new);
        EntityRenderers.register(AMEntityRegistry.EMU_EGG.get(), (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRenderers.register(AMEntityRegistry.PLATYPUS.get(), RenderPlatypus::new);
        EntityRenderers.register(AMEntityRegistry.KANGAROO.get(), RenderKangaroo::new);
        EntityRenderers.register(AMEntityRegistry.CACHALOT_WHALE.get(), RenderCachalotWhale::new);
        EntityRenderers.register(AMEntityRegistry.CACHALOT_ECHO.get(), RenderCachalotEcho::new);
        EntityRenderers.register(AMEntityRegistry.LEAFCUTTER_ANT.get(), RenderLeafcutterAnt::new);

        EntityRenderers.register(AMEntityRegistry.BALD_EAGLE.get(), RenderBaldEagle::new);
        EntityRenderers.register(AMEntityRegistry.TIGER.get(), RenderTiger::new);
        EntityRenderers.register(AMEntityRegistry.SEAGULL.get(), RenderSeagull::new);
        EntityRenderers.register(AMEntityRegistry.FROSTSTALKER.get(), RenderFroststalker::new);
        EntityRenderers.register(AMEntityRegistry.ICE_SHARD.get(), RenderIceShard::new);
        EntityRenderers.register(AMEntityRegistry.TUSKLIN.get(), RenderTusklin::new);
        EntityRenderers.register(AMEntityRegistry.LAVIATHAN.get(), RenderLaviathan::new);
        EntityRenderers.register(AMEntityRegistry.TOUCAN.get(), RenderToucan::new);
        EntityRenderers.register(AMEntityRegistry.MANED_WOLF.get(), RenderManedWolf::new);

        EntityRenderers.register(AMEntityRegistry.ROCKY_ROLLER.get(), RenderRockyRoller::new);
        EntityRenderers.register(AMEntityRegistry.FLUTTER.get(), RenderFlutter::new);
        EntityRenderers.register(AMEntityRegistry.POLLEN_BALL.get(), RenderPollenBall::new);
        EntityRenderers.register(AMEntityRegistry.GELADA_MONKEY.get(), RenderGeladaMonkey::new);
        EntityRenderers.register(AMEntityRegistry.JERBOA.get(), RenderJerboa::new);
        EntityRenderers.register(AMEntityRegistry.TERRAPIN.get(), RenderTerrapin::new);
        EntityRenderers.register(AMEntityRegistry.COSMIC_COD.get(), RenderCosmicCod::new);
        EntityRenderers.register(AMEntityRegistry.BUNFUNGUS.get(), RenderBunfungus::new);
        EntityRenderers.register(AMEntityRegistry.BISON.get(), RenderBison::new);
        EntityRenderers.register(AMEntityRegistry.GIANT_SQUID.get(), RenderGiantSquid::new);
        EntityRenderers.register(AMEntityRegistry.DEVILS_HOLE_PUPFISH.get(), RenderDevilsHolePupfish::new);
        EntityRenderers.register(AMEntityRegistry.CATFISH.get(), RenderCatfish::new);
        EntityRenderers.register(AMEntityRegistry.FLYING_FISH.get(), RenderFlyingFish::new);
        EntityRenderers.register(AMEntityRegistry.POTOO.get(), RenderPotoo::new);
        EntityRenderers.register(AMEntityRegistry.MUDSKIPPER.get(), RenderMudskipper::new);
        EntityRenderers.register(AMEntityRegistry.MUD_BALL.get(), RenderMudBall::new);
        EntityRenderers.register(AMEntityRegistry.RHINOCEROS.get(), RenderRhinoceros::new);
        EntityRenderers.register(AMEntityRegistry.SUGAR_GLIDER.get(), RenderSugarGlider::new);
        EntityRenderers.register(AMEntityRegistry.SKUNK.get(), RenderSkunk::new);
        EntityRenderers.register(AMEntityRegistry.FART.get(), RenderFart::new);
        EntityRenderers.register(AMEntityRegistry.BLUE_JAY.get(), RenderBlueJay::new);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        try {
            ItemProperties.register(AMItemRegistry.SHIELD_OF_THE_DEEP.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> {
                return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.SOMBRERO.get(), new ResourceLocation("silly"), (stack, p_239421_1_, p_239421_2_, j) -> {
                return AlexsMobs.isAprilFools() ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.PUPFISH_LOCATOR.get(), new ResourceLocation("in_chunk"), (stack, world, entity, j) -> {
                int x = pupfishChunkX * 16;
                int z = pupfishChunkZ * 16;
                if (entity != null && entity.getX() >= x && entity.getX() <= x + 16 && entity.getZ() >= z && entity.getZ() <= z + 16) {
                    return 1.0F;
                }
                return 0.0F;
            });
        } catch (Exception e) {
            AlexsMobs.LOGGER.warn("Could not load item models for weapons");
        }
        BlockEntityRenderers.register(AMTileEntityRegistry.CAPSID.get(), RenderCapsid::new);
    }

    private void initRainbowBuffers() {
        Minecraft.getInstance().renderBuffers().fixedBuffers.put(AMRenderTypes.STATIC_PORTAL, new BufferBuilder(AMRenderTypes.STATIC_PORTAL.bufferSize()));
        Minecraft.getInstance().renderBuffers().fixedBuffers.put(AMRenderTypes.STATIC_PARTICLE, new BufferBuilder(AMRenderTypes.STATIC_PARTICLE.bufferSize()));
        Minecraft.getInstance().renderBuffers().fixedBuffers.put(AMRenderTypes.STATIC_ENTITY, new BufferBuilder(AMRenderTypes.STATIC_ENTITY.bufferSize()));
        initializedRainbowBuffers = true;
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @OnlyIn(Dist.CLIENT)
    public Object getArmorModel(int armorId, LivingEntity entity) {
        switch (armorId) {
            /*
            case 0:
                return ROADRUNNER_BOOTS_MODEL;
            case 1:
                return MOOSE_HEADGEAR_MODEL;
            case 2:
                return FRONTIER_CAP_MODEL.withAnimations(entity);
            case 3:
                return SOMBRERO_MODEL;
            case 4:
                return SPIKED_TURTLE_SHELL_MODEL;
            case 5:
                return FEDORA_MODEL;
            case 6:
                return ELYTRA_MODEL.withAnimations(entity);

             */
            default:
                return null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void onEntityStatus(Entity entity, byte updateKind) {
        if (entity instanceof EntityBlueJay && entity.isAlive() && updateKind == 67) {
            singingBlueJayId = entity.getId();
        }
        if (entity instanceof EntityBlueJay && entity.isAlive() && updateKind == 68) {
            singingBlueJayId = -1;
        }
    }

    public void updateBiomeVisuals(int x, int z) {
        Minecraft.getInstance().levelRenderer.setBlocksDirty(x - 32, 0, x - 32, z + 32, 255, z + 32);
    }

    public static void setupParticles(RegisterParticleProvidersEvent registry) {
        AlexsMobs.LOGGER.debug("Registered particle factories");
        registry.register(AMParticleRegistry.PLATYPUS_SENSE.get(), ParticlePlatypus.Factory::new);
        registry.register(AMParticleRegistry.WHALE_SPLASH.get(), ParticleWhaleSplash.Factory::new);
        registry.register(AMParticleRegistry.DNA.get(), ParticleDna.Factory::new);
        registry.register(AMParticleRegistry.SHOCKED.get(), ParticleSimpleHeart.Factory::new);
        //registry.register(AMParticleRegistry.INVERT_DIG.get(), ParticleInvertDig.Factory::new);
        registry.register(AMParticleRegistry.TEETH_GLINT.get(), ParticleTeethGlint.Factory::new);
        registry.register(AMParticleRegistry.SMELLY.get(), ParticleSmelly.Factory::new);
        registry.register(AMParticleRegistry.BUNFUNGUS_TRANSFORMATION.get(), ParticleBunfungusTransformation.Factory::new);
        registry.register(AMParticleRegistry.FUNGUS_BUBBLE.get(), ParticleFungusBubble.Factory::new);
        registry.register(AMParticleRegistry.SUNBIRD_FEATHER.get(), ParticleSunbirdFeather.Factory::new);
        registry.register(AMParticleRegistry.STATIC_SPARK.get(), new ParticleStaticSpark.Factory());
        registry.register(AMParticleRegistry.SKULK_BOOM.get(), new ParticleSkulkBoom.Factory());
        registry.register(AMParticleRegistry.BIRD_SONG.get(), ParticleBirdSong.Factory::new);
    }


    public void setRenderViewEntity(Entity entity) {
        prevPOV = Minecraft.getInstance().options.getCameraType();
        Minecraft.getInstance().setCameraEntity(entity);
        Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
    }

    public void resetRenderViewEntity() {
        Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
    }

    public int getPreviousPOV() {
        return prevPOV.ordinal();
    }

    public boolean isFarFromCamera(double x, double y, double z) {
        Minecraft lvt_1_1_ = Minecraft.getInstance();
        return lvt_1_1_.gameRenderer.getMainCamera().getPosition().distanceToSqr(x, y, z) >= 256.0D;
    }

    public void resetVoidPortalCreation(Player player) {

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRegisterEntityRenders(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @Override
    public Object getISTERProperties() {
        return new AMItemRenderProperties();
    }

    @Override
    public Object getArmorRenderProperties() {
        return new CustomArmorRenderProperties();
    }


    public void processVisualFlag(Entity entity, int flag) {
        if (entity == Minecraft.getInstance().player && flag == 87) {
            ClientEvents.renderStaticScreenFor = 60;
        }
    }

    public void setPupfishChunkForItem(int chunkX, int chunkZ) {
        this.pupfishChunkX = chunkX;
        this.pupfishChunkZ = chunkZ;
    }

    public void setDisplayTransmuteResult(int slot, ItemStack stack){
        transmuteStacks[Mth.clamp(slot, 0, 2)] = stack;
    }

    public ItemStack getDisplayTransmuteResult(int slot){
        ItemStack stack = transmuteStacks[Mth.clamp(slot, 0, 2)];
        return stack == null ? ItemStack.EMPTY : stack;
    }

    public int getSingingBlueJayId() {
        return singingBlueJayId;
    }

}
