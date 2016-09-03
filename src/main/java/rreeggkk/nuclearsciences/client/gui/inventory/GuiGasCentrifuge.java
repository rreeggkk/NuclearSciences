package rreeggkk.nuclearsciences.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import rreeggkk.nuclearsciences.NuclearSciences;
import rreeggkk.nuclearsciences.client.gui.util.GuiUtil;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.inventory.ContainerGasCentrifuge;
import rreeggkk.nuclearsciences.common.nuclear.element.AIsotope;
import scala.actors.threadpool.Arrays;

public class GuiGasCentrifuge extends GuiContainer {
	
	private static final ResourceLocation mainGUITexture = new ResourceLocation(Constants.MOD_ID + ":textures/gui/container/centrifuge.png");
	
	private ContainerGasCentrifuge container;
	
	public GuiGasCentrifuge(ContainerGasCentrifuge container) {
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
		
		AIsotope<?,?> iso = container.tile.getDesiredIsotope();
		String isotope = (iso == null ? I18n.format("text.nuclearsciences.centrifuge.noIsotope") : iso.getFullName());
		fontRendererObj.drawString(isotope,
				69 - fontRendererObj.getStringWidth(isotope) / 2, 30,
				4210752);
		
		String prodAssay = container.tile.getProductAssay()/100d + "%";
		fontRendererObj.drawString(prodAssay,
				69 - fontRendererObj.getStringWidth(prodAssay) / 2, 49,
				4210752);
		
		String tailAssay = container.tile.getTailsAssay()/100d + "%";
		fontRendererObj.drawString(tailAssay,
				69 - fontRendererObj.getStringWidth(tailAssay) / 2, 66,
				4210752);

		if (GuiUtil.isMouseIn(mouseX, mouseY, 8, 26, 16, 16, guiLeft, guiTop)) {
			drawHoveringText(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.prevIsotope")}), mouseX-guiLeft, mouseY-guiTop);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 114, 26, 16, 16, guiLeft, guiTop)) {
			drawHoveringText(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.nextIsotope")}), mouseX-guiLeft, mouseY-guiTop);
		}
		
		List<String> sizeChange = Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.size.2"),I18n.format("text.nuclearsciences.centrifuge.size.1"),I18n.format("text.nuclearsciences.centrifuge.size.0." + (Minecraft.IS_RUNNING_ON_MAC ? "1" : "0"))});
		
		if (GuiUtil.isMouseIn(mouseX, mouseY, 8, 44, 16, 16, guiLeft, guiTop)) {
			List<String> tooltip = new ArrayList<String>(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.product.positive." + (1 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0)))}));
			tooltip.addAll(sizeChange);
			drawHoveringText(tooltip, mouseX-guiLeft, mouseY-guiTop);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 114, 44, 16, 16, guiLeft, guiTop)) {
			List<String> tooltip = new ArrayList<String>(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.product.negative." + (1 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0)))}));
			tooltip.addAll(sizeChange);
			drawHoveringText(tooltip, mouseX-guiLeft, mouseY-guiTop);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 8, 62, 16, 16, guiLeft, guiTop)) {
			List<String> tooltip = new ArrayList<String>(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.tails.positive." + (1 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0)))}));
			tooltip.addAll(sizeChange);
			drawHoveringText(tooltip, mouseX-guiLeft, mouseY-guiTop);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 114, 62, 16, 16, guiLeft, guiTop)) {
			List<String> tooltip = new ArrayList<String>(Arrays.asList(new String[]{I18n.format("text.nuclearsciences.centrifuge.tails.negative." + (1 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0)))}));
			tooltip.addAll(sizeChange);
			drawHoveringText(tooltip, mouseX-guiLeft, mouseY-guiTop);
		}
		
		/*
		fontRendererObj.drawString(
				I18n.format("container.inventory", new Object[0]), 8,
				ySize - 96 + 2, 4210752);*/
		
		if (mouseX>guiLeft + 154 && mouseX<guiLeft + 170 && mouseY>guiTop + 7 && mouseY<guiTop + 78) {
			drawHoveringText(Arrays.asList(new String[]{container.tile.getEnergy().getStored() + "/" + container.tile.getEnergy().getCapacity() + " " + NuclearSciences.instance.config.energyUnit}), mouseX-guiLeft, mouseY-guiTop);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (GuiUtil.isMouseIn(mouseX, mouseY, 8, 26, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 1);
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 114, 26, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 0);
		}

		if (GuiUtil.isMouseIn(mouseX, mouseY, 8, 44, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 8 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0));
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 114, 44, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 4 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0));
		}

		
		if (GuiUtil.isMouseIn(mouseX, mouseY, 8, 62, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 16 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0));
		}
		if (GuiUtil.isMouseIn(mouseX, mouseY, 114, 62, 16, 16, guiLeft, guiTop)) {
			mc.playerController.sendEnchantPacket(container.windowId, 12 - (GuiScreen.isAltKeyDown()?1:0) - (GuiScreen.isShiftKeyDown()?1:0) + (GuiScreen.isCtrlKeyDown()?1:0));
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
}
