package rreeggkk.nuclearsciences.common.tile.centrifuge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

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
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.energy.IntEnergyContainer;
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;
import rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import rreeggkk.nuclearsciences.common.util.NuclearMaterialUtil;

public class TileEntityVaporizer extends TileEntity implements ITickable, ISidedInventory {

	private IntEnergyContainer energy;

	private ItemStack[] inventory = new ItemStack[3];

	private List<TileEntityCentrifuge> centrifuges = new ArrayList<TileEntityCentrifuge>();
	private TileEntityCondensor lightCondensor, heavyCondensor; 

	private AIsotope<?,?> desiredIsotope;
	private double productAssay = 0.040;
	private double tailsAssay =   0.003;

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

			if (inventory[0] != null && desiredIsotope != null) {
				Apfloat feedMass = ModItems.nuclearMaterial.getTotalMass(inventory[0]).precision(Constants.PRECISION);
				Apfloat feedAssay = ModItems.nuclearMaterial.getContentsMass(inventory[0]).get(desiredIsotope).divide(feedMass).precision(Constants.PRECISION);

				Apfloat feedProductRatio = new Apfloat(productAssay, Constants.PRECISION).subtract(new Apfloat(tailsAssay, Constants.PRECISION)).divide(feedAssay.subtract(new Apfloat(tailsAssay, Constants.PRECISION)));
				Apfloat tailsProductRatio = new Apfloat(productAssay, Constants.PRECISION).subtract(feedAssay).divide(feedAssay.subtract(new Apfloat(tailsAssay, Constants.PRECISION)));

				Apfloat maxSWU = new Apfloat(centrifuges.size()).multiply(new Apfloat(NuclearSciences.instance.config.SWUPerCentrifugeTick, Constants.PRECISION));
				Apfloat maxEn = maxSWU.multiply(new Apfloat(NuclearSciences.instance.config.energyPerSWU, Constants.PRECISION));

				int enSize = maxEn.multiply(new Apfloat(2)).ceil().intValue();
				energy.setCapacity(enSize);
				energy.setInputRate(enSize);
				energy.setOutputRate(enSize);

				Apfloat swu = maxSWU;
				if (maxEn.compareTo(new Apfloat(energy.getStored())) < 0) {
					swu = new Apfloat(energy.getStored()).divide(new Apfloat(NuclearSciences.instance.config.energyPerSWU, Constants.PRECISION));
				}

				Apfloat productMass = feedMass.divide(feedProductRatio).precision(Constants.PRECISION);
				Apfloat tailsMass = productMass.multiply(tailsProductRatio).precision(Constants.PRECISION);

				Apfloat feedSWU = productMass.multiply(value(productAssay)).add(tailsMass.multiply(value(tailsAssay))).subtract(feedMass.multiply(value(feedAssay)));
				boolean removeStack = false;
				if (feedSWU.compareTo(swu) < 0) {
					swu = feedSWU;
					removeStack = true;
				}

				Apfloat energy = swu.multiply(new Apfloat(NuclearSciences.instance.config.energyPerSWU, Constants.PRECISION));
				Apfloat feed = swu.multiply(feedProductRatio).divide(value(productAssay).add(value(tailsAssay).multiply(tailsProductRatio)).add(value(feedAssay).multiply(feedProductRatio)));
				Apfloat product = feed.divide(feedProductRatio).precision(Constants.PRECISION);
				Apfloat tail = product.multiply(tailsProductRatio).precision(Constants.PRECISION);

				this.energy.takePower(energy.floor().longValue(), false);

				HashMap<AIsotope<?,?>, Apfloat> feedContents = ModItems.nuclearMaterial.getContentsMass(inventory[0]);
				HashMap<AIsotope<?,?>, Apfloat> productContents = new HashMap<>();
				HashMap<AIsotope<?,?>, Apfloat> tailsContents = new HashMap<>();

				NuclearMaterialUtil.calculateOutputs(feedContents, productContents, tailsContents, desiredIsotope, productAssay, tailsAssay, product, tail);

				if (removeStack) {
					inventory[0] = null;
				} else {
					ModItems.nuclearMaterial.setContentsMass(inventory[0], feedContents);
				}

				if (inventory[1] == null) {
					inventory[1] = new ItemStack(ModItems.nuclearMaterial);
				}
				ModItems.nuclearMaterial.setContentsMass(inventory[1], NuclearMaterialUtil.sumMassMaps(productContents, ModItems.nuclearMaterial.getContentsMass(inventory[1])));

				if (inventory[2] == null) {
					inventory[2] = new ItemStack(ModItems.nuclearMaterial);
				}
				ModItems.nuclearMaterial.setContentsMass(inventory[2], NuclearMaterialUtil.sumMassMaps(tailsContents, ModItems.nuclearMaterial.getContentsMass(inventory[2])));
			}
		}
	}

	private Apfloat value(double input) {
		return new Apfloat((1-2*input) * Math.log((1-input)/input), Constants.PRECISION);
	}

	private Apfloat value(Apfloat input) {
		return new Apfloat(1).subtract(new Apfloat(2).multiply(input)).multiply(ApfloatMath.log(new Apfloat(1).subtract(input).divide(input))).precision(Constants.PRECISION);
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
	}

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
		if (compound.hasKey("Isotope")) {
			desiredIsotope = IsotopeRegistry.get(compound.getString("Isotope"));
		}
		productAssay = compound.getDouble("ProductAssay");
		tailsAssay = compound.getDouble("TailsAssay");
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
		if (desiredIsotope != null) {
			compound.setString("Isotope", desiredIsotope.getFullName());
		}
		compound.setDouble("ProductAssay", productAssay);
		compound.setDouble("TailsAssay", tailsAssay);
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
		return index == 0 && stack.getItem() == ModItems.nuclearMaterial && ModItems.nuclearMaterial.getContentsMass(stack).containsKey(desiredIsotope);
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

	public int[] getSlotsForFaceCondensor(EnumFacing side, TileEntityCondensor condensor) {
		if (condensor == lightCondensor) {
			return new int[]{1};
		}
		if (condensor == heavyCondensor) {
			return new int[]{2};
		}
		return new int[]{};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 0;
	}

	public boolean canExtractItemCondensor(int index, ItemStack stack, EnumFacing direction, TileEntityCondensor condensor) {
		if (condensor == lightCondensor) {
			return index == 1;
		}
		if (condensor == heavyCondensor) {
			return index == 2;
		}
		return false;
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
