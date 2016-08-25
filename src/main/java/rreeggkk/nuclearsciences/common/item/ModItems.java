package rreeggkk.nuclearsciences.common.item;

public class ModItems {
	
	public static ItemNuclearMaterial nuclearMaterial;
	public static ItemRTGUpgradeBase irpanel;
	public static ItemRTGUpgradeBase stirling;

	public static void init() {
		nuclearMaterial = new ItemNuclearMaterial();
		irpanel = (ItemRTGUpgradeBase) new ItemRTGUpgradeBase(0.1, "irpanel");
		stirling = (ItemRTGUpgradeBase) new ItemRTGUpgradeBase(0.2, "stirling");
	}
}
