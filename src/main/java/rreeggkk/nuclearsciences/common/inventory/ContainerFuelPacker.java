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
import rreeggkk.nuclearsciences.common.nuclear.fuel.FuelTypes;
import rreeggkk.nuclearsciences.common.tile.TileEntityFuelPacker;

public class ContainerFuelPacker extends Container {
	public TileEntityFuelPacker tile;
	
	private static final int COMPLETION_POINTS = 2;
	
	private int lastEnergy;
	private int lastFixedPoint;
	private int lastFuelTypeIndex;

	public ContainerFuelPacker(InventoryPlayer player,
			TileEntityFuelPacker tilee) {
		int playerInvOffX = 0;
		int playerInvOffY = 12;

		tile = tilee;
		addSlotToContainer(new MachineSlot(tilee, 0, 44, 36));
		addSlotToContainer(new MachineSlot(tilee, 1, 116, 36));
		addSlotToContainer(new MachineSlot(tilee, 2, 0, 36));

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
			if (lastFuelTypeIndex != FuelTypes.getFuelTypes().indexOf(tile.getFuelType())) {
				l.sendProgressBarUpdate(this, 2, FuelTypes.getFuelTypes().indexOf(tile.getFuelType()));
			}
		}
		
		lastFixedPoint = tile.getFixedCompletion(COMPLETION_POINTS);
		lastEnergy = tile.getEnergy().getStored();
		lastFuelTypeIndex = FuelTypes.getFuelTypes().indexOf(tile.getFuelType());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int val) {
		if (id == 0) {
			lastFixedPoint = val;
		} else if (id == 1) {
			tile.getEnergy().setStored(val);
		} else if (id == 2) {
			tile.setFuelType(FuelTypes.getFuelTypes().get(val));
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
		if (action == 0) {
			tile.setFuelType(FuelTypes.getFuelTypes().get((FuelTypes.getFuelTypes().indexOf(tile.getFuelType())-1 + FuelTypes.getFuelTypes().size())%FuelTypes.getFuelTypes().size()));
		} else if (action == 1) {
			tile.setFuelType(FuelTypes.getFuelTypes().get((FuelTypes.getFuelTypes().indexOf(tile.getFuelType())+1)%FuelTypes.getFuelTypes().size()));
		}
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
				if (!mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (tile.isItemValidForSlot(0, itemstack1)
					&& slotNum >= 3
					&& slotNum < 39) {
				if (!mergeItemStack(itemstack1, 0, 1, false)) {
					return null;
				}
			} else if (slotNum >= 3 && slotNum < 12) {
				if (!mergeItemStack(itemstack1, 12, 39, false)) {
					return null;
				}
			} else if (slotNum >= 12 && slotNum < 39) {
				if (!mergeItemStack(itemstack1, 3, 12, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 3, 39, false)) {
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
