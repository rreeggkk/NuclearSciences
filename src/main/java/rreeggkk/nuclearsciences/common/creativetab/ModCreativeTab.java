package rreeggkk.nuclearsciences.common.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import rreeggkk.nuclearsciences.common.item.ModItems;

public class ModCreativeTab extends CreativeTabs {

	public ModCreativeTab() {
		super("tab.nuclearsciences.name");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.nuclearMaterial);
	}
}
