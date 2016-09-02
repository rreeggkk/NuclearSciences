package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCentrifuge extends TileEntity implements IGasCentrifugeTile {
	private TileEntityVaporizer master;

	public void setMaster(TileEntityVaporizer master) {
		this.master = master;
	}

	public TileEntityVaporizer getMaster() {
		return master;
	}

	@Override
	public boolean hasVaporizer() {
		return master != null ? true : false;
	}

	@Override
	public TileEntityVaporizer getVaporizer() {
		return master != null ? master.getVaporizer() : null;
	}

	@Override
	public boolean isUsable(EntityPlayer player) {
		return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
	}
}
