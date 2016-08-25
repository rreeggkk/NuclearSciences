package io.github.rreeggkk.nuclearsciences.common.crafting.hydraulic;

import net.minecraft.item.ItemStack;

public interface IHydraulicRecipe {
	public int getRequiredEnergy();

	public int getRequiredWater();

	public boolean isInput(ItemStack input);
	
	public int getInputAmount(ItemStack input);

	public ItemStack getResult(ItemStack input);
}
