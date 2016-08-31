package rreeggkk.nuclearsciences.common.tile.centrifuge;

import net.minecraft.tileentity.TileEntity;

public class TileEntityCentrifuge extends TileEntity {
	private TileEntityVaporizer master;

	/*
	public void setMaster(TileEntityVaporizer master) {
		if (this.master != null) {
			this.master.removeCentrifuge(this);
		}
		this.master = master;
		if (this.master != null) {
			this.master.addCentrifuge(this);
		}
	}*/
	
	public void setMaster(TileEntityVaporizer master) {
		this.master = master;
	}

	public TileEntityVaporizer getMaster() {
		return master;
	}/*

	public void propogateMasterChange() {
		IBlockState state = worldObj.getBlockState(getPos());

		BlockPos right = pos.offset(state.getValue(BlockCentrifuge.FACING).rotateYCCW());
		BlockPos left = pos.offset(state.getValue(BlockCentrifuge.FACING).rotateY());

		IBlockState rightBlock = worldObj.getBlockState(right);
		TileEntity teRight = worldObj.getTileEntity(right);

		IBlockState leftBlock = worldObj.getBlockState(left);
		TileEntity teLeft = worldObj.getTileEntity(left);

		if (rightBlock.getBlock() == ModBlocks.centrifuge) {
			TileEntityCentrifuge te = (TileEntityCentrifuge)teRight;
			te.setMaster(getMaster());
			te.propogateMasterChangeRight();
		} else if (rightBlock.getBlock() == ModBlocks.condensor) {
			TileEntityCondensor te = (TileEntityCondensor)teRight;
			te.setMaster(getMaster(), false);
		}

		if (leftBlock.getBlock() == ModBlocks.centrifuge) {
			TileEntityCentrifuge te = (TileEntityCentrifuge)teLeft;
			te.setMaster(getMaster());
			te.propogateMasterChangeLeft();
		} else if (leftBlock.getBlock() == ModBlocks.condensor) {
			TileEntityCondensor te = (TileEntityCondensor)teLeft;
			te.setMaster(getMaster(), true);
		}
	}

	private void propogateMasterChangeRight() {
		IBlockState state = worldObj.getBlockState(getPos());

		BlockPos right = pos.offset(state.getValue(BlockCentrifuge.FACING).rotateYCCW());

		IBlockState rightBlock = worldObj.getBlockState(right);
		TileEntity teRight = worldObj.getTileEntity(right);

		if (rightBlock.getBlock() == ModBlocks.centrifuge) {
			TileEntityCentrifuge te = (TileEntityCentrifuge)teRight;
			te.setMaster(getMaster());
			te.propogateMasterChangeRight();
		} else if (rightBlock.getBlock() == ModBlocks.condensor) {
			TileEntityCondensor te = (TileEntityCondensor)teRight;
			te.setMaster(getMaster(), false);
		}
	}

	private void propogateMasterChangeLeft() {
		IBlockState state = worldObj.getBlockState(getPos());

		BlockPos left = pos.offset(state.getValue(BlockCentrifuge.FACING).rotateY());

		IBlockState leftBlock = worldObj.getBlockState(left);
		TileEntity teLeft = worldObj.getTileEntity(left);

		if (leftBlock.getBlock() == ModBlocks.centrifuge) {
			TileEntityCentrifuge te = (TileEntityCentrifuge)teLeft;
			te.setMaster(getMaster());
			te.propogateMasterChangeLeft();
		} else if (leftBlock.getBlock() == ModBlocks.condensor) {
			TileEntityCondensor te = (TileEntityCondensor)teLeft;
			te.setMaster(getMaster(), true);
		}
	}*/
}
