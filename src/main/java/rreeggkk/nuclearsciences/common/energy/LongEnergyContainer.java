package rreeggkk.nuclearsciences.common.energy;

import net.minecraft.nbt.NBTTagCompound;

public class LongEnergyContainer extends AEnergyContainer {

	/**
	 * The amount of stored power.
	 */
	private long stored;

	/**
	 * The maximum amount of power that can be stored.
	 */
	private long capacity;

	/**
	 * The maximum amount of power that can be accepted.
	 */
	private long inputRate;

	/**
	 * The maximum amount of power that can be extracted
	 */
	private long outputRate;

	private boolean saveIO;

	public LongEnergyContainer(long capacity, long inOut, boolean saveIO) {
		this(capacity, inOut, inOut, saveIO);
	}

	public LongEnergyContainer(long capacity, long in, long out, boolean saveIO) {
		this(0, capacity, in, out, saveIO);
	}

	public LongEnergyContainer(long power, long capacity, long in, long out, boolean saveIO) {
		this.stored = power;
		this.capacity = capacity;
		this.inputRate = in;
		this.outputRate = out;
		this.saveIO = saveIO;
	}

	public LongEnergyContainer(NBTTagCompound nbt) {
		this(0,0, true);
		deserializeNBT(nbt);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setLong("Power", this.stored);
		nbt.setLong("Capacity", this.capacity);
		nbt.setBoolean("SaveIO", this.saveIO);
		if (saveIO) {
			nbt.setLong("Input", this.inputRate);
			nbt.setLong("Output", this.outputRate);
		}

		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.stored = nbt.getLong("Power");

		if (nbt.hasKey("Capacity"))
			this.capacity = nbt.getLong("Capacity");

		if (nbt.hasKey("SaveIO"))
			this.saveIO = nbt.getBoolean("SaveIO");

		if (nbt.hasKey("Input"))
			this.inputRate = nbt.getLong("Input");

		if (nbt.hasKey("Output"))
			this.outputRate = nbt.getLong("Output");

		if (this.stored > this.getCapacity())
			this.stored = this.getCapacity();
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

	/**
	 * @return the stored
	 */
	public long getStored() {
		return stored;
	}

	/**
	 * @param stored the stored to set
	 */
	public void setStored(long stored) {
		this.stored = stored;
	}

	/**
	 * @return the inputRate
	 */
	public long getInputRate() {
		return inputRate;
	}

	/**
	 * @param inputRate the inputRate to set
	 */
	public void setInputRate(long inputRate) {
		this.inputRate = inputRate;
	}

	/**
	 * @return the outputRate
	 */
	public long getOutputRate() {
		return outputRate;
	}

	/**
	 * @param outputRate the outputRate to set
	 */
	public void setOutputRate(long outputRate) {
		this.outputRate = outputRate;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
	
	public double getFraction() {
		return (double)stored/capacity;
	}
}
