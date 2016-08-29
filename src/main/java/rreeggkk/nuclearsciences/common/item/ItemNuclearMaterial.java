package rreeggkk.nuclearsciences.common.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apfloat.Apfloat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.nuclear.registry.IsotopeRegistry;
import rreeggkk.nuclearsciences.common.util.MapUtil;
import rreeggkk.nuclearsciences.common.util.TextUtil;

public class ItemNuclearMaterial extends ItemNSBase {
	public ItemNuclearMaterial() {
		super("material");

		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (!GuiScreen.isShiftKeyDown()) {
			tooltip.add(I18n.format("text.nuclearsciences.material.shift"));
		} else {			
			Map<AIsotope<?,?>, Apfloat> metalMap = getContentsMass(stack);
			Apfloat totalWeight = getTotalMass(metalMap);

			metalMap = MapUtil.sortByValue(metalMap, false);

			Apfloat hundred = new Apfloat(100);

			boolean ctrlNeedsDisplay = false;
			for (Entry<AIsotope<?,?>, Apfloat> e : metalMap.entrySet()) {
				Apfloat percent = e.getValue().divide(totalWeight).multiply(hundred);

				if (GuiScreen.isCtrlKeyDown() || percent.multiply(hundred).compareTo(new Apfloat(0.01)) >= 0) {
					tooltip.add(e.getKey().getFullName()
							+ ": "
							+ TextUtil.getUnitString(e.getValue(), 16, "g", true, true)
							+ " "
							+ String.format("%.2f", percent.doubleValue()) + "%");
				} else {
					ctrlNeedsDisplay = true;
				}
			}

			if (ctrlNeedsDisplay) {
				tooltip.add(I18n.format("text.nuclearsciences.material." + (Minecraft.IS_RUNNING_ON_MAC ? "command" : "control")));
			}

			tooltip.add(I18n.format("text.nuclearsciences.material.totalMass", TextUtil.getUnitString(totalWeight, 16, "g", true, true)));
		}
	}

	public HashMap<String, Apfloat> getContents(ItemStack stack) {
		if (stack.getItem() != this) return null;

		HashMap<String, Apfloat> metalMap = new HashMap<String, Apfloat>();

		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null) {
			return new HashMap<String, Apfloat>();
		}

		NBTTagList metals = compound.getTagList("Metals", NBT.TAG_COMPOUND);

		for (int i = metals.tagCount() - 1; i >= 0; i--) {
			NBTTagCompound metal = metals.getCompoundTagAt(i);

			String name = metal.getString("Name");
			Apfloat amount = new Apfloat(metal.getString("Quantity")).precision(128);

			metalMap.put(name, amount);
		}

		return metalMap;
	}

	public ItemStack setContents(ItemStack stack, HashMap<String, Apfloat> data) {
		if (stack.getItem() != this) return null;

		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null) {
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		NBTTagList metals = new NBTTagList();

		for (Entry<String, Apfloat> e : data.entrySet()) {
			NBTTagCompound metal = new NBTTagCompound();

			metal.setString("Name", e.getKey());
			metal.setString("Quantity", e.getValue().toString());

			metals.appendTag(metal);
		}
		compound.setTag("Metals", metals);
		
		return stack;
	}

	public HashMap<AIsotope<?,?>, Apfloat> getContentsMass(ItemStack stack) {
		if (stack.getItem() != this) return null;

		HashMap<AIsotope<?,?>, Apfloat> metalMap = new HashMap<AIsotope<?,?>, Apfloat>();

		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null) {
			return new HashMap<AIsotope<?,?>, Apfloat>();
		}

		NBTTagList metals = compound.getTagList("Metals", NBT.TAG_COMPOUND);

		for (int i = metals.tagCount() - 1; i >= 0; i--) {
			NBTTagCompound metal = metals.getCompoundTagAt(i);

			String name = metal.getString("Name");
			AIsotope<?,?> iso = IsotopeRegistry.get(name);
			Apfloat amount = new Apfloat(metal.getString("Quantity")).precision(Constants.PRECISION).multiply(iso.getAtomicMass().precision(Constants.PRECISION));			

			metalMap.put(iso, amount);
		}

		return metalMap;
	}

	public void setContentsMass(ItemStack stack, Map<AIsotope<?,?>, Apfloat> data) {
		if (stack.getItem() != this) return;

		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null) {
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		NBTTagList metals = new NBTTagList();

		for (Entry<AIsotope<?,?>, Apfloat> e : data.entrySet()) {
			NBTTagCompound metal = new NBTTagCompound();

			metal.setString("Name", e.getKey().getFullName());
			metal.setString("Quantity", e.getValue().precision(Constants.PRECISION).divide(e.getKey().getAtomicMass().precision(Constants.PRECISION)).toString());

			metals.appendTag(metal);
		}
		compound.setTag("Metals", metals);
	}

	public void addIsotopeMass(ItemStack stack, AIsotope<?,?> iso, Apfloat mass) {
		Map<AIsotope<?,?>, Apfloat> data = getContentsMass(stack);

		data.put(iso, data.getOrDefault(iso, new Apfloat(0)).add(mass));

		setContentsMass(stack, data);
	}

	public void addIsotopeMass(ItemStack stack, String iso, Apfloat mass) {
		AIsotope<?,?> isot = IsotopeRegistry.get(iso);
		if (isot != null) {
			addIsotopeMass(stack, isot, mass);
		}
	}

	public Apfloat getTotalMass(ItemStack stack) {
		if (stack.getItem() != this) return null;

		Apfloat mass = new Apfloat(0, Constants.PRECISION);

		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null) {
			return mass;
		}

		NBTTagList metals = compound.getTagList("Metals", NBT.TAG_COMPOUND);

		for (int i = metals.tagCount() - 1; i >= 0; i--) {
			NBTTagCompound metal = metals.getCompoundTagAt(i);

			String name = metal.getString("Name");
			AIsotope<?,?> iso = IsotopeRegistry.get(name);
			Apfloat amount = new Apfloat(metal.getString("Quantity")).precision(Constants.PRECISION).multiply(iso.getAtomicMass().precision(Constants.PRECISION));			

			mass = mass.add(amount).precision(Constants.PRECISION);
		}

		return mass;
	}

	public Apfloat getTotalMass(Map<AIsotope<?,?>, Apfloat> massMap) {
		Apfloat mass = new Apfloat(0, Constants.PRECISION);
		for (Entry<AIsotope<?,?>, Apfloat> e : massMap.entrySet()) {
			mass = mass.add(e.getValue()).precision(Constants.PRECISION);
		}
		return mass;
	}

	public Apfloat getHeatCapacity(Map<String, Apfloat> molarMap) {
		Apfloat heatCapacity = new Apfloat(0, Constants.PRECISION);
		for (Entry<String, Apfloat> e : molarMap.entrySet()) {
			AIsotope<?,?> iso = IsotopeRegistry.get(e.getKey());
			if (iso != null && iso.getDecayMode() != null) {
				heatCapacity = heatCapacity.add(e.getValue().multiply(iso.getElement().getMolarHeatCapacity().precision(Constants.PRECISION))).precision(Constants.PRECISION);
			}
		}
		return heatCapacity;
	}
}
