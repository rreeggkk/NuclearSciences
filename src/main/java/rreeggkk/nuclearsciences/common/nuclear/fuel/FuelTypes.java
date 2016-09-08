package rreeggkk.nuclearsciences.common.nuclear.fuel;

import java.util.ArrayList;
import java.util.List;

import org.apfloat.Apfloat;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import rreeggkk.nuclearsciences.common.Constants;

public class FuelTypes {
	private static List<FuelType> fuelTypes = new ArrayList<>();
	
	public static final FuelType PELLET = new FuelType("pellet", new Apfloat(7, Constants.PRECISION), "nuclearsciences:pellet");
	public static final FuelType PEBBLE = new FuelType("pebble", new Apfloat(6, Constants.PRECISION), "nuclearsciences:pebble", new ItemStack(Items.COAL)); //TODO: Add graphite as input item
	public static final FuelType SALT = new FuelType("salt", new Apfloat(10, Constants.PRECISION), "nuclearsciences:salt", new ItemStack(Items.REDSTONE)); //TODO: Add salt as input item

	public static void registerDefaults() {
		register(PELLET);
		register(PEBBLE);
		register(SALT);
	}
	
	public static List<FuelType> getFuelTypes() {
		return fuelTypes;
	}
	
	public static <T extends FuelType> T register(T fuelType) {
		fuelTypes.add(fuelType);
		return fuelType;
	}
}
