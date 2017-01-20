package rreeggkk.nuclearsciences.common.crafting.hydraulic;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreHydraulicRecipe implements IHydraulicRecipe {

	private int input;
	private int inputNum;
	private ItemStack output;
	private int energy, water;

	public OreHydraulicRecipe(String input, String output, int energy, int water) {
		this.input = OreDictionary.getOreID(input);
		List<ItemStack> ores = OreDictionary.getOres(output);

		if (!ores.isEmpty()) {
			this.output = ores.get(0);
		} else {
			this.output = ItemStack.EMPTY;
		}
		this.energy = energy;
		inputNum = 1;
		this.water = water;
	}

	public OreHydraulicRecipe(String input, String output, int outputNum,
			int energy, int water) {
		this.input = OreDictionary.getOreID(input);

		List<ItemStack> ores = OreDictionary.getOres(output);

		if (!ores.isEmpty()) {
			this.output = ores.get(0);
		} else {
			this.output = ItemStack.EMPTY;
		}
		this.output.setCount(outputNum);
		this.energy = energy;
		inputNum = 1;
		this.water = water;
	}

	public OreHydraulicRecipe(String input, int inputNum, String output,
			int outputNum, int energy, int water) {
		this.input = OreDictionary.getOreID(input);
		this.inputNum = inputNum;
		List<ItemStack> ores = OreDictionary.getOres(output);

		if (!ores.isEmpty()) {
			this.output = ores.get(0);
		} else {
			this.output = ItemStack.EMPTY;
		}
		this.output.setCount(outputNum);
		this.energy = energy;
		this.water = water;
	}

	public OreHydraulicRecipe(String input, int inputNum, ItemStack output,
			int energy, int water) {
		this.input = OreDictionary.getOreID(input);
		this.inputNum = inputNum;
		this.output = output;
		this.energy = energy;
		this.water = water;
	}

	public OreHydraulicRecipe(ItemStack input, ItemStack output, int energy,
			int water) {
		this.input = OreDictionary.getOreIDs(input)[0];
		inputNum = input.getCount();
		this.output = output;
		this.energy = energy;
		this.water = water;
	}

	@Override
	public int getRequiredEnergy() {
		return energy;
	}

	@Override
	public boolean isInput(ItemStack input) {
		for (int i : OreDictionary.getOreIDs(input)) {
			if (this.input == i) {
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack getResult(ItemStack input) {
		return output;
	}

	@Override
	public int getRequiredWater() {
		return water;
	}

	@Override
	public int getInputAmount(ItemStack input) {
		return inputNum;
	}
}
