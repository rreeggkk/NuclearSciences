package rreeggkk.nuclearsciences.common.block.centrifuge;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.block.BlockContainerNSBase;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.gui.GuiHandler;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityCentrifuge;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityVaporizer;

public class BlockCentrifuge extends BlockContainerNSBase {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public static final PropertyBool RIGHT = PropertyBool.create("right");
	public static final PropertyBool LEFT = PropertyBool.create("left");

	public static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
	public static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D);

	public BlockCentrifuge() {
		super(Material.IRON, "centrifuge", TileEntityCentrifuge.class);

		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(RIGHT, false).withProperty(LEFT, false));
	}

	public void updateBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityVaporizer master = null;

		BlockPos right = getRightOf(state, pos);
		BlockPos left = getLeftOf(state, pos);
		BlockPos down = pos.down();

		IBlockState rightBlock = world.getBlockState(right);
		TileEntity teRight = world.getTileEntity(right);

		IBlockState leftBlock = world.getBlockState(left);
		TileEntity teLeft = world.getTileEntity(left);

		if (leftBlock.getBlock() == ModBlocks.centrifuge) {
			master = ((TileEntityCentrifuge)teLeft).getMaster();
		}
		if (master == null && world.getBlockState(down).getBlock() == ModBlocks.vaporizer) {
			master = (TileEntityVaporizer)world.getTileEntity(down);
		}
		if (master == null && rightBlock.getBlock() == ModBlocks.centrifuge) {
			master = ((TileEntityCentrifuge)teRight).getMaster();
		}

		if (master != null) {
			master.recalculateNext();
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return (state.getValue(FACING).getAxis() == Axis.X ? X_AABB : Z_AABB);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		// Get the tile entity
		TileEntity tileEntity = world.getTileEntity(pos);
		// If the tile entity doesn't exsist or the player is sneaking
		if (tileEntity == null || player.isSneaking()) {
			// Let the normal action happen
			return false;
		}

		// Open the GUI
		player.openGui(NuclearSciences.instance, GuiHandler.MULTIBLOCK_GAS_CENTRIFUGE, world, pos.getX(), pos.getY(), pos.getZ());

		// Stop any other right click actions
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCentrifuge();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, RIGHT, LEFT);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		// 0b0000 = XXXY; X = facing, Y = runining
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return blockState.getBaseState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(RIGHT, world.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getBlock() == this
				&& world.getBlockState(pos.offset(state.getValue(FACING).rotateYCCW())).getValue(FACING) == state.getValue(FACING))
				.withProperty(LEFT, world.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getBlock() == this
				&& world.getBlockState(pos.offset(state.getValue(FACING).rotateY())).getValue(FACING) == state.getValue(FACING));
	}
	
	public BlockPos getRightOf(IBlockState state, BlockPos pos) {
		return pos.offset(state.getValue(FACING).rotateYCCW());
	}
	
	public BlockPos getLeftOf(IBlockState state, BlockPos pos) {
		return pos.offset(state.getValue(FACING).rotateY());
	}
	
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (hasTileEntity(state)){
            worldIn.removeTileEntity(pos);
        }
    }
}
