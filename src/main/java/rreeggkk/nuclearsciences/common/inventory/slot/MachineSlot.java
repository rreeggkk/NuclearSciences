package rreeggkk.nuclearsciences.common.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MachineSlot extends Slot {
	private IInventory inv;
	private int limit;

	public MachineSlot(IInventory inv, int slotIndex, int x, int y) {
		this(inv, slotIndex, x, y, 64);
	}

	public MachineSlot(IInventory inv, int slotIndex, int x, int y, int stackLimit) {
		super(inv, slotIndex, x, y);
		this.inv = inv;
		this.limit = stackLimit;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return inv.isItemValidForSlot(getSlotIndex(), stack);
	}
	
	@Override
	public int getSlotStackLimit() {
		return limit;
	}
}
