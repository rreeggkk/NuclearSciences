package io.github.rreeggkk.nuclearsciences.common.tile;

import java.util.HashMap;

import org.apfloat.Apfloat;
import org.apfloat.Apint;

import io.github.rreeggkk.nuclearsciences.NuclearSciences;
import io.github.rreeggkk.nuclearsciences.common.Constants;
import io.github.rreeggkk.nuclearsciences.common.energy.EnergyContainer;
import io.github.rreeggkk.nuclearsciences.common.item.ModItems;
import io.github.rreeggkk.nuclearsciences.common.item.RTGUpgrade;
import io.github.rreeggkk.nuclearsciences.common.nuclear.simulation.DecaySimulation;
import io.github.rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import io.github.rreeggkk.nuclearsciences.common.util.CompatUtil;
import io.github.rreeggkk.nuclearsciences.common.util.TemperatureUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityRTG extends TileEntity implements ITickable, IInventory {

	private ItemStack[] inventory;
	private EnergyContainer energy;
	private Apfloat partialEnergy = new Apfloat(0, Constants.PRECISION);
	private Apfloat lastEnergyPerTick = new Apfloat(0, Constants.PRECISION);
	private Apfloat temperature = TemperatureUtil.AMBIENT;

	public TileEntityRTG() {
		inventory = new ItemStack[2];
		energy = new EnergyContainer(5000, 5000, 5000, false);
	}

	@Override
	public void update() {

		if (!worldObj.isRemote) {
			
			Apfloat deltaHeat = new Apfloat(0, Constants.PRECISION);
			Apfloat heatCapacity = new Apfloat(1, Constants.PRECISION);
			
			if (inventory[0] != null && inventory[0].stackSize>0 && inventory[0].getItem() == ModItems.nuclearMaterial) {
				HashMap<String, Apfloat> molarContents = ModItems.nuclearMaterial.getContents(inventory[0]);
				deltaHeat = DecaySimulation.simulateDecay(molarContents).precision(Constants.PRECISION);
				ModItems.nuclearMaterial.setContents(inventory[0], molarContents);
				heatCapacity = ModItems.nuclearMaterial.getHeatCapacity(molarContents);
			}
			
			Apfloat q = temperature.subtract(TemperatureUtil.AMBIENT).multiply(getThermalConductivity());
			deltaHeat = deltaHeat.subtract(q);
			
			lastEnergyPerTick = getEfficiency().multiply(q).precision(Constants.PRECISION).multiply(NuclearSciences.instance.config.rtgPowerMultipler);
			partialEnergy = partialEnergy.add(lastEnergyPerTick);
			
			Apint intEnergy = partialEnergy.floor();
			partialEnergy = partialEnergy.subtract(intEnergy);
			
			energy.givePower(intEnergy.longValue(), false);
			
			temperature = temperature.add(deltaHeat.divide(heatCapacity));
			
			CompatUtil.tryPushEnergyFrom(worldObj, pos, energy);
		}

		worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
		markDirty();
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
		partialEnergy = new Apfloat(compound.getString("PartialEnergy"), Constants.PRECISION);
		temperature = new Apfloat(compound.getString("Temp"), Constants.PRECISION);
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
		compound.setString("PartialEnergy", partialEnergy.toString());
		compound.setString("Temp", temperature.toString());
		return compound;
	}

	@Override
	public String getName() {
		return "container.nuclearsciences.rtg.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
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
		this.inventory[index] = stack;
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
		if (index==0) {
			return stack.getItem() == ModItems.nuclearMaterial;			
		} else if (index==1) {
			return stack.getItem() instanceof RTGUpgrade;
		}
		return false;
	}

	public EnergyContainer getEnergy() {
		return energy;
	}

	public Apfloat getEnergyPerTick() {
		return lastEnergyPerTick;
	}

	public Apfloat getInternalTemperature() {
		return temperature;
	}

	public Apfloat getEfficiency() {
		return getCarnotEfficiency().multiply(getPercentCarnotEfficiency()).precision(Constants.PRECISION);
	}
	
	public Apfloat getCarnotEfficiency() {
		if (temperature.equals(new Apfloat(0))) {
			return new Apfloat(0, Constants.PRECISION);
		}
		return temperature.subtract(TemperatureUtil.AMBIENT).divide(temperature);
	}
	
	public Apfloat getPercentCarnotEfficiency() {
		if (inventory[1] != null && (inventory[1].getItem() instanceof RTGUpgrade)){
			return new Apfloat(((RTGUpgrade)inventory[1].getItem()).getPercentCarnotEfficiency(inventory[1]), Constants.PRECISION);
		}
		return new Apfloat(0.05, Constants.PRECISION);
	}
	
	public Apfloat getThermalConductivity() {
		return new Apfloat(0.2, Constants.PRECISION);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		inventory = new ItemStack[inventory.length];
	}

	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
		if (cap == CapabilityUtil.CAPABILITY_PRODUCER) {
			return true;
		}
		return super.hasCapability(cap, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing facing) {
		if (cap == CapabilityUtil.CAPABILITY_PRODUCER) {
			return (T) energy;
		}
		return super.getCapability(cap, facing);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound comp = new NBTTagCompound();
		comp.setLong("Power", energy.getStored());
		comp.setString("EnPTi", lastEnergyPerTick.toString());
		comp.setString("Temp", temperature.toString());
		return new SPacketUpdateTileEntity(getPos(), 0, comp);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound comp = pkt.getNbtCompound();
		energy.setStored(comp.getLong("Power"));
		lastEnergyPerTick = new Apfloat(comp.getString("EnPTi"), Constants.PRECISION);
		temperature = new Apfloat(comp.getString("Temp"), Constants.PRECISION);
	}
}
