package io.github.rreeggkk.nuclearsciences.common.creativetab;

import io.github.rreeggkk.nuclearsciences.common.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTab extends CreativeTabs {

	public ModCreativeTab() {
		super("tab.nuclearsciences.name");
	}

	@Override
	public Item getTabIconItem() {
		return ModItems.nuclearMaterial;
	}
}
