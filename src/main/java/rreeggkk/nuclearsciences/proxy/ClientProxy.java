package rreeggkk.nuclearsciences.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.item.ModItems;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		
		ModBlocks.initModels();
		ModItems.initModels();
	}
}
