package rreeggkk.nuclearsciences.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rreeggkk.nuclearsciences.common.Constants;

public abstract class BlockContainerNSBase extends BlockNSBase implements ITileEntityProvider {

	public BlockContainerNSBase(Material materialIn, String name, Class<? extends TileEntity> tileClass, String tileRegister) {
		this(materialIn, materialIn.getMaterialMapColor(), name, tileClass, tileRegister);
	}

	public BlockContainerNSBase(Material blockMaterialIn, MapColor blockMapColorIn, String name, Class<? extends TileEntity> tileClass, String tileRegister) {
		super(blockMaterialIn, blockMapColorIn, name);
		
		GameRegistry.registerTileEntity(tileClass, Constants.MOD_ID + ":tile" + name);
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
}
