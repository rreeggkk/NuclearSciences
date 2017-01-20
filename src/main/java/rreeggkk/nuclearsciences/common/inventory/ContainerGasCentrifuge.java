package rreeggkk.nuclearsciences.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import rreeggkk.nuclearsciences.common.tile.centrifuge.IGasCentrifugeTile;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityVaporizer;

public class ContainerGasCentrifuge extends Container {
	public IGasCentrifugeTile interactTile;

	private int lastEnergy;
	private int lastCapacity;
	private int lastPAssay;
	private int lastTAssay;
	
	private int dX, dY;
	
	private TileEntityVaporizer lastTile;

	private AIsotope<?,?> lastIsotope;

	public ContainerGasCentrifuge(InventoryPlayer player, IGasCentrifugeTile interactTile) {
		int playerInvOffX = 0;
		int playerInvOffY = 12;

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

		if (getTile() != null) {
			for (IContainerListener l : listeners) {
				if (lastEnergy != getTile().getEnergy().getStored()) {
					l.sendProgressBarUpdate(this, 0, getTile().getEnergy().getStored());
				}
				if (lastPAssay != getTile().getProductAssay()) {
					l.sendProgressBarUpdate(this, 1, getTile().getProductAssay());
				}
				if (lastTAssay != getTile().getTailsAssay()) {
					l.sendProgressBarUpdate(this, 2, getTile().getTailsAssay());
				}
				if (lastCapacity != getTile().getEnergy().getIntCapacity()) {
					l.sendProgressBarUpdate(this, 3, getTile().getEnergy().getIntCapacity());
				}
				if (lastIsotope != getTile().getDesiredIsotope()) {
					l.sendProgressBarUpdate(this, 4, 0);
					getTile().getWorld().notifyBlockUpdate(getTile().getPos(), getTile().getWorld().getBlockState(getTile().getPos()), getTile().getWorld().getBlockState(getTile().getPos()), 2);
					getTile().markDirty();
				}
				if (lastTile != getTile()) {
					BlockPos deltaPos = getTile().getPos().subtract(interactTile.getPos());
					l.sendProgressBarUpdate(this, 5, deltaPos.getX());
					l.sendProgressBarUpdate(this, 6, deltaPos.getY());
					l.sendProgressBarUpdate(this, 7, deltaPos.getZ());
				}
			}
			
			if (lastTile != getTile()) {
				lastTile = getTile();
				return;
			}

			lastEnergy = getTile().getEnergy().getStored();
			lastPAssay = getTile().getProductAssay();
			lastTAssay = getTile().getTailsAssay();
			lastCapacity = getTile().getEnergy().getIntCapacity();
			lastIsotope = getTile().getDesiredIsotope();
		}
		lastTile = getTile();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int val) {
		if (id == 5) {
			dX = val;
		} else if (id == 6) {
			dY = val;
		} else if (id == 7) {
			interactTile.setVaporizer((TileEntityVaporizer) interactTile.getWorld().getTileEntity(new BlockPos(dX, dY, val).add(interactTile.getPos())));
		}
		if (getTile() == null) {
			return;
		}
		if (id == 0) {
			getTile().getEnergy().setStored(val);
		} else if (id == 1) {
			getTile().setProductAssay(val);
		} else if (id == 2) {
			getTile().setTailsAssay(val);
		} else if (id == 3) {
			getTile().getEnergy().setCapacity(val);
		} else if (id == 4) {
			getTile().getWorld().notifyBlockUpdate(getTile().getPos(), getTile().getWorld().getBlockState(getTile().getPos()), getTile().getWorld().getBlockState(getTile().getPos()), 2);
			getTile().markDirty();
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
		if (!player.getEntityWorld().isRemote && getTile() != null) {
			if (action == 0) {
				getTile().nextIsotope();
			} else if (action == 1) {
				getTile().previousIsotope();
			} else if (action == 2) {
				getTile().changeProductAssay(0.1);
			} else if (action == 3) {
				getTile().changeProductAssay(0.01);
			} else if (action == 4) {
				getTile().changeProductAssay(0.001);
			} else if (action == 5) {
				getTile().changeProductAssay(0.0001);
			} else if (action == 6) {
				getTile().changeProductAssay(-0.1);
			} else if (action == 7) {
				getTile().changeProductAssay(-0.01);
			} else if (action == 8) {
				getTile().changeProductAssay(-0.001);
			} else if (action == 9) {
				getTile().changeProductAssay(-0.0001);
			} else if (action == 10) {
				getTile().changeTailsAssay(0.1);
			} else if (action == 11) {
				getTile().changeTailsAssay(0.01);
			} else if (action == 12) {
				getTile().changeTailsAssay(0.001);
			} else if (action == 13) {
				getTile().changeTailsAssay(0.0001);
			} else if (action == 14) {
				getTile().changeTailsAssay(-0.1);
			} else if (action == 15) {
				getTile().changeTailsAssay(-0.01);
			} else if (action == 16) {
				getTile().changeTailsAssay(-0.001);
			} else if (action == 17) {
				getTile().changeTailsAssay(-0.0001);
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
		return ItemStack.EMPTY;
	}

	public TileEntityVaporizer getTile() {
		return interactTile.hasVaporizer() ? interactTile.getVaporizer() : null;
	}
}
