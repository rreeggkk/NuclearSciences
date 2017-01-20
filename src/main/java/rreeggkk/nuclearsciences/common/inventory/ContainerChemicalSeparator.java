package rreeggkk.nuclearsciences.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rreeggkk.nuclearsciences.common.inventory.slot.MachineSlot;
import rreeggkk.nuclearsciences.common.tile.TileEntityChemicalSeparator;

public class ContainerChemicalSeparator extends Container {
	public TileEntityChemicalSeparator tile;
	
	private static final int COMPLETION_POINTS = 2;
	
	private int lastEnergy;
	private int lastFixedPoint;

	public ContainerChemicalSeparator(InventoryPlayer player,
			TileEntityChemicalSeparator tilee) {
		int playerInvOffX = 0;
		int playerInvOffY = 12;

		tile = tilee;
		addSlotToContainer(new MachineSlot(tilee, 0, 44, 36));
		addSlotToContainer(new MachineSlot(tilee, 1, 116, 36));

		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(player, x, 8 + 18 * x + playerInvOffX,
					130 + playerInvOffY));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + 18 * x
						+ playerInvOffX, 72 + y * 18 + playerInvOffY));
			}
		}

	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (IContainerListener l : listeners) {
			if (lastFixedPoint != tile.getFixedCompletion(COMPLETION_POINTS)) {
				l.sendProgressBarUpdate(this, 0, tile.getFixedCompletion(COMPLETION_POINTS));
			}
			if (lastEnergy != tile.getEnergy().getStored()) {
				l.sendProgressBarUpdate(this, 1, tile.getEnergy().getStored());
			}
		}
		
		lastFixedPoint = tile.getFixedCompletion(COMPLETION_POINTS);
		lastEnergy = tile.getEnergy().getStored();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int val) {
		if (id == 0) {
			lastFixedPoint = val;
		} else if (id == 1) {
			tile.getEnergy().setStored(val);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUsableByPlayer(player);
	}

	/**
	 * Used to sync client to server Only occurs
	 */
	@Override
	public boolean enchantItem(EntityPlayer player, int action) {
		return super.enchantItem(player, action);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotNum == 0) {
				if (!mergeItemStack(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (tile.isItemValidForSlot(0, itemstack1)
					&& slotNum >= 2
					&& slotNum < 38) {
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotNum >= 2 && slotNum < 11) {
				if (!mergeItemStack(itemstack1, 11, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if (slotNum >= 11 && slotNum < 38) {
				if (!mergeItemStack(itemstack1, 2, 11, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemstack1, 2, 38, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;

	}
	
	public double getCompletion() {
		return lastFixedPoint/Math.pow(10, COMPLETION_POINTS);
	}
}
