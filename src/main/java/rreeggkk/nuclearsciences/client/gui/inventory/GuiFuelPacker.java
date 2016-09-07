package rreeggkk.nuclearsciences.client.gui.inventory;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.client.gui.util.GuiUtil;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.inventory.ContainerFuelPacker;
import rreeggkk.nuclearsciences.common.nuclear.fuel.FuelType;
import scala.actors.threadpool.Arrays;

public class GuiFuelPacker extends GuiContainer {
	
	private static final ResourceLocation mainGUITexture = new ResourceLocation(Constants.MOD_ID + ":textures/gui/container/fuelPacker.png");
	
	private ContainerFuelPacker container;
	
	public GuiFuelPacker(ContainerFuelPacker container) {
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
		
		GuiUtil.guiEnergyMeter(container.tile.getEnergy(), guiLeft + 151, guiTop + 6);

		mc.getTextureManager().bindTexture(mainGUITexture);

		drawTexturedModalRect(guiLeft + 77, guiTop + 25, 176, 0, (int)(24 * container.getCompletion()), 17);
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
		
		FuelType fuelType = container.tile.getFuelType();
		String isotope = (fuelType == null ? I18n.format("text.nuclearsciences.fuelPacker.noType") : fuelType.getLocalizedName());
		fontRendererObj.drawString(isotope,
				68 - fontRendererObj.getStringWidth(isotope) / 2, 52,
				4210752);
		
		if (GuiUtil.isMouseIn(mouseX, mouseY, 7, 48, 16, 16, guiLeft, guiTop)) {
			drawHoveringText(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.fuelPacker.prevType")}), mouseX-guiLeft, mouseY-guiTop);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 113, 48, 16, 16, guiLeft, guiTop)) {
			drawHoveringText(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.fuelPacker.nextType")}), mouseX-guiLeft, mouseY-guiTop);
		}

		if (mouseX>guiLeft + 152 && mouseX<guiLeft + 170 && mouseY>guiTop + 7 && mouseY<guiTop + 78) {
			drawHoveringText(Arrays.asList(new String[]{container.tile.getEnergy().getStored() + "/" + container.tile.getEnergy().getCapacity() + " " + NuclearSciences.instance.config.energyUnit}), mouseX-guiLeft, mouseY-guiTop);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (GuiUtil.isMouseIn(mouseX, mouseY, 7, 48, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 0);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 113, 48, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 1);
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
