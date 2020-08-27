/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.surfacebuilder;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import net.dries007.tfc.world.noise.INoise2D;
import net.dries007.tfc.world.noise.SimplexNoise2D;

public class ShoreSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
    private long lastSeed;
    private boolean initialized;
    private INoise2D variantNoise;

    public ShoreSurfaceBuilder()
    {
        super(SurfaceBuilderConfig::deserialize);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
    {
        float variantNoiseValue = variantNoise.noise(x, z);
        if (variantNoiseValue > 0.6f)
        {
            TFCSurfaceBuilders.NORMAL.get().buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, TFCSurfaceBuilders.RED_SAND_CONFIG);
        }
        else
        {
            TFCSurfaceBuilders.NORMAL.get().buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, TFCSurfaceBuilders.RED_SANDSTONE_CONFIG);
        }
    }

    @Override
    public void setSeed(long seed)
    {
        if (lastSeed != seed || !initialized)
        {
            variantNoise = new SimplexNoise2D(seed).octaves(2).spread(0.3f).abs();

            lastSeed = seed;
            initialized = true;
        }
    }
}
