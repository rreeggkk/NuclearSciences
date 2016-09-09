package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.common.Constants;

public abstract class BlockContainerNSBase extends BlockContainer {

	public BlockContainerNSBase(Material materialIn, String name, Class<? extends TileEntity> tileClass) {
		this(materialIn, materialIn.getMaterialMapColor(), name, tileClass);
	}

	public BlockContainerNSBase(Material blockMaterialIn, MapColor blockMapColorIn, String name, Class<? extends TileEntity> tileClass) {
		super(blockMaterialIn, blockMapColorIn);
		
		setUnlocalizedName(Constants.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(NuclearSciences.instance.tab);
		
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(tileClass, Constants.MOD_ID + ":tile" + name);
		
		if (NuclearSciences.proxy.getSide() == Side.CLIENT) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		}
	}
	
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    protected boolean isInvalidNeighbor(World worldIn, BlockPos pos, EnumFacing facing) {
    	return false;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	return EnumBlockRenderType.MODEL;
    }
}
