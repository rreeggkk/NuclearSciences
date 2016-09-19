package rreeggkk.nuclearsciences.common.nuclear.element;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.decay.IDecayMode;

public class AIsotope<T extends IElement<T>, U extends AIsotope<T,U>> {
	private Apfloat decayPercent;
	private Apfloat atomicMass;
	private int neutronNumber;
	private String suffix;
	private T element;
	private IDecayMode<U> decayMode;
	private double thermalCapture, thermalFission;

	public AIsotope(int neutronNumber, double thermalCapture, double thermalFisison) {
		this.neutronNumber = neutronNumber;
		this.thermalCapture = thermalCapture;
		this.thermalFission = thermalFisison;
	}

	/**
	 * Initialize this isotope with an element. MUST BE CALLED BEFORE USE OF THIS ISOTOPE OR STABILITY NOT ENSURED
	 * 
	 * @param element the element that is the parent of this isotope
	 */
	public void init(T element) {
		this.element = element;
		suffix = "" + (neutronNumber + element.getAtomicNumber().orElse(0));
	}

	/**
	 * @return the decayPercent
	 */
	public Apfloat getDecayPercent() {
		return decayPercent;
	}

	/**
	 * @param decayPercent the decayPercent to set
	 */
	protected void setDecayI(IDecayMode<U> decayMode, Apfloat halflifeTicks) {
		this.decayPercent = new Apfloat(1, Constants.PRECISION).subtract(ApfloatMath.pow(new Apfloat(0.5, Constants.PRECISION), new Apfloat(1, Constants.PRECISION).divide(halflifeTicks)).precision(Constants.PRECISION));
		this.decayMode = decayMode;
	}

	/**
	 * @return the atomicMass
	 */
	public Apfloat getAtomicMass() {
		return atomicMass;
	}

	/**
	 * @param atomicMass the atomicMass to set
	 */
	protected void setAtomicMassI(Apfloat atomicMass) {
		this.atomicMass = atomicMass;
	}

	/**
	 * @return the neutronNumber
	 */
	public int getNeutronNumber() {
		return neutronNumber;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix the suffix to set
	 */
	protected void setSuffixI(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the element
	 */
	public T getElement() {
		return element;
	}

	/**
	 * @return the decayMode
	 */
	public IDecayMode<U> getDecayMode() {
		return decayMode;
	}

	public String getFullName() {
		return element.getName() + "-" + getSuffix();
	}

	public String getShortname() {
		return element.getShortname() + getSuffix();
	}
	
	public boolean isStable() {
		return decayMode == null;
	}
	
	@Override
	public String toString() {
		return getFullName();
	}
	
	public double getThermalCapture() {
		return thermalCapture;
	}
	
	public double getThermalFission() {
		return thermalFission;
	}
}
