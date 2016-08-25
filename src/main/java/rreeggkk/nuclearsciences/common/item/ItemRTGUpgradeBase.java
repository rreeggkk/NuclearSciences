package rreeggkk.nuclearsciences.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rreeggkk.nuclearsciences.NuclearSciences;

public class ItemRTGUpgradeBase extends Item implements RTGUpgrade {
	
	private double eff;
	
	public ItemRTGUpgradeBase(double eff) {
		this.eff = eff;
		
		setCreativeTab(NuclearSciences.instance.tab);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public double getPercentCarnotEfficiency(ItemStack stack) {
		if (stack.getItem() != this) {
			return 0;
		}
		return eff;
	}
}
