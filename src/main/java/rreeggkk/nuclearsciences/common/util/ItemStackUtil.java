package rreeggkk.nuclearsciences.common.util;

import net.minecraft.item.ItemStack;

public class ItemStackUtil {
    public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB)
    {
        return stackA == null && stackB == null ? true : (stackA == null || stackB == null ? false : (stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB)));
    }
}
