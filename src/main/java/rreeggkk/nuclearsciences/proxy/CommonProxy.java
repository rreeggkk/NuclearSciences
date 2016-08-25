package rreeggkk.nuclearsciences.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.crafting.ModCrafting;
import rreeggkk.nuclearsciences.common.crafting.hydraulic.HydraulicSeparatorCraftingHandler;
import rreeggkk.nuclearsciences.common.creativetab.ModCreativeTab;
import rreeggkk.nuclearsciences.common.element.ElementRegister;
import rreeggkk.nuclearsciences.common.gui.GuiHandler;
import rreeggkk.nuclearsciences.common.item.ModItems;

public abstract class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
		NuclearSciences.instance.tab = new ModCreativeTab();
		
		NuclearSciences.instance.config.init();
		
		ElementRegister.register();

		ModBlocks.init();
		ModItems.init();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(NuclearSciences.instance, new GuiHandler());
	}

	public void init(FMLInitializationEvent e) {
		ModCrafting.init();
		HydraulicSeparatorCraftingHandler.initRecipies();
	}

	public void postInit(FMLPostInitializationEvent e) {

	}
	
	public abstract Side getSide();
}
