package io.github.rreeggkk.nuclearsciences.common.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MachineSlot extends Slot {
	private IInventory inv;

	public MachineSlot(IInventory inv, int slotIndex, int x, int y) {
		super(inv, slotIndex, x, y);
		this.inv = inv;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return inv.isItemValidForSlot(getSlotIndex(), stack);
	}

}
