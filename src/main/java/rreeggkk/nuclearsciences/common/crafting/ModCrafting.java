package rreeggkk.nuclearsciences.common.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.item.ModItems;

public class ModCrafting {
	public static void init() {
		if (NuclearSciences.instance.config.vanillaRecipe) {
			GameRegistry.addRecipe(new ItemStack(ModBlocks.rtg), 
					"igi",
					"q q",
					"iri",
					'i', new ItemStack(Items.IRON_INGOT),
					'g', new ItemStack(Items.GOLD_INGOT),
					'q', new ItemStack(Items.QUARTZ),
					'r', new ItemStack(Blocks.REDSTONE_BLOCK));

			GameRegistry.addRecipe(new ItemStack(ModBlocks.hydraulicSeparator), 
					"ibi",
					"i i",
					"grg",
					'i', new ItemStack(Items.IRON_INGOT),
					'b', new ItemStack(Items.BUCKET),
					'g', new ItemStack(Items.GOLD_INGOT),
					'r', new ItemStack(Blocks.REDSTONE_BLOCK));

			GameRegistry.addRecipe(new ItemStack(ModItems.irpanel), 
					"igi",
					"grg",
					"rgr",
					'i', new ItemStack(Items.IRON_INGOT),
					'r', new ItemStack(Items.REDSTONE),
					'g', new ItemStack(Items.GOLD_NUGGET));

			GameRegistry.addRecipe(new ItemStack(ModItems.stirling), 
					"rir",
					"gpg",
					"iri",
					'i', new ItemStack(Items.IRON_INGOT),
					'r', new ItemStack(Items.REDSTONE),
					'g', new ItemStack(Items.GOLD_NUGGET),
					'p', new ItemStack(Blocks.PISTON));
		}
	}
}
