package rreeggkk.nuclearsciences.client.gui.inventory;

import org.apfloat.Apfloat;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.client.gui.util.GuiUtil;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.inventory.ContainerRTG;
import rreeggkk.nuclearsciences.common.tile.TileEntityRTG;
import rreeggkk.nuclearsciences.common.util.TemperatureUtil;
import rreeggkk.nuclearsciences.common.util.TextUtil;
import scala.actors.threadpool.Arrays;

public class GuiRTG extends GuiContainer {
	
	private static final ResourceLocation mainGUITexture = new ResourceLocation(Constants.MOD_ID + ":textures/gui/container/rtg.png");
	
	private ContainerRTG container;
	
	public GuiRTG(ContainerRTG container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;

		mc.getTextureManager().bindTexture(mainGUITexture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		GuiUtil.guiEnergyMeter(container.tile.getEnergy(), guiLeft + 153, guiTop + 6);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = container.tile.hasCustomName() ? container.tile
				.getName() : I18n.format(
				container.tile.getName(), new Object[0]);
		fontRendererObj.drawString(s,
				xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 10,
				4210752);
		
		String rft = TextUtil.getUnitString(container.tile.getEnergyPerTick(), 5, NuclearSciences.instance.config.energyUnit + "/t", true);
		fontRendererObj.drawString(rft,
				37, 28,
				4210752);
		
		String temp = "Temperature: " + TemperatureUtil.convertFromKelvin(container.tile.getInternalTemperature()).precision(6).toString(true) + NuclearSciences.instance.config.temperatureUnit;
		fontRendererObj.drawString(temp,
				37, 44,
				4210752);
		if (container.tile.getInternalTemperature().compareTo(TileEntityRTG.maxTemperature) > 0) {
			mc.displayGuiScreen(null);
		}
		
		String eff = "Efficiency: " + container.tile.getEfficiency().multiply(new Apfloat(100)).precision(4).toString(true) + "%";
		fontRendererObj.drawString(eff,
				37, 60,
				4210752);
		
		fontRendererObj.drawString(
				I18n.format("container.inventory", new Object[0]), 8,
				ySize - 96 + 2, 4210752);
		
		if (mouseX>guiLeft + 154 && mouseX<guiLeft + 170 && mouseY>guiTop + 7 && mouseY<guiTop + 78) {
			drawHoveringText(Arrays.asList(new String[]{container.tile.getEnergy().getStored() + "/" + container.tile.getEnergy().getCapacity() + " " + NuclearSciences.instance.config.energyUnit}), mouseX-guiLeft, mouseY-guiTop);
		}
	}
}
