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
import rreeggkk.nuclearsciences.common.item.ModItems;
import rreeggkk.nuclearsciences.common.item.RTGUpgrade;
import rreeggkk.nuclearsciences.common.tile.TileEntityRTG;

public class ContainerRTG extends Container {
	public TileEntityRTG tile;
	
	private int lastEnergy=-1;

	public ContainerRTG(InventoryPlayer player,
			TileEntityRTG tilee) {
		int playerInvOffX = 0;
		int playerInvOffY = 12;

		tile = tilee;
		addSlotToContainer(new MachineSlot(tilee, 0, 15, 29));
		addSlotToContainer(new MachineSlot(tilee, 1, 15, 53, 1));

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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (IContainerListener l : listeners) {
			if (lastEnergy != tile.getEnergy().getStored()) {
				l.sendProgressBarUpdate(this, 0, tile.getEnergy().getStored());
			}
		}
		
		lastEnergy = tile.getEnergy().getStored();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int val) {
		if (id == 0) {
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
			} else if (itemstack1.getItem() == ModItems.nuclearMaterial
					&& slotNum >= 2
					&& slotNum < 38) {
				
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (itemstack1.getItem() instanceof RTGUpgrade
					&& slotNum >= 2
					&& slotNum < 38) {
				if (!inventorySlots.get(1).getHasStack()) {
					((Slot)inventorySlots.get(1)).putStack(inventorySlots.get(slotNum).getStack().splitStack(1));
					if (inventorySlots.get(slotNum).getStack().getCount() == 0) {
						((Slot)inventorySlots.get(slotNum)).putStack(ItemStack.EMPTY);
					}
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
}
