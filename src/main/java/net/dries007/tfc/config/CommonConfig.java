/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.config;

import java.util.function.Function;

import net.minecraftforge.common.ForgeConfigSpec;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Common Config
 * - not synced, saved per instance
 * - use for things that are only important server side (i.e. world gen), or make less sense to have per-world.
 */
public class CommonConfig
{
    // World Generation - General
    public final ForgeConfigSpec.BooleanValue flatBedrock;
    public final ForgeConfigSpec.IntValue islandFrequency;
    public final ForgeConfigSpec.IntValue biomeZoomLevel;
    public final ForgeConfigSpec.IntValue rockBottomZoomLevel;
    public final ForgeConfigSpec.IntValue rockMiddleZoomLevel;
    public final ForgeConfigSpec.IntValue rockTopZoomLevel;
    public final ForgeConfigSpec.IntValue rockLayerHeight;
    public final ForgeConfigSpec.IntValue rockLayerSpread;
    public final ForgeConfigSpec.IntValue seaLevel;
    public final ForgeConfigSpec.EnumValue<NoiseLayerType> temperatureLayerType;
    public final ForgeConfigSpec.IntValue temperatureLayerScale;
    public final ForgeConfigSpec.EnumValue<NoiseLayerType> rainfallLayerType;
    public final ForgeConfigSpec.IntValue rainfallLayerScale;
    public final ForgeConfigSpec.IntValue defaultMonthLength;
    // World Generation - Caves
    public final ForgeConfigSpec.IntValue worleyCaveHeightFade;
    public final ForgeConfigSpec.DoubleValue worleyCaveBaseNoiseCutoff;
    public final ForgeConfigSpec.DoubleValue worleyCaveWorleyNoiseCutoff;
    public final ForgeConfigSpec.IntValue caveSpikeMaxY;
    // World Generation - Biomes
    public final ForgeConfigSpec.IntValue frozenTemperatureCutoff;
    public final ForgeConfigSpec.IntValue coldTemperatureCutoff;
    public final ForgeConfigSpec.IntValue normalTemperatureCutoff;
    public final ForgeConfigSpec.IntValue lukewarmTemperatureCutoff;
    public final ForgeConfigSpec.IntValue aridRainfallCutoff;
    public final ForgeConfigSpec.IntValue dryRainfallCutoff;
    public final ForgeConfigSpec.IntValue normalRainfallCutoff;
    public final ForgeConfigSpec.IntValue dampRainfallCutoff;

    CommonConfig(ForgeConfigSpec.Builder innerBuilder)
    {
        // Standardization for translation keys
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(MOD_ID + ".config.general." + name);

        innerBuilder.push("worldGeneration").push("general");

        flatBedrock = builder.apply("flatBedrock").define("flatBedrock", false);

        islandFrequency = builder.apply("islandFrequency").defineInRange("islandFrequency", 6, 1, 100);
        biomeZoomLevel = builder.apply("biomeZoomLevel").defineInRange("biomeZoomLevel", 4, 1, 20);

        rockBottomZoomLevel = builder.apply("rockBottomZoomLevel").comment("This controls how large the bottom rock layer is. The formula is on average, a layer is 2^(4 + rockBottomZoomLevel) blocks wide.").defineInRange("rockBottomZoomLevel", 7, 1, 20);
        rockMiddleZoomLevel = builder.apply("rockMiddleZoomLevel").comment("This controls how large the middle rock layer is. The formula is on average, a layer is 2^(4 + rockMiddleZoomLevel) blocks wide.").defineInRange("rockMiddleZoomLevel", 7, 1, 20);
        rockTopZoomLevel = builder.apply("rockTopZoomLevel").comment("This controls how large the top rock layer is. The formula is on average, a layer is 2^(4 + rockZoomLevel) blocks wide.").defineInRange("rockTopZoomLevel", 5, 1, 20);
        rockLayerHeight = builder.apply("rockLayerHeight").defineInRange("rockLayerHeight", 50, 0, 256);
        rockLayerSpread = builder.apply("rockLayerSpread").defineInRange("rockLayerSpread", 10, 0, 256);

        seaLevel = builder.apply("seaLevel").defineInRange("seaLevel", 96, 0, 256);

        temperatureLayerType = builder.apply("temperatureLayerType").comment("This controls how temperature is generated.").defineEnum("temperatureLayerType", NoiseLayerType.PERIODIC_Z, NoiseLayerType.values());
        temperatureLayerScale = builder.apply("temperatureLayerScale").comment("This is how spread out the temperature layer is, in blocks.").defineInRange("temperatureLayerScale", 20_000, 1000, 1_000_000);

        rainfallLayerType = builder.apply("rainfallLayerType").comment("This controls how rainfall is generated.").defineEnum("rainfallLayerType", NoiseLayerType.PERIODIC_X, NoiseLayerType.values());
        rainfallLayerScale = builder.apply("rainfallLayerScale").comment("This is how spread out the temperature layer is, in blocks.").defineInRange("rainfallLayerScale", 20_000, 1000, 1_000_000);

        defaultMonthLength = builder.apply("defaultMonthLength").defineInRange("defaultMonthLength", 8, 1, Integer.MAX_VALUE);

        innerBuilder.pop().push("caves");

        worleyCaveHeightFade = builder.apply("worleyCaveHeightFade").defineInRange("worleyCaveHeightFade", 94, 0, 256);
        worleyCaveBaseNoiseCutoff = builder.apply("worleyCaveBaseNoiseCutoff").defineInRange("worleyCaveBaseNoiseCutoff", 0.3, 0, 1);
        worleyCaveWorleyNoiseCutoff = builder.apply("worleyCaveWorleyNoiseCutoff").defineInRange("worleyCaveWorleyNoiseCutoff", 0.38, 0, 1);
        caveSpikeMaxY = builder.apply("caveSpikeMaxY").defineInRange("caveSpikeMaxY", 60, 0, 255);

        innerBuilder.pop().push("biomes");

        frozenTemperatureCutoff = builder.apply("frozenTemperatureCutoff").defineInRange("frozenTemperatureCutoff", -2, -20, 50);
        coldTemperatureCutoff = builder.apply("coldTemperatureCutoff").defineInRange("coldTemperatureCutoff", 6, -20, 50);
        normalTemperatureCutoff = builder.apply("normalTemperatureCutoff").defineInRange("normalTemperatureCutoff", 14, -20, 50);
        lukewarmTemperatureCutoff = builder.apply("lukewarmTemperatureCutoff").defineInRange("lukewarmTemperatureCutoff", 22, -20, 50);

        aridRainfallCutoff = builder.apply("aridRainfallCutoff").defineInRange("aridRainfallCutoff", 125, 0, 500);
        dryRainfallCutoff = builder.apply("dryRainfallCutoff").defineInRange("dryRainfallCutoff", 200, 0, 500);
        normalRainfallCutoff = builder.apply("normalRainfallCutoff").defineInRange("normalRainfallCutoff", 300, 0, 500);
        dampRainfallCutoff = builder.apply("dampRainfallCutoff").defineInRange("dampRainfallCutoff", 375, 0, 500);

        innerBuilder.pop().pop();
    }
}
