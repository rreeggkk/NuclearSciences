package rreeggkk.nuclearsciences.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;

public class ItemNSBase extends Item {
	public ItemNSBase(String name) {
		setUnlocalizedName(Constants.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(NuclearSciences.instance.tab);
		
		GameRegistry.register(this);
		
		if (NuclearSciences.proxy.getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "normal"));
		}
	}
}
