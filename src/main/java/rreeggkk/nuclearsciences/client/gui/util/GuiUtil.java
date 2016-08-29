package rreeggkk.nuclearsciences.client.gui.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import rreeggkk.nuclearsciences.common.Constants;
import rreeggkk.nuclearsciences.common.energy.AEnergyContainer;

public class GuiUtil {
	private static final ResourceLocation elementsTexture = new ResourceLocation(Constants.MOD_ID + ":textures/gui/guiElements.png");

	public static void guiFluidTank(FluidTank tank, int x, int y) {
		mc().getTextureManager().bindTexture(elementsTexture);
		drawTexturedModalRect(x, y, 0, 0, 18, 73);
		if (tank.getFluid() != null) {
			drawRepeatedFluidSprite(tank.getFluid().getFluid(), x+1, y + 72 - (int)(71*((double)tank.getFluidAmount()/tank.getCapacity())), 16, (int)(71*((double)tank.getFluidAmount()/tank.getCapacity())));
			mc().getTextureManager().bindTexture(elementsTexture);
		}
		drawTexturedModalRect(x, y, 18, 0, 18, 73);
	}

	public static void guiEnergyMeter(AEnergyContainer energy, int x, int y) {
		mc().getTextureManager().bindTexture(elementsTexture);
		drawTexturedModalRect(x, y, 54, 0, 18, 73);
		drawTexturedModalRect(x+1, y+72 - (int)(71*energy.getFraction()), 72, 1, 16, (int)(71*energy.getFraction()));
	}

	private static Minecraft mc(){
		return Minecraft.getMinecraft();
	}

	private static void bindTexture(ResourceLocation loc) {
		mc().getTextureManager().bindTexture(loc);
	}

	/*
	 * Credit to ImmersiveEngineering (BluSunrize)
	 */
	public static void drawTexturedRect(float x, float y, float w, float h, double... uv) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(x, y+h, 0).tex(uv[0], uv[3]).endVertex();
		worldrenderer.pos(x+w, y+h, 0).tex(uv[1], uv[3]).endVertex();
		worldrenderer.pos(x+w, y, 0).tex(uv[1], uv[2]).endVertex();
		worldrenderer.pos(x, y, 0).tex(uv[0], uv[2]).endVertex();
		tessellator.draw();
	}

	/*
	 * Credit to ImmersiveEngineering (BluSunrize)
	 */
	public static void drawTexturedRect(int x, int y, int w, int h, float picSize, int... uv) {
		double[] d_uv = new double[]{uv[0]/picSize,uv[1]/picSize, uv[2]/picSize,uv[3]/picSize};
		drawTexturedRect(x,y,w,h, d_uv);
	}

	/*
	 * Credit to ImmersiveEngineering (BluSunrize)
	 */
	public static void drawRepeatedFluidSprite(Fluid fluid, float x, float y, float w, float h) {
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite sprite = mc().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
		if(sprite != null)
		{
			int iW = sprite.getIconWidth();
			int iH = sprite.getIconHeight();
			if(iW>0 && iH>0)
				drawRepeatedSprite(x,y,w,h, iW, iH, sprite.getMinU(),sprite.getMaxU(), sprite.getMinV(),sprite.getMaxV());
		}
	}

	/*
	 * Credit to ImmersiveEngineering (BluSunrize)
	 */
	public static void drawRepeatedSprite(float x, float y, float w, float h, int iconWidth, int iconHeight, float uMin, float uMax, float vMin, float vMax) {
		int iterMaxW = (int)(w/iconWidth);
		int iterMaxH = (int)(h/iconHeight);
		float leftoverW = w%iconWidth;
		float leftoverH = h%iconHeight;
		float leftoverWf = leftoverW/(float)iconWidth;
		float leftoverHf = leftoverH/(float)iconHeight;
		float iconUDif = uMax-uMin;
		float iconVDif = vMax-vMin;
		for(int ww=0; ww<iterMaxW; ww++)
		{
			for(int hh=0; hh<iterMaxH; hh++)
				drawTexturedRect(x+ww*iconWidth, y+hh*iconHeight, iconWidth,iconHeight, uMin,uMax,vMin,vMax);
			drawTexturedRect(x+ww*iconWidth, y+iterMaxH*iconHeight, iconWidth,leftoverH, uMin,uMax,vMin,(vMin+iconVDif*leftoverHf));
		}
		if(leftoverW>0)
		{
			for(int hh=0; hh<iterMaxH; hh++)
				drawTexturedRect(x+iterMaxW*iconWidth, y+hh*iconHeight, leftoverW,iconHeight, uMin,(uMin+iconUDif*leftoverWf),vMin,vMax);
			drawTexturedRect(x+iterMaxW*iconWidth, y+iterMaxH*iconHeight, leftoverW,leftoverH, uMin,(uMin+iconUDif*leftoverWf),vMin,(vMin+iconVDif*leftoverHf));
		}
	}

	private static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
	{
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		vertexbuffer.pos((double)(x + 0), (double)(y + height), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
		vertexbuffer.pos((double)(x + width), (double)(y + height), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
		vertexbuffer.pos((double)(x + width), (double)(y + 0), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
		vertexbuffer.pos((double)(x + 0), (double)(y + 0), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
		tessellator.draw();
	}
}
