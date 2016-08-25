package rreeggkk.nuclearsciences.common.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemRTGUpgradeBase extends ItemNSBase implements RTGUpgrade {
	
	private double eff;
	
	public ItemRTGUpgradeBase(double eff, String name) {
		super(name);
		this.eff = eff;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("text.nuclearsciences.rtgupgrade.tooltip", eff*100));
	}

	@Override
	public double getPercentCarnotEfficiency(ItemStack stack) {
		if (stack.getItem() != this) {
			return 0;
		}
		return eff;
	}
}
