package rreeggkk.nuclearsciences.common.nuclear.element;

import java.util.HashMap;
import java.util.Optional;

import org.apfloat.Apfloat;

public interface IElement<T extends IElement<T>> {
	
	/**
	 * Used for getting the full name of this element
	 * 
	 * @return the full name of this element. Ex: Uranium, Plutonium, Copper, Iron
	 */
	public String getName();
	
	/**
	 * Used for getting the shortened name of this element
	 * 
	 * @return the symbol or short name of this element. Ex: U, Pu, Cu, Fe
	 */
	public String getShortname();
	
	/**
	 * This is used for determining automatic decay products. If this returns a value then this should be registered in the PeriodicTable.
	 * 
	 * @return the atomic number if it exists or even makes sense in the context of this element
	 */
	public Optional<Integer> getAtomicNumber();
	
	/**
	 * Used for getting a map of neutron # to isotope. Also functions as a list of isotopes
	 * 
	 * @return the map of neutron number to isotope
	 */
	public HashMap<Integer, AIsotope<BasicElement, ?>> getIsotopes();
	
	/**
	 * Used for getting the molar heat capacity of this element
	 * 
	 * @return the molar heat capacity of this element in J/molK
	 */
	public Apfloat getMolarHeatCapacity();
}
