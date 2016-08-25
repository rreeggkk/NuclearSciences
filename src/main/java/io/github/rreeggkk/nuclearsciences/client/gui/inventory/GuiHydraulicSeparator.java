package io.github.rreeggkk.nuclearsciences.client.gui.inventory;

import org.lwjgl.opengl.GL11;

import io.github.rreeggkk.nuclearsciences.NuclearSciences;
import io.github.rreeggkk.nuclearsciences.common.Constants;
import io.github.rreeggkk.nuclearsciences.common.inventory.ContainerHydraulicSeparator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class GuiHydraulicSeparator extends GuiContainer {
	
	private static final ResourceLocation mainGUITexture = new ResourceLocation(Constants.MOD_ID + ":textures/gui/container/hydraulicSeparator.png");
	
	private ContainerHydraulicSeparator container;
	
	public GuiHydraulicSeparator(ContainerHydraulicSeparator container) {
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
		
		//GuiUtil.drawFluid(fluid, manager);
		
		drawTexturedModalRect(guiLeft + 8, guiTop + 7, 176, 88, 16, 72);
		
		drawTexturedModalRect(guiLeft + 152, guiTop + 7 + (int)(71*(1-container.tile.getEnergy().getFraction())), 176, 17, 16, (int)(71*container.tile.getEnergy().getFraction()));

		drawTexturedModalRect(guiLeft + 77, guiTop + 36, 176, 0, (int)(24 * container.getCompletion()), 17);
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
		
		fontRendererObj.drawString(
				I18n.format("container.inventory", new Object[0]), 28,
				ySize - 96 + 2, 4210752);
		
		if (mouseX>guiLeft + 152 && mouseX<guiLeft + 170 && mouseY>guiTop + 7 && mouseY<guiTop + 78) {
			drawHoveringText(Arrays.asList(new String[]{container.tile.getEnergy().getStored() + "/" + container.tile.getEnergy().getCapacity() + " " + NuclearSciences.instance.config.energyUnit}), mouseX-guiLeft, mouseY-guiTop);
		}
		if (mouseX>guiLeft + 8 && mouseX<guiLeft + 24 && mouseY>guiTop + 7 && mouseY<guiTop + 78) {
			drawHoveringText(Arrays.asList(new String[]{container.tile.getTank().getFluidAmount() + "/" + container.tile.getTank().getCapacity() + " " + I18n.format("text.nuclearsciences.hydraulicSeparator.mBWater")}), mouseX-guiLeft, mouseY-guiTop);
		}
	}
}
