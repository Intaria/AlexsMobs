package com.github.alexthe666.alexsmobs.tileentity;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AlexsMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AMTileEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AlexsMobs.MODID);

    public static RegistryObject<BlockEntityType<TileEntityLeafcutterAnthill>> LEAFCUTTER_ANTHILL = DEF_REG.register("leafcutter_anthill_te", () -> BlockEntityType.Builder.of(TileEntityLeafcutterAnthill::new, AMBlockRegistry.LEAFCUTTER_ANTHILL.get()).build(null));
    public static RegistryObject<BlockEntityType<TileEntityCapsid>> CAPSID = DEF_REG.register("capsid_te", () -> BlockEntityType.Builder.of(TileEntityCapsid::new, AMBlockRegistry.CAPSID.get()).build(null));
    public static RegistryObject<BlockEntityType<TileEntityTerrapinEgg>> TERRAPIN_EGG = DEF_REG.register("terrapin_egg_te", () -> BlockEntityType.Builder.of(TileEntityTerrapinEgg::new, AMBlockRegistry.TERRAPIN_EGG.get()).build(null));
}
