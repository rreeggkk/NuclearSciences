package io.github.rreeggkk.nuclearsciences.common.crafting.hydraulic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apfloat.Apfloat;

import io.github.rreeggkk.nuclearsciences.common.Constants;
import io.github.rreeggkk.nuclearsciences.common.block.ModBlocks;
import io.github.rreeggkk.nuclearsciences.common.item.ModItems;
import io.github.rreeggkk.nuclearsciences.common.nuclear.NuclearOreType;
import io.github.rreeggkk.nuclearsciences.common.util.RandomUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RadioactiveStoneHydraulicRecipe implements IHydraulicRecipe {

	public static final Map<NuclearOreType, Double> oreTypes = new HashMap<NuclearOreType, Double>();
	
	static {
		oreTypes.put(NuclearOreType.NATURAL_URANIUM, 2.5);
		oreTypes.put(NuclearOreType.THORIUM, 9.928);
	}
	
	@Override
	public int getRequiredEnergy() {
		return 5000;
	}

	@Override
	public int getRequiredWater() {
		return 1000;
	}

	@Override
	public boolean isInput(ItemStack input) {
		return input.getItem() == Item.getItemFromBlock(ModBlocks.radioactiveStone);
	}

	@Override
	public ItemStack getResult(ItemStack input) {
		HashMap<String, Apfloat> contents = new HashMap<>();
		
		for (Entry<NuclearOreType, Double> e : oreTypes.entrySet()) {
			double mult = e.getValue() * RandomUtil.boundedGaussian(1, 0.2, 0, 2);
			for (Entry<String, Double> o : e.getKey().getOreMap().entrySet()) {
				contents.put(o.getKey(), contents.getOrDefault(o.getKey(), new Apfloat(0).precision(Constants.PRECISION)).add(new Apfloat(mult*o.getValue(), Constants.PRECISION)).precision(Constants.PRECISION));
			}
		}
		
		ItemStack stack = new ItemStack(ModItems.nuclearMaterial);
		ModItems.nuclearMaterial.setContents(stack, contents);
		return stack;
	}

	@Override
	public int getInputAmount(ItemStack input) {
		return 1;
	}
}
