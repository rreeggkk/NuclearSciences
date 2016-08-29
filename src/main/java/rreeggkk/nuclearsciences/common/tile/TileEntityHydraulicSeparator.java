package rreeggkk.nuclearsciences.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidRegistry;
import rreeggkk.nuclearsciences.common.block.BlockHydraulicSeparator;
import rreeggkk.nuclearsciences.common.crafting.hydraulic.HydraulicSeparatorCraftingHandler;
import rreeggkk.nuclearsciences.common.crafting.hydraulic.IHydraulicRecipe;
import rreeggkk.nuclearsciences.common.energy.IntEnergyContainer;
import rreeggkk.nuclearsciences.common.fluid.SingleFluidTank;
import rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import rreeggkk.nuclearsciences.common.util.ItemStackUtil;

public class TileEntityHydraulicSeparator extends TileEntity implements ISidedInventory, ITickable {

	private SingleFluidTank tank;
	private IntEnergyContainer energy;

	private ItemStack[] inventory = new ItemStack[2];

	private int currentEnergy;
	private int currentWater;
	private ItemStack output;
	private IHydraulicRecipe processingRecipe;

	public TileEntityHydraulicSeparator() {
		tank = new SingleFluidTank(FluidRegistry.WATER, 8000);
		energy = new IntEnergyContainer(5000, 5000, 80, false);
	}

	@Override
	public void update() {

		if (!worldObj.isRemote) {
			if (processingRecipe == null) {
				if (inventory[0] != null) {
					IHydraulicRecipe recipe = HydraulicSeparatorCraftingHandler.instance.getRecipeForInput(inventory[0]);
					if (recipe != null && recipe.getInputAmount(inventory[0]) <= inventory[0].stackSize) {
						processingRecipe = recipe;
						currentEnergy = 0;
						currentWater = 0;
						output = processingRecipe.getResult(inventory[0]);
						inventory[0].splitStack(processingRecipe.getInputAmount(inventory[0]));
						if (inventory[0].stackSize == 0) {
							inventory[0] = null;
						}
						worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos).withProperty(BlockHydraulicSeparator.RUNNING, true), 2);
					}
				} else {
					worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos).withProperty(BlockHydraulicSeparator.RUNNING, false), 2);
				}
			}
			if (processingRecipe != null) {
				if (!(currentEnergy >= processingRecipe.getRequiredEnergy() && currentWater >= processingRecipe.getRequiredWater())) {
					int desiredEn = (int) Math.round(getMaxRunFraction() * getMaxEnergyPerTick());
					if (desiredEn > energy.getStored()) {
						desiredEn = (int) energy.getStored();
					}
					if (currentEnergy + desiredEn > processingRecipe.getRequiredEnergy()) {
						desiredEn = (int) (processingRecipe.getRequiredEnergy() - currentEnergy);
					}
					int desiredWater = (int) Math.round(processingRecipe.getRequiredWater() * (double)(currentEnergy + desiredEn)/processingRecipe.getRequiredEnergy() - currentWater);

					if (desiredWater>tank.getFluidAmount()) {
						desiredWater = tank.getFluidAmount();
						desiredEn = (int) Math.round(processingRecipe.getRequiredEnergy() * (double)(currentWater + desiredWater)/processingRecipe.getRequiredWater() - currentEnergy);
					}

					currentEnergy += energy.takePower(desiredEn, false);
					if (desiredWater > 0) {
						currentWater += tank.drain(desiredWater, true).amount;
					}
				}

				if (currentEnergy >= processingRecipe.getRequiredEnergy() && currentWater >= processingRecipe.getRequiredWater()) {
					if (inventory[1] == null || inventory[1].stackSize == 0) {
						inventory[1] = output.copy();
						processingRecipe = null;
					} else if (ItemStackUtil.areItemStacksEqual(inventory[1], output)) {
						inventory[1].stackSize += output.stackSize;
						processingRecipe = null;
					} else {
						worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos).withProperty(BlockHydraulicSeparator.RUNNING, false), 2);
					}
				}
			}
		}
	}

	public int getFixedCompletion(int point) {
		if (processingRecipe == null) {
			return 0;
		}
		return (int) ((double)currentEnergy/processingRecipe.getRequiredEnergy() * Math.pow(10, point));
	}

	public SingleFluidTank getTank() {
		return tank;
	}

	private double getMaxRunFraction() {
		return energy.getFraction();
	}

	private int getMaxEnergyPerTick() {
		return (int) energy.getOutputRate();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
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
		tank.readFromNBT(compound.getCompoundTag("Fluid"));
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
		compound.setTag("Fluid", tank.writeToNBT(new NBTTagCompound()));
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
		return 64;
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
		return index == 0 && HydraulicSeparatorCraftingHandler.instance.getRecipeForInput(stack) != null;
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
		return "container.nuclearsciences.hydraulicseparator.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0,1};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 1;
	}

	@Override
	public boolean hasCapability(Capability<?> c, EnumFacing facing) {
		if (c == CapabilityUtil.CAPABILITY_CONSUMER || c == CapabilityUtil.FLUID_HANDLER) {
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
		if (c == CapabilityUtil.FLUID_HANDLER) {
			return (T) tank;
		}
		return super.getCapability(c, facing);
	}

	public IntEnergyContainer getEnergy() {
		return energy;
	}
}
