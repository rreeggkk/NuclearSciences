package rreeggkk.nuclearsciences.common.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemRTGUpgradeBase extends ItemMultiNSBase implements RTGUpgrade {
	
	private double[] effs;
	
	public ItemRTGUpgradeBase(double[] effs, String[] names) {
		super("rtgUpgrade", names);
		this.effs = effs;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("text.nuclearsciences.rtgupgrade.tooltip", effs[stack.getItemDamage()]*100));
	}

	@Override
	public double getPercentCarnotEfficiency(ItemStack stack) {
		if (stack.getItem() != this) {
			return 0;
		}
		return effs[stack.getItemDamage()];
	}
}
