package rreeggkk.nuclearsciences.common.nuclear.element;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.decay.IDecayMode;

public class Isotope<T extends IElement<T>> extends AIsotope<T, Isotope<T>> {
	public Isotope(int neutronNumber, double thermalCapture, double thermalFisison) {
		super(neutronNumber, thermalCapture, thermalFisison);
	}

	/**
	 * @param decayPercent the decayPercent to set
	 */
	public Isotope<T> setDecay(IDecayMode<Isotope<T>> decayMode, Apfloat halflifeTicks) {
		setDecayI(decayMode, halflifeTicks);
		decayMode.init(this);
		return this;
	}

	/**
	 * @param atomicMass the atomicMass to set
	 */
	public Isotope<T> setAtomicMass(Apfloat atomicMass) {
		setAtomicMassI(atomicMass);
		return this;
	}

	/**
	 * @param atomicMass the atomicMass to set
	 */
	public Isotope<T> setAtomicMass(double atomicMass) {
		return setAtomicMass(new Apfloat(atomicMass, Constants.PRECISION));
	}

	/**
	 * @param suffix the suffix to set
	 */
	public Isotope<T> setSuffix(String suffix) {
		setSuffixI(suffix);
		return this;
	}
}
