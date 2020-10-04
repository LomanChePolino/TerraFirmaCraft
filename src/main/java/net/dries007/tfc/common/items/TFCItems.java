/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.common.items;

import java.util.Map;
import java.util.function.Supplier;

import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.types.Ore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.common.TFCItemGroup;
import net.dries007.tfc.common.blocks.Gem;
import net.dries007.tfc.common.types.Metal;
import net.dries007.tfc.common.types.Rock;
import net.dries007.tfc.common.types.RockCategory;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Collection of all TFC items.
 * Unused is as the registry object fields themselves may be unused but they are required to register each item.
 * Whenever possible, avoid using hardcoded references to these, prefer tags or recipes.
 */
@SuppressWarnings("unused")
public final class TFCItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final Map<Gem, Map<Gem.Grade, RegistryObject<Item>>> GEMS = Helpers.mapOfKeys(Gem.class, gem ->
        Helpers.mapOfKeys(Gem.Grade.class, grade ->
            register(("gem/" + grade.name().toLowerCase() + "/" + gem.name()).toLowerCase(), TFCItemGroup.GEMS)
        )
    );

    public static final Map<Metal.Default, Map<Metal.ItemType, RegistryObject<Item>>> METAL_ITEMS = Helpers.mapOfKeys(Metal.Default.class, metal ->
        Helpers.mapOfKeys(Metal.ItemType.class, type -> type.hasMetal(metal), type ->
            register(("metal/" + type.name() + "/" + metal.name()).toLowerCase(), () -> type.create(metal))
        )
    );

    public static final Map<Ore.Default, RegistryObject<Item>> ORE = Helpers.mapOfKeys(Ore.Default.class, Ore.Default::isNotGem, type ->
        register( "ore/" + type.name().toLowerCase(), TFCItemGroup.MISC)
    );

    public static final Map<Ore.Default, Map<Ore.ItemGrade, RegistryObject<Item>>> ORE_GRADES = Helpers.mapOfKeys(Ore.Default.class, Ore.Default::isGraded, ore ->
        Helpers.mapOfKeys(Ore.ItemGrade.class, grade ->
            register(("ore/" + grade.name().toLowerCase() + '/' + ore.name().toLowerCase()), TFCItemGroup.MISC)
        )
    );

    public static final Map<HideItem.Size, Map<HideItem.Stage, RegistryObject<Item>>> HIDES = Helpers.mapOfKeys(HideItem.Size.class, size ->
        Helpers.mapOfKeys(HideItem.Stage.class, stage ->
            register(("hide/" + size.name().toLowerCase() + '/' + stage.name().toLowerCase()), () -> new HideItem(size, stage))
        )
    );

    public static final Map<RockCategory, Map<RockCategory.ItemType, RegistryObject<Item>>> ROCK_TOOLS = Helpers.mapOfKeys(RockCategory.class, category ->
        Helpers.mapOfKeys(RockCategory.ItemType.class, type ->
            register(("stone/" + type.name() + "/" + category.name()).toLowerCase(), () -> type.create(category))
        )
    );

    public static final Map<Rock.Default, RegistryObject<RockItem>> LOOSE_ROCKS = Helpers.mapOfKeys(Rock.Default.class, type ->
        register( "rock/rock/" + type.name().toLowerCase(), () -> new RockItem(type))
    );

    public static final Map<Rock.Default, RegistryObject<Item>> BRICKS = Helpers.mapOfKeys(Rock.Default.class, type ->
        register( "rock/brick/" + type.name().toLowerCase(), TFCItemGroup.MISC)
    );

    private static RegistryObject<Item> register(String name, ItemGroup group)
    {
        return register(name, () -> new Item(new Item.Properties().tab(group)));
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ITEMS.register(name, item);
    }
}