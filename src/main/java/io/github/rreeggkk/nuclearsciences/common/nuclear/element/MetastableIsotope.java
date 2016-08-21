package io.github.rreeggkk.nuclearsciences.common.nuclear.element;

import org.apfloat.Apfloat;

import io.github.rreeggkk.nuclearsciences.common.Constants;
import io.github.rreeggkk.nuclearsciences.common.nuclear.decay.IDecayMode;

public class MetastableIsotope<T extends IElement<T>> extends AIsotope<T, MetastableIsotope<T>> {
	
	private String metaStr;

	public MetastableIsotope(int neutronNumber, int metastableNumber) {
		super(neutronNumber);
		this.metaStr = "m" + (metastableNumber > 0 ? metastableNumber : "");
	}


	/**
	 * @param decayPercent the decayPercent to set
	 */
	public MetastableIsotope<T> setDecay(IDecayMode<MetastableIsotope<T>> decayMode, Apfloat halflifeTicks) {
		setDecayI(decayMode, halflifeTicks);
		decayMode.init(this);
		return this;
	}

	/**
	 * @param atomicMass the atomicMass to set
	 */
	public MetastableIsotope<T> setAtomicMass(Apfloat atomicMass) {
		setAtomicMassI(atomicMass);
		return this;
	}

	/**
	 * @param atomicMass the atomicMass to set
	 */
	public MetastableIsotope<T> setAtomicMass(double atomicMass) {
		return setAtomicMass(new Apfloat(atomicMass, Constants.PRECISION));
	}

	/**
	 * @param suffix the suffix to set
	 */
	public MetastableIsotope<T> setSuffix(String suffix) {
		setSuffixI(suffix);
		return this;
	}

	@Override
	public String getFullName() {
		return super.getFullName() + metaStr;
	}
}
