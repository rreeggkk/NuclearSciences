package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCentrifuge extends TileEntity {
	private TileEntityVaporizer master;

	public void setMaster(TileEntityVaporizer master) {
		this.master = master;
	}

	public TileEntityVaporizer getMaster() {
		return master;
	}

}
