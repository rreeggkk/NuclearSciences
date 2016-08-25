package io.github.rreeggkk.nuclearsciences.proxy;

import io.github.rreeggkk.nuclearsciences.NuclearSciences;
import io.github.rreeggkk.nuclearsciences.common.block.ModBlocks;
import io.github.rreeggkk.nuclearsciences.common.crafting.hydraulic.HydraulicSeparatorCraftingHandler;
import io.github.rreeggkk.nuclearsciences.common.creativetab.ModCreativeTab;
import io.github.rreeggkk.nuclearsciences.common.element.ElementRegister;
import io.github.rreeggkk.nuclearsciences.common.gui.GuiHandler;
import io.github.rreeggkk.nuclearsciences.common.item.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		NuclearSciences.instance.tab = new ModCreativeTab();
		
		NuclearSciences.instance.config.init();
		
		ElementRegister.register();

		ModBlocks.init();
		ModItems.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(NuclearSciences.instance, new GuiHandler());

		//ModCrafting.init();
	}

	public void init(FMLInitializationEvent e) {
		HydraulicSeparatorCraftingHandler.initRecipies();
	}

	public void postInit(FMLPostInitializationEvent e) {

	}
}
