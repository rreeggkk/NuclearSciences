package rreeggkk.nuclearsciences.common.nuclear.decay;

import java.util.HashMap;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.element.IElement;

public class MultiDecay<T extends AIsotope<? extends IElement<?>, T>> implements IDecayMode<T> {
	
	private IDecayMode<T>[] decays;
	private Apfloat[] fractions;
	private Apfloat energyPerMole;
	private HashMap<AIsotope<?,?>, Apfloat> outputRatios;
	
	/**
	 * Objects should be in the order of IDecayMode, Apfloat, IDecayMode, Apfloat
	 * The Apfloat should correspond to the fraction of the time that the previous decay mode should be used
	 * 
	 * 
	 * @param totalInput the total input to this method 
	 */
	@SuppressWarnings("unchecked")
	public MultiDecay(Object ... totalInput) {
		decays = (IDecayMode<T>[]) new IDecayMode[totalInput.length/2];
		fractions = new Apfloat[totalInput.length/2];
		
		for (int i = 0; i<totalInput.length/2; i++) {
			decays[i] = (IDecayMode<T>) totalInput[i*2];
			fractions[i] = ((Apfloat) totalInput[i*2+1]).precision(Constants.PRECISION);
		}
	}
	
	@Override
	public void init(T isotope) {
		for (int i = 0; i<decays.length; i++) {
			decays[i].init(isotope);
		}
	}
	
	private void setup() {
		outputRatios = new HashMap<AIsotope<?,?>, Apfloat>();
		energyPerMole = new Apfloat(0, Constants.PRECISION);
		
		for (int i = 0; i<decays.length; i++) {
			HashMap<AIsotope<?,?>, Apfloat> out = decays[i].getOutputs();
			for (AIsotope<?,?> iso : out.keySet()) {
				outputRatios.put(iso, out.get(iso).precision(Constants.PRECISION).multiply(fractions[i]).add(outputRatios.getOrDefault(iso, new Apfloat(0)).precision(Constants.PRECISION)));
			}
			
			energyPerMole = energyPerMole.add(decays[i].getEnergyPerMolReleased().precision(Constants.PRECISION).multiply(fractions[i])).precision(Constants.PRECISION);
		}
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
