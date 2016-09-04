package rreeggkk.nuclearsciences.common.item;

public class ModItems {
	
	public static ItemNuclearMaterial nuclearMaterial;
	public static ItemRTGUpgradeBase upgrades;
	//public static ItemRTGUpgradeBase stirling;

	public static void init() {
		nuclearMaterial = new ItemNuclearMaterial();
		upgrades = new ItemRTGUpgradeBase(new double[]{0.1, 0.2}, new String[]{"irpanel", "stirling"});
		//stirling = (ItemRTGUpgradeBase) new ItemRTGUpgradeBase(0.2, "stirling");
	}
}
