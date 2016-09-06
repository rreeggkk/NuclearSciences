package rreeggkk.nuclearsciences.common.config;

import org.apfloat.Apfloat;

import net.minecraftforge.common.config.Configuration;
import rreeggkk.nuclearsciences.common.Constants;

public class Config {

	public static final Apfloat MIN_CUTOFF = new Apfloat(1e-30);
	private static final String CATEGORY_GENERAL = "general";
	private static final String CATEGORY_CRAFTING = "crafting";
	private static final String CATEGORY_BALANCING = "balancing";

	private Configuration config;

	public String energyUnit;
	public String temperatureUnit;
	public Apfloat rtgPowerMultipler;
	
	public boolean vanillaRecipe;
	
	public int chemicalSeparatorEnergyPerOperation;
	public double SWUPerCentrifugeTick;
	public double energyPerSWU;
	
	public int fuelPackerEnergyPerPack;

	public Config(Configuration configuration) {
		config = configuration;
	}

	public void init() {
		config.load();

		config.addCustomCategoryComment(CATEGORY_GENERAL, "General Configuration"); {
			energyUnit = config.getString("energyUnit", CATEGORY_GENERAL, "T", "The energy unit to use in GUIs and other displays.");
			temperatureUnit = config.getString("temperatureUnit", CATEGORY_GENERAL, "C", "The temperature unit to use in GUIs and other displays", new String[] {"C", "K", "F"});
			rtgPowerMultipler = new Apfloat(config.getString("rtgPowerMultiplier", CATEGORY_GENERAL, "10", "The multiplier on the power output of the RTG."), Constants.PRECISION);
		}
		config.addCustomCategoryComment(CATEGORY_CRAFTING, "Crafting Configuration"); {
			vanillaRecipe = config.getBoolean("VanillaRecipe", CATEGORY_CRAFTING, true, "A recipe that contains only vanilla parts");
		}
		config.addCustomCategoryComment(CATEGORY_BALANCING, "Balancing Configuration"); {
			chemicalSeparatorEnergyPerOperation = config.getInt("ChemicalSeparatorEnergyPerOperation", CATEGORY_BALANCING, 5000, 0, Integer.MAX_VALUE, "The amount of energy that the chemical separator will use per operation.");
			SWUPerCentrifugeTick = config.get(CATEGORY_BALANCING, "SWUPerCentrifugeTick", 0.05, "The amount of Separative Work Units produced by a centrifuge each tick. [Default: 0.05]").getDouble();
			energyPerSWU = config.get(CATEGORY_BALANCING, "energyPerSWU", 1000, "The amount of energy per Separative Work Unit. [Default: 1000]").getDouble();
			fuelPackerEnergyPerPack = config.getInt("fuelPackerEnergyPerPack", CATEGORY_BALANCING, 500, 0, Integer.MAX_VALUE, "The amount of energy that the fuel packer will use per operation.");
		}

		trySave();
	}

	public void trySave() {
		if (config.hasChanged()) {
			config.save();
		}
	}

	public Configuration getConfiguration() {
		return config;
	}
}
