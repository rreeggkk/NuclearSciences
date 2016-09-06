package rreeggkk.nuclearsciences.common.item;

import rreeggkk.nuclearsciences.common.nuclear.fuel.FuelTypes;

public class ModItems {
	
	public static ItemNuclearMaterial nuclearMaterial;
	public static ItemRTGUpgradeBase upgrades;
	public static ItemNuclearFuel nuclearFuel;

	public static void init() {
		nuclearMaterial = new ItemNuclearMaterial();
		upgrades = new ItemRTGUpgradeBase(new double[]{0.1, 0.2}, new String[]{"irpanel", "stirling"});
		nuclearFuel = new ItemNuclearFuel(FuelTypes.PELLET, FuelTypes.PEBBLE, FuelTypes.SALT);
	}
}
