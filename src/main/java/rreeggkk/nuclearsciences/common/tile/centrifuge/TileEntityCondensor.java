package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityCondensor extends TileEntity implements ISidedInventory, IGasCentrifugeTile  {
	private TileEntityVaporizer master;
	private boolean forceUpdate = true;
	
	public void setMaster(TileEntityVaporizer master) {
		this.master = master;
		worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
		markDirty();
	}

	public TileEntityVaporizer getMaster() {
		return master;
	}

	@Override
	public String getName() {
		return master != null ? master.getName() : "";
	}

	@Override
	public boolean hasCustomName() {
		return master != null ? master.hasCustomName() : false;
	}

	@Override
	public int getSizeInventory() {
		return master != null ? master.getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return master != null ? master.getStackInSlot(index) : null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return master != null ? master.decrStackSize(index, count) : null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return master != null ? master.removeStackFromSlot(index) : null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (master != null) { 
			master.setInventorySlotContents(index, stack);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return master != null ? master.getInventoryStackLimit() : 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (master != null) { 
			master.openInventory(player);
		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (master != null) { 
			master.closeInventory(player);
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return master != null ? master.isItemValidForSlot(index, stack) : false;
	}

	@Override
	public int getField(int id) {
		return master != null ? master.getField(id) : 0;
	}

	@Override
	public void setField(int id, int value) {
		if (master != null) { 
			master.setField(id, value);
		}
	}

	@Override
	public int getFieldCount() {
		return master != null ? master.getFieldCount() : 0;
	}

	@Override
	public void clear() {
		if (master != null) { 
			master.clear();
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return master != null ? master.getSlotsForFaceCondensor(side, this) : new int[]{};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return master != null ? master.canExtractItemCondensor(index, stack, direction, this) : false;
	}

	@Override
	public boolean hasVaporizer() {
		if (forceUpdate) {
			worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
			markDirty();
			forceUpdate = false;
		}
		return master != null ? true : false;
	}

	@Override
	public TileEntityVaporizer getVaporizer() {
		return master != null ? master.getVaporizer() : null;
	}

	@Override
	public void setVaporizer(TileEntityVaporizer vaporizer) {
		this.master = vaporizer;
	}

	@Override
	public boolean isUsable(EntityPlayer player) {
		return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		if (compound.hasKey("dX")) {
			master = (TileEntityVaporizer) worldObj.getTileEntity(new BlockPos(compound.getInteger("dX"),compound.getInteger("dY"),compound.getInteger("dZ")).add(getPos()));
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
