package rreeggkk.nuclearsciences.common.block;

import rreeggkk.nuclearsciences.common.block.centrifuge.BlockCentrifuge;

public class ModBlocks {
	
	public static BlockRadioactiveStone radioactiveStone;
	public static BlockRTG rtg;
	public static BlockHydraulicSeparator hydraulicSeparator;
	public static BlockChemicalSeparator chemicalSeparator;
	
	public static BlockCentrifuge centrifuge;

	public static void init() {
		radioactiveStone = new BlockRadioactiveStone();
		rtg = new BlockRTG();
		hydraulicSeparator = new BlockHydraulicSeparator();
		chemicalSeparator = new BlockChemicalSeparator();
		
		centrifuge = new BlockCentrifuge();
	}
}
