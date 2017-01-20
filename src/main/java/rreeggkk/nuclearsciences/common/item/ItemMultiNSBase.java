package rreeggkk.nuclearsciences.common.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;

public class ItemMultiNSBase extends Item {

	protected String[] names;

	public ItemMultiNSBase(String name, String... subNames) {
		this.names = subNames;

		setUnlocalizedName(Constants.MOD_ID + "." + name);
		setRegistryName(name);
		setHasSubtypes(true);
		setCreativeTab(NuclearSciences.instance.tab);
		//setMaxDamage(subNames.length);

		GameRegistry.register(this);

		if (NuclearSciences.proxy.getSide() == Side.CLIENT) {
			for (int i = 0; i<names.length; i++) {
				ResourceLocation loc = new ResourceLocation(Constants.MOD_ID, name + "/" + names[i]);
				ModelBakery.registerItemVariants(this, loc);
				ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(loc, "inventory"));
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + Constants.MOD_ID + "." + names[stack.getItemDamage()];
	}
	
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i<names.length; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean isDamageable() {
		return false;
	}
	
	
}
