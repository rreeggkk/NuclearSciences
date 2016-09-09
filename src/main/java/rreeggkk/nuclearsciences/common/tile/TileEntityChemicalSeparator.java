package rreeggkk.nuclearsciences.common.tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apfloat.Apfloat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.energy.IntEnergyContainer;
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.element.IElement;
import rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;
import rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import rreeggkk.nuclearsciences.common.util.ItemStackUtil;
import rreeggkk.nuclearsciences.common.util.RandomUtil;

public class TileEntityChemicalSeparator extends TileEntity implements ISidedInventory, ITickable {

	private IntEnergyContainer energy;

	private ItemStack[] inventory = new ItemStack[2];

	private int currentEnergy;
	private ItemStack output;
	private int energyNeeded;

	private boolean running;

	public TileEntityChemicalSeparator() {
		energy = new IntEnergyContainer(5000, 5000, 80, false);
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (output == null) {
				if (inventory[0] != null && inventory[0].getItem() == ModItems.nuclearMaterial) {
					int numElements = getNumElements(inventory[0]);
					if (numElements<=1) {
						energyNeeded = 0;
						currentEnergy = 0;
						output = inventory[0].splitStack(1);
						if (inventory[0].stackSize == 0) {
							inventory[0] = null;
						}
					} else if (numElements > 1) {
						energyNeeded = NuclearSciences.instance.config.chemicalSeparatorEnergyPerOperation;
						currentEnergy = 0;
						ItemStack[] outputs = getOutput(inventory[0]);

						output = outputs[1];
						inventory[0] = outputs[0];
						if (!running){
							running = true;
							worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
						}
					}
				} else if (running) {
					running = false;
					worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
				}
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
					} else if (ItemStackUtil.areItemStacksEqual(inventory[1], output)) {
						inventory[1].stackSize += output.stackSize;
						output = null;
					} else if (running) {
						running = false;
						worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
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

	private boolean isValidInput(ItemStack stack) {
		return stack.getItem() == ModItems.nuclearMaterial && getNumElements(stack)>1;
	}

	private int getNumElements(ItemStack stack) {
		return (int) ModItems.nuclearMaterial.getContentsMass(stack).keySet().stream().map((i)->i.getElement()).distinct().count();
	}

	private ItemStack[] getOutput(ItemStack stack) {
		HashMap<String, Apfloat> contents = ModItems.nuclearMaterial.getContents(stack);

		IElement<?> element = RandomUtil.randomItem(ModItems.nuclearMaterial.getContentsMass(stack).keySet().stream().map((i)->i.getElement()).distinct().collect(Collectors.toList()));

		HashMap<String, Apfloat> newContents = new HashMap<>();

		Iterator<Entry<String, Apfloat>> iter = contents.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Apfloat> e = iter.next();
			AIsotope<?,?> iso = IsotopeRegistry.get(e.getKey());
			if (iso != null && iso.getElement() == element) {
				iter.remove();
				newContents.put(e.getKey(), e.getValue());
			}
		}

		ItemStack[] outputs = new ItemStack[2];

		//New Input
		outputs[0] = ModItems.nuclearMaterial.setContents(new ItemStack(ModItems.nuclearMaterial), contents);

		//New Output
		outputs[1] = ModItems.nuclearMaterial.setContents(new ItemStack(ModItems.nuclearMaterial), newContents);

		return outputs;
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
		return index == 0 && isValidInput(stack);
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
		return "container.nuclearsciences.chemicalseparator.name";
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
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
	}
}
