package rreeggkk.nuclearsciences.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import rreeggkk.nuclearsciences.common.energy.AEnergyContainer;
import scala.actors.threadpool.Arrays;

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
	public static void tryPushEnergyFrom(World world, BlockPos pos, AEnergyContainer energy) {
		tryPushEnergyFrom(world, pos, energy, EnumFacing.VALUES);
	}

	/**
	 * Safe to use if TESLA is not loaded
	 * 
	 * @param world the world object
	 * @param pos the position of the block to push from
	 * @param energy the energy container
	 * @param directions the directions to try to push the energy into
	 */
	public static void tryPushEnergyFrom(World world, BlockPos pos, AEnergyContainer energy, EnumFacing... directions) {
		@SuppressWarnings("unchecked")
		List<EnumFacing> facings = new ArrayList<>(Arrays.asList(directions));
		if (isTeslaLoaded()) {
			pushTeslaEnergyFrom(world, pos, energy, facings);
		} else {
			pushForgeEnergyFrom(world, pos, energy, facings);
		}
	}

	@Optional.Method(modid="tesla")
	private static void pushTeslaEnergyFrom(World world, BlockPos pos, AEnergyContainer energy, List<EnumFacing> directions) {
		if (energy.getStoredPower() == 0) return;
		Map<ITeslaConsumer, Long> facing = new HashMap<>(directions.size());
		long totalPower = 0;
		Iterator<EnumFacing> iter = directions.iterator();
		while (iter.hasNext()) {
			EnumFacing dir = iter.next();
			TileEntity te = world.getTileEntity(pos.offset(dir));
			if (te != null && !te.isInvalid() && te.hasCapability(CapabilityUtil.TESLA_CONSUMER, dir.getOpposite())) {
				ITeslaConsumer cons = te.getCapability(CapabilityUtil.TESLA_CONSUMER, dir.getOpposite());
				long power = cons.givePower(energy.getStoredPower(), true);
				if (power > 0) {
					if (energy.getStoredPower()-totalPower >= power) {
						totalPower += power;
					} else {
						totalPower = energy.getStoredPower();
					}
					iter.remove();
					facing.put(cons, power);
				}
			}
		}
		long originalPower = energy.getStoredPower();
		for (Entry<ITeslaConsumer, Long> e : facing.entrySet()) {
			long power = energy.takePower(e.getValue()*originalPower/totalPower, true);
			energy.takePower(e.getKey().givePower(power, false), false);
		}
	}

	private static void pushForgeEnergyFrom(World world, BlockPos pos, AEnergyContainer energy, List<EnumFacing> directions) {
		if (energy.getEnergyStored() == 0) return;
		Map<IEnergyStorage, Integer> facing = new HashMap<>(directions.size());
		int totalPower = 0;
		Iterator<EnumFacing> iter = directions.iterator();
		while (iter.hasNext()) {
			EnumFacing dir = iter.next();
			TileEntity te = world.getTileEntity(pos.offset(dir));
			if (te != null && !te.isInvalid() && te.hasCapability(CapabilityUtil.FORGE_ENERGY_STORAGE, dir.getOpposite())) {
				IEnergyStorage cons = te.getCapability(CapabilityUtil.FORGE_ENERGY_STORAGE, dir.getOpposite());
				int power = cons.receiveEnergy(energy.getEnergyStored(), true);
				if (power > 0) {
					if (energy.getMaxEnergyStored()-totalPower >= power) {
						totalPower += power;
					} else {
						totalPower = energy.getEnergyStored();
					}
					iter.remove();
					facing.put(cons, power);
				}
			}
		}
		int originalPower = energy.getEnergyStored();
		for (Entry<IEnergyStorage, Integer> e : facing.entrySet()) {
			int power = energy.extractEnergy(e.getValue()*originalPower/totalPower, true);
			energy.extractEnergy(e.getKey().receiveEnergy(power, false), false);
		}
	}
}
