package io.github.rreeggkk.nuclearsciences.common.item;

import io.github.rreeggkk.nuclearsciences.common.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
	
	public static ItemNuclearMaterial nuclearMaterial;
	public static ItemRTGUpgradeBase irpanel;
	public static ItemRTGUpgradeBase stirling;

	public static void init() {
		nuclearMaterial = new ItemNuclearMaterial();
		irpanel = (ItemRTGUpgradeBase) new ItemRTGUpgradeBase(0.1).setUnlocalizedName(Constants.MOD_ID + ".irpanel").setRegistryName("irpanel");
		stirling = (ItemRTGUpgradeBase) new ItemRTGUpgradeBase(0.2).setUnlocalizedName(Constants.MOD_ID + ".stirling").setRegistryName("stirling");
		GameRegistry.register(irpanel);
		GameRegistry.register(stirling);
	}
	
    @SideOnly(Side.CLIENT)
    public static void initModels() {
    	nuclearMaterial.initModel();
    	irpanel.initModel();
    	stirling.initModel();
    }
}
