package rreeggkk.nuclearsciences.common.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rreeggkk.nuclearsciences.common.block.ModBlocks;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityCentrifuge;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityCondensor;
import rreeggkk.nuclearsciences.common.tile.centrifuge.TileEntityVaporizer;

public class EventHandler {
	public EventHandler() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onBlockPlace(PlaceEvent event) {
		IBlockState state = event.getPlacedBlock();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		
		if (state.getBlock() == ModBlocks.centrifuge) {
			ModBlocks.centrifuge.updateBlock(world, pos, state);
		} else if (state.getBlock() == ModBlocks.condensor) {
			for (EnumFacing dir : EnumFacing.HORIZONTALS) {
				IBlockState block = world.getBlockState(pos.offset(dir));
				if (block.getBlock() == ModBlocks.centrifuge) {
					ModBlocks.centrifuge.updateBlock(world, pos.offset(dir), block);
					break;
				}
			}
		} else if (state.getBlock() == ModBlocks.vaporizer) {
			((TileEntityVaporizer)world.getTileEntity(pos)).recalculateNext();
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event) {
		IBlockState state = event.getState();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		
		if (state.getBlock() == ModBlocks.centrifuge) {
			TileEntityVaporizer master = ((TileEntityCentrifuge)world.getTileEntity(pos)).getMaster();
			if (master != null) {
				master.recalculateNext();
			}
		} else if (state.getBlock() == ModBlocks.condensor) {
			TileEntityVaporizer master = ((TileEntityCondensor)world.getTileEntity(pos)).getMaster();
			if (master != null) {
				master.recalculateNext();
			}
		}
	}
	
}
