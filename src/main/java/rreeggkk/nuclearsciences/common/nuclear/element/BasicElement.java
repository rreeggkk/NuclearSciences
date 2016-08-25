package rreeggkk.nuclearsciences.common.nuclear.element;

import java.util.HashMap;
import java.util.Optional;

import org.apfloat.Apfloat;

import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;
import rreeggkk.nuclearsciences.common.nuclear.registry.PeriodicTable;

public class BasicElement implements IElement<BasicElement> {
	
	private String name;
	private String shortName;
	private int atomicNumber;
	private HashMap<Integer, AIsotope<BasicElement, ?>> isotopes;
	private Apfloat molarHeatCapacity;

	/**
	 * Creates a new Basic Element and Registers it to the PeriodicTable
	 * 
	 * @param name the full name of this element. Ex: Uranium, Plutonium, Copper, Iron
	 * @param shortName the symbol or short name of this element. Ex: U, Pu, Cu, Fe
	 * @param atomicNumber the atomic number of this element
	 * @param heatCapacity the molar heat capacity of this element in J/molK
	 */
	public BasicElement(String name, String shortName, int atomicNumber, double heatCapacity) {
		this(name, shortName, atomicNumber, new Apfloat(heatCapacity));
	}

	/**
	 * Creates a new Basic Element and Registers it to the PeriodicTable
	 * 
	 * @param name the full name of this element. Ex: Uranium, Plutonium, Copper, Iron
	 * @param shortName the symbol or short name of this element. Ex: U, Pu, Cu, Fe
	 * @param atomicNumber the atomic number of this element
	 * @param heatCapacity the molar heat capacity of this element in J/molK
	 */
	public BasicElement(String name, String shortName, int atomicNumber, Apfloat heatCapacity) {
		this.name = name;
		this.shortName = shortName;
		this.atomicNumber = atomicNumber;
		isotopes = new HashMap<Integer, AIsotope<BasicElement, ?>>();
		PeriodicTable.registerElement(this);
		this.molarHeatCapacity = heatCapacity.precision(Constants.PRECISION);
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getShortname() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortname(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public Optional<Integer> getAtomicNumber() {
		return Optional.of(atomicNumber);
	}

	@Override
	public HashMap<Integer, AIsotope<BasicElement, ?>> getIsotopes() {
		return isotopes;
	}
	
	/**
	 * Adds an isotope to this element and registers it to the isotope registry
	 * 
	 * @param isotope the new isotope
	 */
	public BasicElement addIsotope(AIsotope<BasicElement,?> isotope) {
		isotope.init(this);
		isotopes.put(isotope.getNeutronNumber(), isotope);
		IsotopeRegistry.register(isotope);
		return this;
	}
	
	/**
	 * Initializes this isotope with this element and adds it to the isotope registry.
	 * THIS DOES NOT ADD THE ISOTOPE TO THIS ELEMENTS LIST OF ISOTOPES
	 * (To be used with metastable isotopes or other isomers)
	 * 
	 * @param isotope the new isotope
	 */
	public BasicElement initIsotope(AIsotope<BasicElement,?> isotope) {
		isotope.init(this);
		IsotopeRegistry.register(isotope);
		return this;
	}

	@Override
	public Apfloat getMolarHeatCapacity() {
		return molarHeatCapacity;
	}
}
