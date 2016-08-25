package io.github.rreeggkk.nuclearsciences.common.inventory;

import io.github.rreeggkk.nuclearsciences.common.inventory.slot.MachineSlot;
import io.github.rreeggkk.nuclearsciences.common.tile.TileEntityHydraulicSeparator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerHydraulicSeparator extends Container {
	public TileEntityHydraulicSeparator tile;
	
	private static final int COMPLETION_POINTS = 2;
	
	private int lastFluidAmt;
	private int lastFixedPoint;

	public ContainerHydraulicSeparator(InventoryPlayer player,
			TileEntityHydraulicSeparator tilee) {
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

	@Override
	public void addListener(IContainerListener l) {
		super.addListener(l);
		//l.sendProgressBarUpdate(this, 0, tile.getTank().getFluidAmount());
		//l.sendProgressBarUpdate(this, 1, tile.getFixedCompletion(COMPLETION_POINTS));
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (IContainerListener l : listeners) {
			if (tile.getTank().getFluidAmount() != lastFluidAmt) {
				l.sendProgressBarUpdate(this, 0, tile.getTank().getFluidAmount());
			}
			if (lastFixedPoint != tile.getFixedCompletion(COMPLETION_POINTS)) {
				l.sendProgressBarUpdate(this, 1, tile.getFixedCompletion(COMPLETION_POINTS));
			}
		}
		
		lastFluidAmt = tile.getTank().getFluidAmount();
		lastFixedPoint = tile.getFixedCompletion(COMPLETION_POINTS);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int val) {
		if (id == 0) {
			tile.getTank().setFluid(new FluidStack(tile.getTank().getFluidType(), val));
		} else if (id == 1) {
			lastFixedPoint = val;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
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
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (slotNum == 0) {
				if (!mergeItemStack(itemstack1, 2, 38, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (tile.isItemValidForSlot(0, itemstack1)
					&& slotNum >= 2
					&& slotNum < 38) {
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			} else if (slotNum >= 2 && slotNum < 11) {
				if (!mergeItemStack(itemstack1, 11, 38, false)) {
					return null;
				}
			} else if (slotNum >= 11 && slotNum < 38) {
				if (!mergeItemStack(itemstack1, 2, 11, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 2, 38, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;

	}
	
	public double getCompletion() {
		return lastFixedPoint/Math.pow(10, COMPLETION_POINTS);
	}
}
