package io.github.rreeggkk.nuclearsciences.common.gui;

import io.github.rreeggkk.nuclearsciences.client.gui.inventory.GuiHydraulicSeparator;
import io.github.rreeggkk.nuclearsciences.client.gui.inventory.GuiRTG;
import io.github.rreeggkk.nuclearsciences.common.inventory.ContainerHydraulicSeparator;
import io.github.rreeggkk.nuclearsciences.common.inventory.ContainerRTG;
import io.github.rreeggkk.nuclearsciences.common.tile.TileEntityHydraulicSeparator;
import io.github.rreeggkk.nuclearsciences.common.tile.TileEntityRTG;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int RTG = 0;
	public static final int HYDRAULIC_SEPARATOR = 1; 

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile != null) {
			switch(ID) {
				case RTG:
					return new ContainerRTG(player.inventory, (TileEntityRTG)tile);
				case HYDRAULIC_SEPARATOR:
					return new ContainerHydraulicSeparator(player.inventory, (TileEntityHydraulicSeparator)tile);
				default:
					return null;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile != null) {
			switch(ID) {
				case RTG:
					return new GuiRTG(new ContainerRTG(player.inventory, (TileEntityRTG)tile));
				case HYDRAULIC_SEPARATOR:
					return new GuiHydraulicSeparator(new ContainerHydraulicSeparator(player.inventory, (TileEntityHydraulicSeparator)tile));
				default:
					return null;
			}
		}
		return null;
	}

}
