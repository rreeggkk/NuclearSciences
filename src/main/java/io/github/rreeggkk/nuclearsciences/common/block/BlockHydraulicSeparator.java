package io.github.rreeggkk.nuclearsciences.common.block;

import io.github.rreeggkk.nuclearsciences.NuclearSciences;
import io.github.rreeggkk.nuclearsciences.common.Constants;
import io.github.rreeggkk.nuclearsciences.common.gui.GuiHandler;
import io.github.rreeggkk.nuclearsciences.common.tile.TileEntityHydraulicSeparator;
import io.github.rreeggkk.nuclearsciences.common.util.CapabilityUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHydraulicSeparator extends BlockContainer {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool RUNNING = PropertyBool.create("running");

	public BlockHydraulicSeparator() {
		super(Material.IRON);

		setUnlocalizedName(Constants.MOD_ID + ".hydraulicSeparator");
		setRegistryName("hydraulicSeparator");
		setCreativeTab(NuclearSciences.instance.tab);

		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(RUNNING, false));

		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(TileEntityHydraulicSeparator.class, "tileHydraulicSeparator");
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
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
		
		if (FluidUtil.interactWithFluidHandler(heldItem, tileEntity.getCapability(CapabilityUtil.FLUID_HANDLER, side), player)){
			return true;
		}

		// Open the GUI
		player.openGui(NuclearSciences.instance, GuiHandler.HYDRAULIC_SEPARATOR, world, pos.getX(), pos.getY(), pos.getZ());

		// Stop any other right click actions
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityHydraulicSeparator();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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
