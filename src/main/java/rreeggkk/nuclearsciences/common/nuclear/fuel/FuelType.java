package rreeggkk.nuclearsciences.common.nuclear.fuel;

import org.apfloat.Apfloat;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class FuelType {
	protected String name, uid;
	protected Apfloat massPerComponent;
	protected ItemStack item;
	protected ItemStack inputItem;
	
	public FuelType(String name, Apfloat gramsPerComponent, String uid) {
		this.name = name;
		this.massPerComponent = gramsPerComponent;
		this.uid = uid;
	}
	
	public FuelType(String name, Apfloat gramsPerComponent, String uid, ItemStack inputItem) {
		this.name = name;
		this.massPerComponent = gramsPerComponent;
		this.inputItem = inputItem;
		this.uid = uid;
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
	public ItemStack getOutputItem() {
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
	
	public String getUID() {
		return uid;
	}
}
