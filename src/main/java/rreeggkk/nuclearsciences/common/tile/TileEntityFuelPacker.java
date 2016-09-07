package rreeggkk.nuclearsciences.common.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apfloat.Apfloat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.block.BlockFuelPacker;
import rreeggkk.nuclearsciences.common.energy.IntEnergyContainer;
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.fuel.FuelType;
import rreeggkk.nuclearsciences.common.nuclear.fuel.FuelTypes;
import rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import rreeggkk.nuclearsciences.common.util.ItemStackUtil;
import rreeggkk.nuclearsciences.common.util.NuclearMaterialUtil;

public class TileEntityFuelPacker extends TileEntity implements ISidedInventory, ITickable {

	private IntEnergyContainer energy;

	private ItemStack[] inventory = new ItemStack[3];

	private int currentEnergy;
	private ItemStack output;
	private int energyNeeded;
	private FuelType fuelType;

	public TileEntityFuelPacker() {
		energy = new IntEnergyContainer(5000, 5000, 80, false);
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (output == null && inventory[0] != null && inventory[0].getItem() == ModItems.nuclearMaterial && fuelType != null && ModItems.nuclearMaterial.getTotalMass(inventory[0]).compareTo(fuelType.getGramsPerComponent()) >= 0 && eatExtraInput()) {
				energyNeeded = NuclearSciences.instance.config.fuelPackerEnergyPerPack;
				currentEnergy = 0;
				ItemStack[] outputs = getOutput(inventory[0], inventory[1]);

				output = outputs[1];
				inventory[0] = outputs[0];
				worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos).withProperty(BlockFuelPacker.RUNNING, true), 2);
			} else {
				worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos).withProperty(BlockFuelPacker.RUNNING, false), 2);
			}
			if (output != null) {
				if (currentEnergy < energyNeeded) {
					int desiredEn = (int) Math.round(getMaxRunFraction() * getMaxEnergyPerTick());
					if (desiredEn > energy.getStored()) {
						desiredEn = energy.getStored();
					}
					if (currentEnergy + desiredEn > energyNeeded) {
						desiredEn = energyNeeded - currentEnergy;
					}

					currentEnergy += energy.takePower(desiredEn, false);
				}

				if (currentEnergy >= energyNeeded) {
					if (inventory[1] == null || inventory[1].stackSize == 0) {
						inventory[1] = output.copy();
						output = null;
					} else if (ItemStackUtil.areItemStacksEqual(inventory[1], output) && inventory[1].stackSize + output.stackSize <= 64) {
						inventory[1].stackSize += output.stackSize;
						output = null;
					} else {
						worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos).withProperty(BlockFuelPacker.RUNNING, false), 2);
					}
				}
			}
		}
	}

	public int getFixedCompletion(int point) {
		if (output == null) {
			return 0;
		}
		return (int) ((double)currentEnergy/energyNeeded * Math.pow(10, point));
	}

	private double getMaxRunFraction() {
		return energy.getFraction();
	}

	private int getMaxEnergyPerTick() {
		return (int) energy.getOutputRate();
	}

	private ItemStack[] getOutput(ItemStack stack, ItemStack tryFit) {
		Map<AIsotope<?,?>, Apfloat> contents = ModItems.nuclearMaterial.getContentsMass(stack);
		Apfloat totalMass = ModItems.nuclearMaterial.getTotalMass(contents).precision(Constants.PRECISION);

		Map<AIsotope<?,?>, Apfloat> newContents = new HashMap<>();

		for (Entry<AIsotope<?,?>, Apfloat> e : contents.entrySet()) {
			newContents.put(e.getKey(), e.getValue().precision(Constants.PRECISION).divide(totalMass).multiply(fuelType.getGramsPerComponent().precision(Constants.PRECISION)).precision(Constants.PRECISION));
		}
		
		if (tryFit != null && fuelType != null && tryFit.getItem() == fuelType.getOutputItem().getItem() && tryFit.getItemDamage() == fuelType.getOutputItem().getItemDamage() && NuclearMaterialUtil.areCloseEnough(newContents, ModItems.nuclearFuel.getContentsMass(tryFit))) {
			newContents = ModItems.nuclearFuel.getContentsMass(tryFit);
		}

		for (Entry<AIsotope<?,?>, Apfloat> e : newContents.entrySet()) {
			contents.put(e.getKey(), contents.get(e.getKey()).precision(Constants.PRECISION).subtract(e.getValue()).precision(Constants.PRECISION));
		}

		ItemStack[] output = new ItemStack[2];

		output[0] = new ItemStack(ModItems.nuclearMaterial);

		ModItems.nuclearMaterial.setContentsMass(output[0], contents);

		output[1] = fuelType.getOutputItem().copy();

		ModItems.nuclearFuel.setContentsMass(output[1], newContents);

		return output;
	}

	private boolean eatExtraInput() {
		if (fuelType.getInputItem() == null) {
			return true;
		} else {
			if (ItemStackUtil.areItemStacksEqual(fuelType.getInputItem(), inventory[2])) {
				if (inventory[2].stackSize >= fuelType.getInputItem().stackSize) {
					inventory[2].splitStack(fuelType.getInputItem().stackSize);
					if (inventory[2].stackSize == 0) {
						inventory[2] = null;
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
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
		
		if (compound.hasKey("FuelType")) {
			fuelType = FuelTypes.getFuelTypes().stream().filter((p)->p.getUID().equals(compound.getString("FuelType"))).findFirst().orElse(null);
		}

		if (compound.hasKey("Processing")) {
			output = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("POutput"));

			currentEnergy = compound.getInteger("PEnergy");
			energyNeeded = compound.getInteger("NEnergy");
		}
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
		if (fuelType != null) {
			compound.setString("FuelType", fuelType.getUID());
		}

		if (output != null) {
			compound.setBoolean("Processing", true);

			NBTTagCompound nbttagcompound = new NBTTagCompound();
			output.writeToNBT(nbttagcompound);
			compound.setTag("POutput", output.serializeNBT());

			compound.setInteger("PEnergy", currentEnergy);
			compound.setInteger("NEnergy", energyNeeded);
		}

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
		return ItemStackHelper.getAndSplit(inventory, index, count);
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
		return "container.nuclearsciences.fuelpacker.name";
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

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
}
