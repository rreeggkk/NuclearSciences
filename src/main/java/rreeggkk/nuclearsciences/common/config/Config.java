package rreeggkk.nuclearsciences.common.config;

import org.apfloat.Apfloat;

import net.minecraftforge.common.config.Configuration;
import rreeggkk.nuclearsciences.common.Constants;

public class Config {
	
	public static final Apfloat MIN_CUTOFF = new Apfloat(1e-30);
    private static final String CATEGORY_GENERAL = "general";

	private Configuration config;
	
	public String energyUnit;
	public String temperatureUnit;
	public Apfloat rtgPowerMultipler;

	public Config(Configuration configuration) {
		config = configuration;
	}

	public void init() {
		config.load();
		
		config.addCustomCategoryComment(CATEGORY_GENERAL, "General Configuration");
		
		energyUnit = config.getString("energyUnit", CATEGORY_GENERAL, "T", "The energy unit to use in GUIs and other displays.");
		
		temperatureUnit = config.getString("temperatureUnit", CATEGORY_GENERAL, "C", "The temperature unit to use in GUIs and other displays", new String[] {"C", "K", "F"});
		
		rtgPowerMultipler = new Apfloat(config.getString("rtgPowerMultiplier", CATEGORY_GENERAL, "10", "The multiplier on the power output of the RTG."), Constants.PRECISION);
		
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
