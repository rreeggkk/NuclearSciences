package io.github.rreeggkk.nuclearsciences.common.util;

import net.minecraft.item.ItemStack;

public class ItemStackUtil {
    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB)
    {
        return stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB);
    }
}
