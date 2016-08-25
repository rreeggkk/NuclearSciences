package io.github.rreeggkk.nuclearsciences.common.nuclear;

import java.util.HashMap;
import java.util.Map;

public class NuclearOreType {
	
	public static final NuclearOreType NATURAL_URANIUM = new NuclearOreType("Uranium-235", 0.0072, "Uranium-238", 0.9928);
	public static final NuclearOreType THORIUM = new NuclearOreType("Thorium-232", 1);
	
	private Map<String, Double> oreMap = new HashMap<>();
	
	/**
	 * MUST BE ALTERNATING String and Double
	 * 
	 * @param inputs
	 */
	public NuclearOreType(Object ... inputs) {
		for (int i = 0; i<inputs.length/2; i++) {
			oreMap.put((String)inputs[2*i], ((Number)inputs[2*i+1]).doubleValue());
		}
	}
	
	public Map<String, Double> getOreMap() {
		return oreMap;
	}
}
