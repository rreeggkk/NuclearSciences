package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRadioactiveStone extends BlockNSBase {
	public BlockRadioactiveStone() {
		super(Material.ROCK, "radioactiveStone");
		
		setLightLevel(4/15f);
		setHardness(1.5F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
	}
}
