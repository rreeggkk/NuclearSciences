 package rreeggkk.nuclearsciences.common.tile;

import java.util.HashMap;

import org.apfloat.Apfloat;
import org.apfloat.Apint;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.energy.IntEnergyContainer;
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.item.RTGUpgrade;
import rreeggkk.nuclearsciences.common.nuclear.simulation.DecaySimulation;
import rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import rreeggkk.nuclearsciences.common.util.CompatUtil;
import rreeggkk.nuclearsciences.common.util.TemperatureUtil;

public class TileEntityRTG extends TileEntityNSInventory implements ITickable {
	
	public static final Apfloat maxTemperature = new Apfloat(1273.15);

	private IntEnergyContainer energy;
	private Apfloat partialEnergy = new Apfloat(0, Constants.PRECISION);
	private Apfloat lastEnergyPerTick = new Apfloat(0, Constants.PRECISION);
	private Apfloat temperature = TemperatureUtil.AMBIENT;

	public TileEntityRTG() {
		inventory = new ItemStack[2];
		energy = new IntEnergyContainer(5000, 5000, 5000, false);
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			if (temperature.compareTo(maxTemperature)>0) {
				IBlockState newBlock = CompatUtil.getMoltenRTGFluidBlock();
				world.setBlockState(getPos(), newBlock, 3);
				world.notifyNeighborsOfStateChange(getPos(), newBlock.getBlock(), true);
				return;
			}
		
			Apfloat deltaHeat = new Apfloat(0, Constants.PRECISION);
			Apfloat heatCapacity = new Apfloat(500, Constants.PRECISION);
			
			if (!inventory[0].isEmpty() && inventory[0].getCount()>0 && inventory[0].getItem() == ModItems.nuclearMaterial) {
				HashMap<String, Apfloat> molarContents = ModItems.nuclearMaterial.getContents(inventory[0]);
				deltaHeat = DecaySimulation.simulateDecay(molarContents).precision(Constants.PRECISION);
				ModItems.nuclearMaterial.setContents(inventory[0], molarContents);
				heatCapacity = heatCapacity.add(ModItems.nuclearMaterial.getHeatCapacity(molarContents)).precision(Constants.PRECISION);
			}
			
			Apfloat q = temperature.subtract(TemperatureUtil.AMBIENT).multiply(getThermalConductivity());
			deltaHeat = deltaHeat.subtract(q);
			
			lastEnergyPerTick = getEfficiency().multiply(q).precision(Constants.PRECISION).multiply(NuclearSciences.instance.config.rtgPowerMultipler);
			partialEnergy = partialEnergy.add(lastEnergyPerTick);
			
			Apint intEnergy = partialEnergy.floor();
			partialEnergy = partialEnergy.subtract(intEnergy);
			
			energy.givePower(intEnergy.longValue(), false);
			
			CompatUtil.tryPushEnergyFrom(world, pos, energy);
			
			temperature = temperature.add(deltaHeat.divide(heatCapacity));
		}

		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
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
				this.inventory[j] = new ItemStack(nbttagcompound);
			}
		}

		energy.deserializeNBT(compound.getCompoundTag("Energy"));
		if (compound.hasKey("PartialEnergy")) {
			partialEnergy = new Apfloat(compound.getString("PartialEnergy"), Constants.PRECISION);
		} else {
			partialEnergy = new Apfloat(0, Constants.PRECISION);
		}
		if (compound.hasKey("Temp")) {
			temperature = new Apfloat(compound.getString("Temp"), Constants.PRECISION);
		} else {
			temperature = TemperatureUtil.AMBIENT;
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
		compound.setString("PartialEnergy", partialEnergy.toString());
		compound.setString("Temp", temperature.toString());
		return compound;
	}

	@Override
	public String getName() {
		return "container.nuclearsciences.rtg.name";
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
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index==0) {
			return stack.getItem() == ModItems.nuclearMaterial;			
		} else if (index==1) {
			return stack.getItem() instanceof RTGUpgrade;
		}
		return false;
	}

	public IntEnergyContainer getEnergy() {
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
	public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
		if (cap == CapabilityUtil.TESLA_PRODUCER) {
			return true;
		}
		return super.hasCapability(cap, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing facing) {
		if (cap == CapabilityUtil.TESLA_PRODUCER) {
			return (T) energy;
		}
		return super.getCapability(cap, facing);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound comp = new NBTTagCompound();
		comp.setString("EnPTi", lastEnergyPerTick.toString());
		comp.setString("Temp", temperature.toString());
		return new SPacketUpdateTileEntity(getPos(), 0, comp);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound comp = pkt.getNbtCompound();
		lastEnergyPerTick = new Apfloat(comp.getString("EnPTi"), Constants.PRECISION);
		temperature = new Apfloat(comp.getString("Temp"), Constants.PRECISION);
	}
}
