package rreeggkk.nuclearsciences.common.energy;

import net.minecraft.nbt.NBTTagCompound;

public class IntEnergyContainer extends AEnergyContainer {

	/**
	 * The amount of stored power.
	 */
	private int stored;

	/**
	 * The maximum amount of power that can be stored.
	 */
	private int capacity;

	/**
	 * The maximum amount of power that can be accepted.
	 */
	private int inputRate;

	/**
	 * The maximum amount of power that can be extracted
	 */
	private int outputRate;

	private boolean saveIO;

	public IntEnergyContainer(int capacity, int inOut, boolean saveIO) {
		this(capacity, inOut, inOut, saveIO);
	}

	public IntEnergyContainer(int capacity, int in, int out, boolean saveIO) {
		this(0, capacity, in, out, saveIO);
	}

	public IntEnergyContainer(int power, int capacity, int in, int out, boolean saveIO) {
		this.stored = power;
		this.capacity = capacity;
		this.inputRate = in;
		this.outputRate = out;
		this.saveIO = saveIO;
	}

	public IntEnergyContainer(NBTTagCompound nbt) {
		this(0,0, true);
		deserializeNBT(nbt);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("Power", this.stored);
		nbt.setInteger("Capacity", this.capacity);
		nbt.setBoolean("SaveIO", this.saveIO);
		if (saveIO) {
			nbt.setInteger("Input", this.inputRate);
			nbt.setInteger("Output", this.outputRate);
		}

		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.stored = nbt.getInteger("Power");

		if (nbt.hasKey("Capacity"))
			this.capacity = nbt.getInteger("Capacity");

		if (nbt.hasKey("SaveIO"))
			this.saveIO = nbt.getBoolean("SaveIO");

		if (nbt.hasKey("Input"))
			this.inputRate = nbt.getInteger("Input");

		if (nbt.hasKey("Output"))
			this.outputRate = nbt.getInteger("Output");

		if (this.stored > this.getCapacity())
			this.stored = this.getIntCapacity();
	}

	@Override
	public long getStoredPower() {
		return stored;
	}

	@Override
	public long getCapacity() {
		return capacity;
	}

	@Override
	public long takePower(long power, boolean simulated) {
		long removed = Math.min(this.stored, Math.min(outputRate, power));

		if (!simulated)
			this.stored -= removed;

		return removed;
	}

	@Override
	public long givePower(long power, boolean simulated) {
		long accepted = Math.min(this.getCapacity() - this.stored, Math.min(inputRate, power));

		if (!simulated)
			this.stored += accepted;

		return accepted;
	}
	
	@Override
	public int extractEnergy(int power, boolean simulated) {
		int removed = Math.min(this.stored, Math.min(outputRate, power));

		if (!simulated)
			this.stored -= removed;

		return removed;
	}
	
	@Override
	public int receiveEnergy(int power, boolean simulated) {
		int accepted = Math.min(this.getIntCapacity() - this.stored, Math.min(inputRate, power));

		if (!simulated)
			this.stored += accepted;

		return accepted;
	}
	
	@Override
	public double getFraction() {
		return (double)stored/capacity;
	}

	/**
	 * @return the stored
	 */
	public int getStored() {
		return stored;
	}

	/**
	 * @param stored the stored to set
	 */
	public void setStored(int stored) {
		this.stored = stored;
	}

	/**
	 * @return the inputRate
	 */
	public int getInputRate() {
		return inputRate;
	}

	/**
	 * @param inputRate the inputRate to set
	 */
	public void setInputRate(int inputRate) {
		this.inputRate = inputRate;
	}

	/**
	 * @return the outputRate
	 */
	public int getOutputRate() {
		return outputRate;
	}

	/**
	 * @param outputRate the outputRate to set
	 */
	public void setOutputRate(int outputRate) {
		this.outputRate = outputRate;
	}

	/**
	 * @return the saveIO
	 */
	public boolean isSaveIO() {
		return saveIO;
	}

	/**
	 * @param saveIO the saveIO to set
	 */
	public void setSaveIO(boolean saveIO) {
		this.saveIO = saveIO;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getIntCapacity() {
		return capacity;
	}

	@Override
	public int getEnergyStored() {
		return stored;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean canExtract() {
		return outputRate>0&&stored>0;
	}

	@Override
	public boolean canReceive() {
		return inputRate>0&&capacity-stored>0;
	}
}
