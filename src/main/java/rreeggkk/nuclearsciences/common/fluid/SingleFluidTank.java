package rreeggkk.nuclearsciences.common.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class SingleFluidTank extends FluidTank {
	
	private Fluid fluid;

	/**
	 * @param fluid
	 * @param amount
	 * @param capacity
	 */
	public SingleFluidTank(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
		this.fluid = fluid;
	}

	/**
	 * @param fluidStack
	 * @param capacity
	 */
	public SingleFluidTank(FluidStack fluidStack, int capacity) {
		super(fluidStack, capacity);
		this.fluid = fluidStack.getFluid();
	}

	/**
	 * @param capacity
	 */
	public SingleFluidTank(Fluid fluid, int capacity) {
		this(fluid, 0, capacity);
	}
	
	@Override
	public boolean canFillFluidType(FluidStack fluid) {
		return fluid.getFluid() == this.fluid;
	}
	
	public Fluid getFluidType() {
		return fluid;
	}

	public double getFraction() {
		return (double)getFluidAmount()/getCapacity();
	}
}
