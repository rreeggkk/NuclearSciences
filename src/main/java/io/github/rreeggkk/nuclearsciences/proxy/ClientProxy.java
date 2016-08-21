package io.github.rreeggkk.nuclearsciences.proxy;

import io.github.rreeggkk.nuclearsciences.common.block.ModBlocks;
import io.github.rreeggkk.nuclearsciences.common.item.ModItems;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		
		ModBlocks.initModels();
		ModItems.initModels();
	}
}
