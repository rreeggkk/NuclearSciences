package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.gui.GuiHandler;
import rreeggkk.nuclearsciences.common.tile.TileEntityRTG;

public class BlockRTG extends BlockContainerNSBase {

    public BlockRTG() {
		super(Material.IRON, "rtg", TileEntityRTG.class, "tileRTG");
		
		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);
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
		player.openGui(NuclearSciences.instance, GuiHandler.RTG, world, pos.getX(), pos.getY(), pos.getZ());

		// Stop any other right click actions
		return true;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
    	return new TileEntityRTG();
	}

    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return ((TileEntityRTG)world.getTileEntity(pos)).getStackInSlot(0) == null ? 0 : 15;
    }
}
