package rreeggkk.nuclearsciences.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import rreeggkk.nuclearsciences.client.gui.inventory.GuiChemicalSeparator;
import rreeggkk.nuclearsciences.client.gui.inventory.GuiGasCentrifuge;
import rreeggkk.nuclearsciences.client.gui.inventory.GuiHydraulicSeparator;
import rreeggkk.nuclearsciences.client.gui.inventory.GuiRTG;
import rreeggkk.nuclearsciences.common.inventory.ContainerChemicalSeparator;
import rreeggkk.nuclearsciences.common.inventory.ContainerGasCentrifuge;
import rreeggkk.nuclearsciences.common.inventory.ContainerHydraulicSeparator;
import rreeggkk.nuclearsciences.common.inventory.ContainerRTG;
import rreeggkk.nuclearsciences.common.tile.TileEntityChemicalSeparator;
import rreeggkk.nuclearsciences.common.tile.TileEntityHydraulicSeparator;
import rreeggkk.nuclearsciences.common.tile.TileEntityRTG;
import rreeggkk.nuclearsciences.common.tile.centrifuge.IGasCentrifugeTile;

public class GuiHandler implements IGuiHandler {

	public static final int RTG = 0;
	public static final int HYDRAULIC_SEPARATOR = 1; 
	public static final int CHEMICAL_SEPARATOR = 2; 
	public static final int MULTIBLOCK_GAS_CENTRIFUGE = 3; 

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile != null) {
			switch(ID) {
				case RTG:
					return new ContainerRTG(player.inventory, (TileEntityRTG)tile);
				case HYDRAULIC_SEPARATOR:
					return new ContainerHydraulicSeparator(player.inventory, (TileEntityHydraulicSeparator)tile);
				case CHEMICAL_SEPARATOR:
					return new ContainerChemicalSeparator(player.inventory, (TileEntityChemicalSeparator)tile);
				case MULTIBLOCK_GAS_CENTRIFUGE:
					return new ContainerGasCentrifuge(player.inventory, (IGasCentrifugeTile)tile);
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
				case CHEMICAL_SEPARATOR:
					return new GuiChemicalSeparator(new ContainerChemicalSeparator(player.inventory, (TileEntityChemicalSeparator)tile));
				case MULTIBLOCK_GAS_CENTRIFUGE:
					return new GuiGasCentrifuge(new ContainerGasCentrifuge(player.inventory, (IGasCentrifugeTile)tile));
				default:
					return null;
			}
		}
		return null;
	}

}
