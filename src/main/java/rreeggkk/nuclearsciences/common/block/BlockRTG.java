package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.gui.GuiHandler;
import rreeggkk.nuclearsciences.common.tile.TileEntityRTG;

public class BlockRTG extends BlockContainer {

    public BlockRTG() {
		super(Material.IRON);

		setUnlocalizedName(Constants.MOD_ID + ".rtg");
		setRegistryName("rtg");
		setCreativeTab(NuclearSciences.instance.tab);
		
		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);
		
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(TileEntityRTG.class, "tileRTG");
	}
	
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
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
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
