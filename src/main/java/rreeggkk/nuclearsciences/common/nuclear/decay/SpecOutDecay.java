package rreeggkk.nuclearsciences.common.nuclear.decay;

import java.util.HashMap;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.element.IElement;
import rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;

public class SpecOutDecay<T extends AIsotope<? extends IElement<?>, T>> implements IDecayMode<T> {
	private T inputIsotope;
	private String[] outputIsotopeNames;
	//private Isotope<?> outputIsotope;
	private Apfloat energyPerMole;
	private HashMap<AIsotope<?,?>, Apfloat> outputRatios;
	
	public SpecOutDecay(String ... isotopeName) {
		outputIsotopeNames = isotopeName;
	}
	
	@Override
	public void init(T isotope) {
		inputIsotope = isotope;
	}
	
	private void setup() {
		outputRatios = new HashMap<AIsotope<?,?>, Apfloat>();
		Apfloat totalOutputMass = new Apfloat(0, Constants.PRECISION);
		
		for (String isoName : outputIsotopeNames) {
			AIsotope<?,?> iso = IsotopeRegistry.get(isoName);
			
			if (iso == null) {
				throw new NullPointerException("Output isotope: " + iso + " not found");
			}
			
			totalOutputMass = totalOutputMass.add(iso.getAtomicMass().precision(Constants.PRECISION)).precision(Constants.PRECISION);
			
			outputRatios.put(iso, new Apfloat(1, Constants.PRECISION));
		}
		
		
		Apfloat deltaMM = inputIsotope.getAtomicMass().precision(Constants.PRECISION)
				.subtract(totalOutputMass); // ∆ g/mol positive = decreased
		
		energyPerMole = deltaMM.precision(Constants.PRECISION).divide(new Apfloat(1000, Constants.PRECISION)).multiply(Constants.C2).precision(Constants.PRECISION);
		
		outputIsotopeNames = null;
	}

	@Override
	public Apfloat getEnergyPerMolReleased() {
		if (energyPerMole == null) {
			setup();
		}
		return energyPerMole;
	}

	@Override
	public HashMap<AIsotope<?,?>, Apfloat> getOutputs() {
		if (outputRatios == null) {
			setup();
		}
		return outputRatios;
	}

}
