package rreeggkk.nuclearsciences.common.crafting.material;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apfloat.Apfloat;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.item.ModItems;

public class AdditionRecipe implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		int nums = 0;

		for (int i = 0; i<inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.isEmpty()) continue;

			if (stack.getItem() == ModItems.nuclearMaterial) {
				nums++;
			} else {
				return false;
			}
		}
		return nums == 2;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		HashMap<String, Apfloat> contents = new HashMap<>();

		for (int i = 0; i<inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.isEmpty()) continue;

			if (stack.getItem() == ModItems.nuclearMaterial) {
				HashMap<String, Apfloat> newMap = ModItems.nuclearMaterial.getContents(stack);
				for (Entry<String, Apfloat> o : newMap.entrySet()) {
					contents.put(o.getKey(), contents.getOrDefault(o.getKey(), new Apfloat(0).precision(Constants.PRECISION)).add(o.getValue().precision(Constants.PRECISION)).precision(Constants.PRECISION));
				}
			} else {
				return ItemStack.EMPTY;
			}
		}

		ItemStack stack = new ItemStack(ModItems.nuclearMaterial);
		ModItems.nuclearMaterial.setContents(stack, contents);
		return stack;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(ModItems.nuclearMaterial);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
	}
}
