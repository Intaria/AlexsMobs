package com.github.alexthe666.alexsmobs.client.particle;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AMParticleRegistry {

    public static final DeferredRegister<ParticleType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AlexsMobs.MODID);
    
    public static final RegistryObject<SimpleParticleType> PLATYPUS_SENSE = DEF_REG.register("platypus_sense", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> WHALE_SPLASH = DEF_REG.register("whale_splash", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DNA = DEF_REG.register("dna", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHOCKED = DEF_REG.register("shocked", ()-> new SimpleParticleType(false));
    //public static final RegistryObject<SimpleParticleType> INVERT_DIG = DEF_REG.register("invert_dig", ()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> TEETH_GLINT = DEF_REG.register("teeth_glint", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMELLY = DEF_REG.register("smelly", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BUNFUNGUS_TRANSFORMATION = DEF_REG.register("bunfungus_transformation", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FUNGUS_BUBBLE = DEF_REG.register("fungus_bubble", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUNBIRD_FEATHER = DEF_REG.register("sunbird_feather", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> STATIC_SPARK = DEF_REG.register("static_spark", ()-> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SKULK_BOOM = DEF_REG.register("skulk_boom", ()-> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BIRD_SONG = DEF_REG.register("bird_song", ()-> new SimpleParticleType(false));
}
