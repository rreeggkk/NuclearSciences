package rreeggkk.nuclearsciences.common.nuclear.decay;

import java.util.HashMap;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.element.IElement;
import rreeggkk.nuclearsciences.common.nuclear.registry.PeriodicTable;

public class BetaDecay<T extends AIsotope<? extends IElement<?>, T>> implements IDecayMode<T> {
	private T inputIsotope;
	//private Isotope<?> outputIsotope;
	private Apfloat energyPerMole;
	private HashMap<AIsotope<?,?>, Apfloat> outputRatios;
	
	@Override
	public void init(T isotope) {
		inputIsotope = isotope;
	}
	
	private void setup() {
		AIsotope<?,?> outputIsotope = PeriodicTable.getZN(
				inputIsotope.getElement().getAtomicNumber()
				.orElseThrow(()->new NullPointerException("Atomic Number not provided")) + 1,
				inputIsotope.getNeutronNumber() - 1);
		
		if (outputIsotope == null) {
			throw new NullPointerException("Output element is not valid for " + inputIsotope.getFullName());
		}
		
		Apfloat deltaMM = inputIsotope.getAtomicMass().precision(Constants.PRECISION)
				.subtract(outputIsotope.getAtomicMass().precision(Constants.PRECISION)); // ∆ g/mol positive = decreased
		
		energyPerMole = deltaMM.precision(Constants.PRECISION).divide(new Apfloat(1000, Constants.PRECISION)).multiply(Constants.C2).precision(Constants.PRECISION);
		
		outputRatios = new HashMap<AIsotope<?,?>, Apfloat>();
		outputRatios.put(outputIsotope, new Apfloat(1, Constants.PRECISION));
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
