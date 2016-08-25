package rreeggkk.nuclearsciences.common.block;

public class ModBlocks {
	
	public static BlockRadioactiveStone radioactiveStone;
	public static BlockRTG rtg;
	public static BlockHydraulicSeparator hydraulicSeparator;

	public static void init() {
		radioactiveStone = new BlockRadioactiveStone();
		rtg = new BlockRTG();
		hydraulicSeparator = new BlockHydraulicSeparator();
	}
}
