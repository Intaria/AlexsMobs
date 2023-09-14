package com.github.alexthe666.alexsmobs.world;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.config.BiomeConfig;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import com.github.alexthe666.citadel.config.biome.SpawnBiomeData;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.common.world.ModifiableStructureInfo;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = AlexsMobs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AMWorldRegistry {

    public static void modifyStructure(Holder<Structure> structure, ModifiableStructureInfo.StructureInfo.Builder builder) {
        if (structure.is(BuiltinStructures.NETHER_FOSSIL) && AMConfig.soulVultureSpawnOnFossil && AMConfig.soulVultureSpawnWeight > 0) {
            builder.getStructureSettings().getOrAddSpawnOverrides(MobCategory.MONSTER).addSpawn(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SOUL_VULTURE.get(), AMConfig.soulVultureSpawnWeight, 1, 1));
        }
    }

    private static ResourceLocation getBiomeName(Holder<Biome> biome) {
        return biome.unwrap().map((resourceKey) -> resourceKey.location(), (noKey) -> null);
    }

    public static boolean testBiome(Pair<String, SpawnBiomeData> entry, Holder<Biome> biome) {
        boolean result = false;
        try {
            result = BiomeConfig.test(entry, biome, getBiomeName(biome));
        } catch (Exception e) {
            AlexsMobs.LOGGER.warn("could not test biome config for " + entry.getLeft() + ", defaulting to no spawns for mob");
            result = false;
        }
        return result;
    }

    public static void addBiomeSpawns(Holder<Biome> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (testBiome(BiomeConfig.roadrunner, biome) && AMConfig.roadrunnerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.ROADRUNNER.get(), AMConfig.roadrunnerSpawnWeight, 2, 2));
        }
        if (testBiome(BiomeConfig.gazelle, biome) && AMConfig.gazelleSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.GAZELLE.get(), AMConfig.gazelleSpawnWeight, 7, 7));
        }
        if (testBiome(BiomeConfig.crocodile, biome) && AMConfig.crocodileSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.CROCODILE.get(), AMConfig.crocodileSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.hummingbird, biome) && AMConfig.hummingbirdSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.HUMMINGBIRD.get(), AMConfig.hummingbirdSpawnWeight, 7, 7));
        }
        if (testBiome(BiomeConfig.orca, biome) && AMConfig.orcaSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.ORCA.get(), AMConfig.orcaSpawnWeight, 3, 4));
        }
        if (testBiome(BiomeConfig.sunbird, biome) && AMConfig.sunbirdSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SUNBIRD.get(), AMConfig.sunbirdSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.hammerheadShark, biome) && AMConfig.hammerheadSharkSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.HAMMERHEAD_SHARK.get(), AMConfig.hammerheadSharkSpawnWeight, 2, 3));
        }
        if (testBiome(BiomeConfig.lobster, biome) && AMConfig.lobsterSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.LOBSTER.get(), AMConfig.lobsterSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.capuchinMonkey, biome) && AMConfig.capuchinMonkeySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.CAPUCHIN_MONKEY.get(), AMConfig.capuchinMonkeySpawnWeight, 9, 16));
        }
        if (testBiome(BiomeConfig.warpedToad, biome) && AMConfig.warpedToadSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.WARPED_TOAD.get(), AMConfig.warpedToadSpawnWeight, 5, 5));
        }
        if (testBiome(BiomeConfig.moose, biome) && AMConfig.mooseSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.MOOSE.get(), AMConfig.mooseSpawnWeight, 3, 4));
        }
        if (testBiome(BiomeConfig.raccoon, biome) && AMConfig.raccoonSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.RACCOON.get(), AMConfig.raccoonSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.blobfish, biome) && AMConfig.blobfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.BLOBFISH.get(), AMConfig.blobfishSpawnWeight, 2, 2));
        }
        if (testBiome(BiomeConfig.seal, biome) && AMConfig.sealSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SEAL.get(), AMConfig.sealSpawnWeight, 3, 8));
        }
        if (testBiome(BiomeConfig.shoebill, biome) && AMConfig.shoebillSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SHOEBILL.get(), AMConfig.shoebillSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.elephant, biome) && AMConfig.elephantSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.ELEPHANT.get(), AMConfig.elephantSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.soulVulture, biome) && AMConfig.soulVultureSpawnWeight > 0 && !AMConfig.soulVultureSpawnOnFossil) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SOUL_VULTURE.get(), AMConfig.soulVultureSpawnWeight, 2, 3));
        }
        if (testBiome(BiomeConfig.snowLeopard, biome) && AMConfig.snowLeopardSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SNOW_LEOPARD.get(), AMConfig.snowLeopardSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.crow, biome) && AMConfig.crowSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.CROW.get(), AMConfig.crowSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.alligatorSnappingTurtle, biome) && AMConfig.alligatorSnappingTurtleSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get(), AMConfig.alligatorSnappingTurtleSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.mungus, biome) && AMConfig.mungusSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.MUNGUS.get(), AMConfig.mungusSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.emu, biome) && AMConfig.emuSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.EMU.get(), AMConfig.emuSpawnWeight, 2, 5));
        }
        if (testBiome(BiomeConfig.platypus, biome) && AMConfig.platypusSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.PLATYPUS.get(), AMConfig.platypusSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.kangaroo, biome) && AMConfig.kangarooSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.KANGAROO.get(), AMConfig.kangarooSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.cachalot_whale_spawns, biome) && AMConfig.cachalotWhaleSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.CACHALOT_WHALE.get(), AMConfig.cachalotWhaleSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.leafcutter_anthill_spawns, biome) && AMConfig.leafcutterAnthillSpawnChance > 0) {
            Optional<Holder<PlacedFeature>> optional = AMFeatureRegistry.AMPlacedFeatureRegistry.ANTHILL.getHolder();
            if (optional.isPresent()) {
                builder.getGenerationSettings().getFeatures(GenerationStep.Decoration.SURFACE_STRUCTURES).add(optional.get());
            }
        }
        if (testBiome(BiomeConfig.baldEagle, biome) && AMConfig.baldEagleSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.BALD_EAGLE.get(), AMConfig.baldEagleSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.tiger, biome) && AMConfig.tigerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.TIGER.get(), AMConfig.tigerSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.seagull, biome) && AMConfig.seagullSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SEAGULL.get(), AMConfig.seagullSpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.froststalker, biome) && AMConfig.froststalkerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.FROSTSTALKER.get(), AMConfig.froststalkerSpawnWeight, 5, 7));
        }
        if (testBiome(BiomeConfig.tusklin, biome) && AMConfig.tusklinSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.TUSKLIN.get(), AMConfig.tusklinSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.laviathan, biome) && AMConfig.laviathanSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.LAVIATHAN.get(), AMConfig.laviathanSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.toucan, biome) && AMConfig.toucanSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.TOUCAN.get(), AMConfig.toucanSpawnWeight, 5, 5));
        }
        if (testBiome(BiomeConfig.maned_wolf, biome) && AMConfig.manedWolfSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.MANED_WOLF.get(), AMConfig.manedWolfSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.rocky_roller, biome) && AMConfig.rockyRollerSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.ROCKY_ROLLER.get(), AMConfig.rockyRollerSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.flutter, biome) && AMConfig.flutterSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.FLUTTER.get(), AMConfig.flutterSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.gelada_monkey, biome) && AMConfig.geladaMonkeySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.GELADA_MONKEY.get(), AMConfig.geladaMonkeySpawnWeight, 9, 16));
        }
        if (testBiome(BiomeConfig.jerboa, biome) && AMConfig.jerboaSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.JERBOA.get(), AMConfig.jerboaSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.terrapin, biome) && AMConfig.terrapinSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.TERRAPIN.get(), AMConfig.terrapinSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.cosmic_cod, biome) && AMConfig.cosmicCodSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.COSMIC_COD.get(), AMConfig.cosmicCodSpawnWeight, 9, 13));
        }
        if (testBiome(BiomeConfig.bunfungus, biome) && AMConfig.bunfungusSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.BUNFUNGUS.get(), AMConfig.bunfungusSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.bison, biome) && AMConfig.bisonSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.BISON.get(), AMConfig.bisonSpawnWeight, 6, 10));
        }
        if (testBiome(BiomeConfig.giant_squid, biome) && AMConfig.giantSquidSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.GIANT_SQUID.get(), AMConfig.giantSquidSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.devils_hole_pupfish, biome) && AMConfig.devilsHolePupfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.DEVILS_HOLE_PUPFISH.get(), AMConfig.devilsHolePupfishSpawnWeight, 5, 12));
        }
        if (testBiome(BiomeConfig.catfish, biome) && AMConfig.catfishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.CATFISH.get(), AMConfig.catfishSpawnWeight, 1, 3));
        }
        if (testBiome(BiomeConfig.flying_fish, biome) && AMConfig.flyingFishSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.WATER_AMBIENT).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.FLYING_FISH.get(), AMConfig.flyingFishSpawnWeight, 3, 6));
        }
        if (testBiome(BiomeConfig.potoo, biome) && AMConfig.potooSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.POTOO.get(), AMConfig.potooSpawnWeight, 1, 1));
        }
        if (testBiome(BiomeConfig.mudskipper, biome) && AMConfig.mudskipperSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.MUDSKIPPER.get(), AMConfig.mudskipperSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.rhinoceros, biome) && AMConfig.rhinocerosSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.RHINOCEROS.get(), AMConfig.rhinocerosSpawnWeight, 3, 5));
        }
        if (testBiome(BiomeConfig.sugar_glider, biome) && AMConfig.sugarGliderSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SUGAR_GLIDER.get(), AMConfig.sugarGliderSpawnWeight, 2, 4));
        }
        if (testBiome(BiomeConfig.skunk, biome) && AMConfig.skunkSpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.SKUNK.get(), AMConfig.skunkSpawnWeight, 1, 2));
        }
        if (testBiome(BiomeConfig.blue_jay, biome) && AMConfig.blueJaySpawnWeight > 0) {
            builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(AMEntityRegistry.BLUE_JAY.get(), AMConfig.blueJaySpawnWeight, 2, 4));
        }
    }

}
