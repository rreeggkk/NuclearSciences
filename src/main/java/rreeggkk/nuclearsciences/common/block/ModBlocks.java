package rreeggkk.nuclearsciences.common.block;

import rreeggkk.nuclearsciences.common.block.centrifuge.BlockCentrifuge;
import rreeggkk.nuclearsciences.common.block.centrifuge.BlockCondensor;
import rreeggkk.nuclearsciences.common.block.centrifuge.BlockVaporizer;

public class ModBlocks {
	
	public static BlockRadioactiveStone radioactiveStone;
	public static BlockRTG rtg;
	public static BlockHydraulicSeparator hydraulicSeparator;
	public static BlockChemicalSeparator chemicalSeparator;
	
	public static BlockVaporizer vaporizer;
	public static BlockCentrifuge centrifuge;
	public static BlockCondensor condensor;

	public static void init() {
		radioactiveStone = new BlockRadioactiveStone();
		rtg = new BlockRTG();
		hydraulicSeparator = new BlockHydraulicSeparator();
		chemicalSeparator = new BlockChemicalSeparator();
		
		vaporizer = new BlockVaporizer();
		centrifuge = new BlockCentrifuge();
		condensor = new BlockCondensor();
	}
}
