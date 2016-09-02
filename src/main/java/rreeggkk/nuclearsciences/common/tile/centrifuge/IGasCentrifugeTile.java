package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.entity.player.EntityPlayer;

public interface IGasCentrifugeTile {
	public boolean hasVaporizer();
	public TileEntityVaporizer getVaporizer();
	
	public boolean isUsable(EntityPlayer player);
}
