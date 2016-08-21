package io.github.rreeggkk.nuclearsciences.common.nuclear.simulation;

import java.util.HashMap;

import org.apfloat.Apfloat;

import io.github.rreeggkk.nuclearsciences.common.Constants;
import io.github.rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import io.github.rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;

public class DecaySimulation {
	public static Apfloat simulateDecay(HashMap<String, Apfloat> materials) {
		Apfloat deltaHeat = new Apfloat(0, Constants.PRECISION);
		HashMap<String, Apfloat> oldMat = new HashMap<>(materials);

		for (String str : oldMat.keySet()) {
			AIsotope<?,?> iso = IsotopeRegistry.get(str);
			if (iso != null && iso.getDecayMode() != null) {

				Apfloat decayMoles = iso.getDecayPercent().multiply(oldMat.get(str)).precision(Constants.PRECISION);

				materials.put(str, materials.get(str).subtract(decayMoles).precision(Constants.PRECISION));

				deltaHeat = deltaHeat.add(iso.getDecayMode().getEnergyPerMolReleased().precision(Constants.PRECISION).multiply(decayMoles)).precision(Constants.PRECISION);

				HashMap<AIsotope<?,?>, Apfloat> outputs = iso.getDecayMode().getOutputs();
				for (AIsotope<?,?> out : outputs.keySet()) {
					Apfloat trueOutMol = outputs.get(out).precision(Constants.PRECISION).multiply(decayMoles);

					String name = out.getFullName();

					materials.put(name, trueOutMol.add(materials.getOrDefault(name, new Apfloat(0, Constants.PRECISION))).precision(Constants.PRECISION));
				}
			}
		}

		return deltaHeat;
	}
}
