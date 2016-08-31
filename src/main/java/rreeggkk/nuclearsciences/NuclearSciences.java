package rreeggkk.nuclearsciences;

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.command.GiveNuclearMaterialCommand;
import rreeggkk.nuclearsciences.common.config.Config;
import rreeggkk.nuclearsciences.common.event.EventHandler;
import rreeggkk.nuclearsciences.proxy.CommonProxy;

@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.MOD_VERSION)
public class NuclearSciences {
	
	@Mod.Instance
	public static NuclearSciences instance;
	
	public Logger logger;
	
	@SidedProxy(clientSide = "rreeggkk.nuclearsciences.proxy.ClientProxy", serverSide = "rreeggkk.nuclearsciences.proxy.ServerProxy")
    public static CommonProxy proxy;
	
	public CreativeTabs tab;
	
	public Config config;
	
	public EventHandler eventHandler;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		logger.info("Preinitializing");
		
		File configDir = event.getModConfigurationDirectory();
		config = new Config(new Configuration(new File(configDir, "NuclearSciences.cfg"))); 
		
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		logger.info("Initializing");
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		logger.info("Postinitialzing");
		proxy.postInit(event);
		config.trySave();
	}
	
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new GiveNuclearMaterialCommand());
    }
}
