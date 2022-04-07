package me.steven.mobsmainmenu.client;

import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class DummyDimensionType {

    private static DimensionType instance;

    public static DimensionType getInstance() {
        if (instance == null) instance =  DimensionType.create(OptionalLong.empty(), true,  false, false, false, 1.0, false, false, false, false, false,16, 32, 0, BlockTags.INFINIBURN_OVERWORLD, DimensionType.OVERWORLD_ID, 1f);
        return instance;
    }
}
