package rreeggkk.nuclearsciences.common.crafting.material;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apfloat.Apfloat;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.item.ModItems;

public class HalvingRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		int nums = 0;

		for (int i = 0; i<inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null) continue;

			if (stack.getItem() == ModItems.nuclearMaterial) {
				nums++;
			} else {
				return false;
			}
		}
		return nums == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		HashMap<String, Apfloat> contents = null;

		for (int i = 0; i<inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null) continue;

			if (contents == null && stack.getItem() == ModItems.nuclearMaterial) {
				contents = ModItems.nuclearMaterial.getContents(stack);
				for (Entry<String, Apfloat> e : contents.entrySet()) {
					contents.put(e.getKey(), e.getValue().divide(new Apfloat(2, Constants.PRECISION)).precision(Constants.PRECISION));
				}
			} else {
				return null;
			}
		}

		ItemStack stack = new ItemStack(ModItems.nuclearMaterial);
		ModItems.nuclearMaterial.setContents(stack, contents);
		return stack;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.nuclearMaterial);
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
		for (int i = 0; i < ret.length; i++){
			if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == ModItems.nuclearMaterial) {
				HashMap<String, Apfloat> contents = ModItems.nuclearMaterial.getContents(inv.getStackInSlot(i));
				
				for (Entry<String, Apfloat> e : contents.entrySet()) {
					contents.put(e.getKey(), e.getValue().divide(new Apfloat(2, Constants.PRECISION)).precision(Constants.PRECISION));
				}
				
				ItemStack stack = new ItemStack(ModItems.nuclearMaterial);
				ModItems.nuclearMaterial.setContents(stack, contents);
				ret[i] = stack;
			} else {
				ret[i] = null;
			}
		}
		return ret;
	}
}
