package rreeggkk.nuclearsciences.common.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
	
	public static BlockRadioactiveStone radioactiveStone;
	public static BlockRTG rtg;
	public static BlockHydraulicSeparator hydraulicSeparator;

	public static void init() {
		radioactiveStone = new BlockRadioactiveStone();
		rtg = new BlockRTG();
		hydraulicSeparator = new BlockHydraulicSeparator();
	}
	
    @SideOnly(Side.CLIENT)
    public static void initModels() {
    	radioactiveStone.initModel();
    	rtg.initModel();
    	hydraulicSeparator.initModel();
    }
}
