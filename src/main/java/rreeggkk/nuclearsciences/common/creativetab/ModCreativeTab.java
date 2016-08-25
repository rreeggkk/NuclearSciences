package rreeggkk.nuclearsciences.common.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import rreeggkk.nuclearsciences.common.item.ModItems;

public class ModCreativeTab extends CreativeTabs {

	public ModCreativeTab() {
		super("tab.nuclearsciences.name");
	}

	@Override
	public Item getTabIconItem() {
		return ModItems.nuclearMaterial;
	}
}
