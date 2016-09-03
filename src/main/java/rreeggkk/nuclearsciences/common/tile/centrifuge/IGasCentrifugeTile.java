package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGasCentrifugeTile {
	public boolean hasVaporizer();
	public TileEntityVaporizer getVaporizer();
	public void setVaporizer(TileEntityVaporizer vaporizer);
	
	public boolean isUsable(EntityPlayer player);
	
	public World getWorld();
	
	public void markDirty();
	public BlockPos getPos();
}
