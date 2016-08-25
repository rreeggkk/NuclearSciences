package io.github.rreeggkk.nuclearsciences.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.github.rreeggkk.nuclearsciences.common.energy.EnergyContainer;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

public class CompatUtil {
	public static boolean isTeslaLoaded() {
		return Loader.isModLoaded("tesla");
	}

	public static IBlockState getMoltenRTGFluidBlock() {
		return Blocks.LAVA.getDefaultState();
	}

	/**
	 * Safe to use if TESLA is not loaded
	 * 
	 * @param world the world object
	 * @param pos the position of the block to push from
	 * @param energy the energy container
	 */
	public static void tryPushEnergyFrom(World world, BlockPos pos, EnergyContainer energy) {
		if (isTeslaLoaded()) {
			pushEnergyFrom(world, pos, energy, EnumFacing.VALUES);
		}
	}

	/**
	 * Safe to use if TESLA is not loaded
	 * 
	 * @param world the world object
	 * @param pos the position of the block to push from
	 * @param energy the energy container
	 * @param directions the directions to try to push the energy into
	 */
	public static void tryPushEnergyFrom(World world, BlockPos pos, EnergyContainer energy, EnumFacing[] directions) {
		if (isTeslaLoaded()) {
			pushEnergyFrom(world, pos, energy, directions);
		}
	}

	@Optional.Method(modid="tesla")
	private static void pushEnergyFrom(World world, BlockPos pos, EnergyContainer energy, EnumFacing[] directions) {
		if (energy.getStored() == 0) {
			return;
		}
		Map<ITeslaConsumer, Long> facing = new HashMap<ITeslaConsumer, Long>(directions.length);
		long totalPower = 0;
		for (EnumFacing dir : directions) {
			TileEntity te = world.getTileEntity(pos.add(dir.getFrontOffsetX(), dir.getFrontOffsetY(), dir.getFrontOffsetZ()));
			if (te != null && !te.isInvalid() && te.hasCapability(CapabilityUtil.CAPABILITY_CONSUMER, dir.getOpposite())) {
				ITeslaConsumer cons = te.getCapability(CapabilityUtil.CAPABILITY_CONSUMER, dir.getOpposite());
				long power = cons.givePower(energy.getStored(), true);
				if (power > 0) {
					totalPower += power;
					facing.put(cons, power);
				}
			}
		}
		for (Entry<ITeslaConsumer, Long> e : facing.entrySet()) {
			long power = energy.takePower(e.getValue()/totalPower*energy.getStored(), true);
			energy.takePower(e.getKey().givePower(power, false), false);
		}
	}
}
