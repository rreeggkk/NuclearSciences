package rreeggkk.nuclearsciences.common.tile.centrifuge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.energy.IntEnergyContainer;
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.util.CapabilityUtil;

public class TileEntityVaporizer extends TileEntity implements ITickable, ISidedInventory {

	private IntEnergyContainer energy;

	private ItemStack[] inventory = new ItemStack[3];

	private List<TileEntityCentrifuge> centrifuges = new ArrayList<TileEntityCentrifuge>();
	private TileEntityCondensor lightCondensor, heavyCondensor; 

	private int speed;
	private int thermalGradient;
	private double outputPerSecond;
	
	private boolean recalc;

	public TileEntityVaporizer() {
		energy = new IntEnergyContainer(10000, 10000, false);
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (recalc) {
				recalculateMultiblock();
				recalc = false;
			}
		}
	}
	
	private void recalculateMultiblock() {
		centrifuges.forEach((c)->c.setMaster(null));
		if (lightCondensor != null) lightCondensor.setMaster(null);
		if (heavyCondensor != null) heavyCondensor.setMaster(null);
		centrifuges = new ArrayList<TileEntityCentrifuge>();
		lightCondensor = null;
		heavyCondensor = null;
		
		Queue<BlockPos> searchQueue = new LinkedList<BlockPos>();
		searchQueue.offer(getPos().up());
		
		while (!searchQueue.isEmpty()) {
			BlockPos searchPos = searchQueue.poll();
			
			IBlockState state = worldObj.getBlockState(searchPos);
			
			if (state.getBlock() == ModBlocks.centrifuge) {
				
				if (centrifuges.contains((TileEntityCentrifuge) worldObj.getTileEntity(searchPos))) {
					continue;
				}
				
				TileEntityCentrifuge centrif = (TileEntityCentrifuge) worldObj.getTileEntity(searchPos);
				centrif.setMaster(this);
				centrifuges.add(centrif);
				
				BlockPos right = ModBlocks.centrifuge.getRightOf(state, searchPos);
				if (worldObj.getBlockState(right).getBlock() == ModBlocks.centrifuge) {
					searchQueue.offer(right);
				} else if (worldObj.getBlockState(right).getBlock() == ModBlocks.condensor) {
					heavyCondensor = (TileEntityCondensor)worldObj.getTileEntity(right);
					heavyCondensor.setMaster(this);
				}
				
				BlockPos left = ModBlocks.centrifuge.getLeftOf(state, searchPos);
				if (worldObj.getBlockState(left).getBlock() == ModBlocks.centrifuge) {
					searchQueue.offer(left);
				} else if (worldObj.getBlockState(left).getBlock() == ModBlocks.condensor) {
					lightCondensor = (TileEntityCondensor)worldObj.getTileEntity(left);
					lightCondensor.setMaster(this);
				}
			}
		}
	}
	
	public void recalculateNext() {
		recalc = true;
	}/*

	public void addCentrifuge(TileEntityCentrifuge cent) {
		centrifuges.add(cent);
	}

	public void removeCentrifuge(TileEntityCentrifuge cent) {
		if (centrifuges.remove(cent)) {

			double d = cent.getPos().distanceSq(getPos());

			for (TileEntityCentrifuge c : new ArrayList<TileEntityCentrifuge>(centrifuges)) {
				if (c.getPos().distanceSq(getPos()) > d) {
					centrifuges.remove(c);
					c.setMaster(null);
				}
			}
		}
	}

	public void addCondensor(TileEntityCondensor cond, boolean small) {
		if(small) {
			lightCondensor = cond;
		} else {
			heavyCondensor = cond;
		}
	}

	public void removeCondensor(boolean small) {
		if (small) {
			lightCondensor = null;
		} else {
			heavyCondensor = null;
		}
	}*/

	@Override
	public void invalidate() {
		super.invalidate();
		for (TileEntityCentrifuge cent : centrifuges) {
			cent.setMaster(null);
		}
		if (lightCondensor != null) lightCondensor.setMaster(null);
		if (heavyCondensor != null) heavyCondensor.setMaster(null);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		recalc = true;
		
		NBTTagList nbttaglist = compound.getTagList("Items", NBT.TAG_COMPOUND);

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < this.inventory.length)
			{
				this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		energy.deserializeNBT(compound.getCompoundTag("Energy"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i)
		{
			if (this.inventory[i] != null)
			{
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				this.inventory[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}
		compound.setTag("Items", nbttaglist);
		compound.setTag("Energy", energy.serializeNBT());
		return compound;
	}
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return inventory[index].splitStack(count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack temp = inventory[index];
		inventory[index] = null;
		return temp;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventory[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && stack.getItem() == ModItems.nuclearMaterial;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		inventory = new ItemStack[inventory.length];
	}

	@Override
	public String getName() {
		return "container.nuclearsciences.gascentrifuge.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 0;
	}

	@Override
	public boolean hasCapability(Capability<?> c, EnumFacing facing) {
		if (c == CapabilityUtil.CAPABILITY_CONSUMER) {
			return true;
		}
		return super.hasCapability(c, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> c, EnumFacing facing) {
		if (c == CapabilityUtil.CAPABILITY_CONSUMER) {
			return (T) energy;
		}
		return super.getCapability(c, facing);
	}

	public IntEnergyContainer getEnergy() {
		return energy;
	}
}
