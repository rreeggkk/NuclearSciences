package rreeggkk.nuclearsciences.common.tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apfloat.Apfloat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

public class TileEntityFuelPacker extends TileEntityNSInventory implements ISidedInventory, ITickable {

	private IntEnergyContainer energy;

	private int currentEnergy;
	private ItemStack output;
	private int energyNeeded;
	private FuelType fuelType;

	private boolean running;

	public TileEntityFuelPacker() {
		energy = new IntEnergyContainer(5000, 5000, 80, false);
		inventory = new ItemStack[3];
		clear();
		output = ItemStack.EMPTY;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			if (output.isEmpty() && !inventory[0].isEmpty() && inventory[0].getItem() == ModItems.nuclearMaterial && fuelType != null) {
				if (ModItems.nuclearMaterial.getTotalMass(inventory[0]).compareTo(fuelType.getGramsPerComponent()) >= 0) {
					if (eatExtraInput()) {
						energyNeeded = NuclearSciences.instance.config.fuelPackerEnergyPerPack;
						currentEnergy = 0;
						ItemStack[] outputs = getOutput(inventory[0], inventory[1]);

						output = outputs[1];
						inventory[0] = outputs[0];
						if (!running){
							running = true;
							world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
						}
					} else if (running) {
						running = false;
						world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
					}
				} else {
					energyNeeded = 0;
					currentEnergy = 0;

					output = inventory[0];
					inventory[0] = ItemStack.EMPTY;
					if (world.getBlockState(pos).getValue(BlockFuelPacker.RUNNING)){
						world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockFuelPacker.RUNNING, false), 2);
					}
				}
			} else if (running) {
				running = false;
				world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
			}
			if (!output.isEmpty()) {
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
					if (inventory[1].isEmpty() || inventory[1].getCount() == 0) {
						inventory[1] = output.copy();
						output = ItemStack.EMPTY;
					} else if (ItemStackUtil.areItemStacksEqual(inventory[1], output) && inventory[1].getCount() + output.getCount() <= 64) {
						inventory[1].setCount(inventory[1].getCount() + output.getCount());
						output = ItemStack.EMPTY;
					} else if (running) {
						running = false;
						world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
					}
				}
			}
		}
	}

	public int getFixedCompletion(int point) {
		if (output.isEmpty()) {
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

		boolean sameOut = false;

		if (!tryFit.isEmpty() && fuelType != null && tryFit.getItem() == fuelType.getOutputItem().getItem() && tryFit.getItemDamage() == fuelType.getOutputItem().getItemDamage() && NuclearMaterialUtil.areCloseEnough(newContents, ModItems.nuclearFuel.getContentsMass(tryFit))) {
			newContents = ModItems.nuclearFuel.getContentsMass(tryFit);
			sameOut = true;
		}

		for (Entry<AIsotope<?,?>, Apfloat> e : newContents.entrySet()) {
			contents.put(e.getKey(), contents.get(e.getKey()).precision(Constants.PRECISION).subtract(e.getValue()).precision(Constants.PRECISION));
		}

		ItemStack[] output = new ItemStack[2];

		output[0] = new ItemStack(ModItems.nuclearMaterial);

		ModItems.nuclearMaterial.setContentsMass(output[0], contents);

		if (sameOut) {
			output[1] = tryFit.copy();
			output[1].setCount(1);;
		} else {
			output[1] = fuelType.getOutputItem().copy();
			ModItems.nuclearFuel.setContentsMass(output[1], newContents);
		}


		return output;
	}

	private boolean eatExtraInput() {
		if (fuelType.getInputItem() == null) {
			return true;
		} else {
			if (ItemStackUtil.areItemStacksEqual(fuelType.getInputItem(), inventory[2])) {
				if (inventory[2].getCount() >= fuelType.getInputItem().getCount()) {
					inventory[2].splitStack(fuelType.getInputItem().getCount());
					if (inventory[2].getCount() == 0) {
						inventory[2] = ItemStack.EMPTY;
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
				this.inventory[j] = new ItemStack(nbttagcompound);
			}
		}

		energy.deserializeNBT(compound.getCompoundTag("Energy"));

		if (compound.hasKey("FuelType")) {
			fuelType = FuelTypes.getFuelTypes().stream().filter((p)->p.getUID().equals(compound.getString("FuelType"))).findFirst().orElse(null);
		}

		if (compound.hasKey("Processing")) {
			output = new ItemStack(compound.getCompoundTag("POutput"));

			currentEnergy = compound.getInteger("PEnergy");
			energyNeeded = compound.getInteger("NEnergy");
		} else {
			output = ItemStack.EMPTY;
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

		if (!output.isEmpty()) {
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
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 ? stack.getItem() == ModItems.nuclearMaterial : (index == 2 && fuelType != null ? ItemStackUtil.areItemStacksEqual(stack, fuelType.getInputItem()) : false);
	}

	@Override
	public String getName() {
		return "container.nuclearsciences.fuelpacker.name";
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
		if (c == CapabilityUtil.TESLA_CONSUMER) {
			return true;
		}
		return super.hasCapability(c, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> c, EnumFacing facing) {
		if (c == CapabilityUtil.TESLA_CONSUMER) {
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

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		System.out.println("updatePacket");
		nbt.setBoolean("Running", running);
		return new SPacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound nbt = pkt.getNbtCompound();
		running = nbt.getBoolean("Running");
		world.markBlockRangeForRenderUpdate(pos, pos);
	}
}
