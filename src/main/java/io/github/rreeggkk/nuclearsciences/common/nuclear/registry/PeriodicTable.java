package io.github.rreeggkk.nuclearsciences.common.nuclear.registry;

import java.util.HashMap;

import io.github.rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import io.github.rreeggkk.nuclearsciences.common.nuclear.element.IElement;

/**
 * Z is the number of protons
 * N is the number of neutrons
 * A is the total number of particles in the nucleus (protons + neutrons)(Z + N)
 */
public final class PeriodicTable {
	
	private static HashMap<Integer, IElement<?>> registry = new HashMap<Integer, IElement<?>>();

	public static AIsotope<?,?> getZA(int z, int a) {
		return getZN(z, a-z);
	}

	public static AIsotope<?,?> getZN(int z, int n) {
		return registry.get(z).getIsotopes().get(n);
	}

	public static void registerElement(IElement<?> element) {
		registry.put(element.getAtomicNumber().orElseThrow(()->new NullPointerException("Atomic Number not provided")), element);
	}

}
