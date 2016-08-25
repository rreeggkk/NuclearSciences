package rreeggkk.nuclearsciences.common.nuclear.decay;

import java.util.HashMap;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.element.IElement;

public interface IDecayMode<T extends AIsotope<? extends IElement<?>, T>> {
	
	/**
	 * Initialize this DecayMode using the isotope
	 * Must be called before any other method. If this is not called first the other methods may cause crashes
	 * 
	 * WARNING: this may be called before all isotopes are registered. 
	 * It is recommended to do isotope based initialization when the data is first needed.
	 * 
	 * @param isotope they isotope to initialize with
	 */
	public void init(T isotope);
	
	/**
	 * Gets the amount of energy released by this reaction per mol of input isotope in Joules
	 * 
	 * @return the amount of energy released
	 */
	public Apfloat getEnergyPerMolReleased();
	
	/**
	 * Get the products of the decay reaction as well as the ratios that they are in
	 * 
	 * @return a hashmap of the products as well as the proportion of that product relative to the amount of moles that decayed
	 */
	public HashMap<AIsotope<?,?>, Apfloat> getOutputs();
}
