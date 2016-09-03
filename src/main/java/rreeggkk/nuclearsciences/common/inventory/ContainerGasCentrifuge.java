package rreeggkk.nuclearsciences.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.tile.centrifuge.IGasCentrifugeTile;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityVaporizer;

public class ContainerGasCentrifuge extends Container {
	public TileEntityVaporizer tile;
	public IGasCentrifugeTile interactTile;
	
	private int lastEnergy;
	private int lastCapacity;
	private int lastPAssay;
	private int lastTAssay;
	
	private AIsotope<?,?> lastIsotope;

	public ContainerGasCentrifuge(InventoryPlayer player,
			TileEntityVaporizer tilee, IGasCentrifugeTile interactTile) {
		int playerInvOffX = 0;
		int playerInvOffY = 12;

		tile = tilee;
		this.interactTile = interactTile;

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
			if (lastPAssay != tile.getProductAssay()) {
				l.sendProgressBarUpdate(this, 1, tile.getProductAssay());
			}
			if (lastTAssay != tile.getTailsAssay()) {
				l.sendProgressBarUpdate(this, 2, tile.getTailsAssay());
			}
			if (lastCapacity != tile.getEnergy().getIntCapacity()) {
				l.sendProgressBarUpdate(this, 3, tile.getEnergy().getIntCapacity());
			}
			if (lastIsotope !=tile.getDesiredIsotope()) {
				l.sendProgressBarUpdate(this, 4, 0);
				tile.getWorld().notifyBlockUpdate(tile.getPos(), tile.getWorld().getBlockState(tile.getPos()), tile.getWorld().getBlockState(tile.getPos()), 2);
				tile.markDirty();
			}
		}
		
		lastEnergy = tile.getEnergy().getStored();
		lastPAssay = tile.getProductAssay();
		lastTAssay = tile.getTailsAssay();
		lastCapacity = tile.getEnergy().getIntCapacity();
		lastIsotope = tile.getDesiredIsotope();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int val) {
		if (id == 0) {
			tile.getEnergy().setStored(val);
		} else if (id == 1) {
			tile.setProductAssay(val);
		} else if (id == 2) {
			tile.setTailsAssay(val);
		} else if (id == 3) {
			tile.getEnergy().setCapacity(val);
		} else if (id == 4) {
			tile.getWorld().notifyBlockUpdate(tile.getPos(), tile.getWorld().getBlockState(tile.getPos()), tile.getWorld().getBlockState(tile.getPos()), 2);
			tile.markDirty();
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return interactTile.isUsable(player);
	}

	/**
	 * Used to sync client to server Only occurs
	 */
	@Override
	public boolean enchantItem(EntityPlayer player, int action) {
		if (!player.getEntityWorld().isRemote) {
			if (action == 0) {
				tile.nextIsotope();
			} else if (action == 1) {
				tile.previousIsotope();
			} else if (action == 2) {
				tile.changeProductAssay(0.1);
			} else if (action == 3) {
				tile.changeProductAssay(0.01);
			} else if (action == 4) {
				tile.changeProductAssay(0.001);
			} else if (action == 5) {
				tile.changeProductAssay(0.0001);
			} else if (action == 6) {
				tile.changeProductAssay(-0.1);
			} else if (action == 7) {
				tile.changeProductAssay(-0.01);
			} else if (action == 8) {
				tile.changeProductAssay(-0.001);
			} else if (action == 9) {
				tile.changeProductAssay(-0.0001);
			} else if (action == 10) {
				tile.changeTailsAssay(0.1);
			} else if (action == 11) {
				tile.changeTailsAssay(0.01);
			} else if (action == 12) {
				tile.changeTailsAssay(0.001);
			} else if (action == 13) {
				tile.changeTailsAssay(0.0001);
			} else if (action == 14) {
				tile.changeTailsAssay(-0.1);
			} else if (action == 15) {
				tile.changeTailsAssay(-0.01);
			} else if (action == 16) {
				tile.changeTailsAssay(-0.001);
			} else if (action == 17) {
				tile.changeTailsAssay(-0.0001);
			}
		}
		return super.enchantItem(player, action);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {
		return null;
	}
}
