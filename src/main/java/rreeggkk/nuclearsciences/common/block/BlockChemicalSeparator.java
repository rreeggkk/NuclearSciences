package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.gui.GuiHandler;
import rreeggkk.nuclearsciences.common.tile.TileEntityChemicalSeparator;

public class BlockChemicalSeparator extends BlockContainerNSBase {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool RUNNING = PropertyBool.create("running");

	public BlockChemicalSeparator() {
		super(Material.IRON, "chemicalSeparator", TileEntityChemicalSeparator.class);

		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(RUNNING, false));
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side,
			float hitX, float hitY, float hitZ) {


		// Get the tile entity
		TileEntity tileEntity = world.getTileEntity(pos);
		// If the tile entity doesn't exsist or the player is sneaking
		if (tileEntity == null || player.isSneaking()) {
			// Let the normal action happen
			return false;
		}

		// Open the GUI
		player.openGui(NuclearSciences.instance, GuiHandler.CHEMICAL_SEPARATOR, world, pos.getX(), pos.getY(), pos.getZ());

		// Stop any other right click actions
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChemicalSeparator();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, RUNNING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		// 0b0000 = XXXY; X = facing, Y = runining
		return (state.getValue(FACING).getHorizontalIndex()+1) << 1 | (state.getValue(RUNNING).booleanValue() ? 1 : 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return blockState.getBaseState().withProperty(RUNNING, (meta & 0b0001) == 1).withProperty(FACING, EnumFacing.getHorizontal((meta >>> 1) - 1));
	}
}
