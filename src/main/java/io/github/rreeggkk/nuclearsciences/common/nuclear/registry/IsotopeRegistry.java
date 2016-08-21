package io.github.rreeggkk.nuclearsciences.common.nuclear.registry;

import java.util.LinkedHashMap;

import io.github.rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;

public final class IsotopeRegistry {
	
	private static LinkedHashMap<String, AIsotope<?,?>> registry = new LinkedHashMap<String, AIsotope<?,?>>();

	public static AIsotope<?,?> get(String name) {
		return registry.get(name);
	}

	public static void register(AIsotope<?, ?> isotope) {
		registry.put(isotope.getFullName(), isotope);
	}
	
	public static LinkedHashMap<String, AIsotope<?,?>> getRegistry() {
		return registry;
	}
}
