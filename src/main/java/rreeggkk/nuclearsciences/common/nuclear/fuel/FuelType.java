package rreeggkk.nuclearsciences.common.nuclear.fuel;

import org.apfloat.Apfloat;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class FuelType {
	protected String name;
	protected Apfloat massPerComponent;
	protected ItemStack item;
	protected ItemStack inputItem;
	
	public FuelType(String name, Apfloat gramsPerComponent) {
		this.name = name;
		this.massPerComponent = gramsPerComponent;
	}
	
	public FuelType(String name, Apfloat gramsPerComponent, ItemStack inputItem) {
		this.name = name;
		this.massPerComponent = gramsPerComponent;
		this.inputItem = inputItem;
	}

	public String getName() {
		return name;
	}
	
	public String getLocalizedName() {
		return I18n.format("nuclearsciences.fueltype." + name + ".name");
	}
	
	public Apfloat getGramsPerComponent() {
		return massPerComponent;
	}

	/**
	 * @return the item
	 */
	public ItemStack getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(ItemStack item) {
		this.item = item;
	}
	
	public ItemStack getInputItem() {
		return inputItem;
	}
}
