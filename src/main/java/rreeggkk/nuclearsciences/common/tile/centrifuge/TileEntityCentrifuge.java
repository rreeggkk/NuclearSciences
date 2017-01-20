package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityCentrifuge extends TileEntity implements IGasCentrifugeTile {
	private TileEntityVaporizer master;

	public void setMaster(TileEntityVaporizer master) {
		this.master = master;
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
		markDirty();
	}

	public TileEntityVaporizer getMaster() {
		return master;
	}

	@Override
	public void setVaporizer(TileEntityVaporizer vaporizer) {
		this.master = vaporizer;
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

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		System.out.println(compound);
		if (compound.hasKey("dX")) {
			master = (TileEntityVaporizer) world.getTileEntity(new BlockPos(compound.getInteger("dX"),compound.getInteger("dY"),compound.getInteger("dZ")).add(getPos()));
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound comp = new NBTTagCompound();
		if (master != null) {
			BlockPos dPos = master.getPos().subtract(getPos());
			comp.setInteger("dX", dPos.getX());
			comp.setInteger("dY", dPos.getY());
			comp.setInteger("dZ", dPos.getZ());
		}
		return new SPacketUpdateTileEntity(getPos(), 0, comp);
	}
}
