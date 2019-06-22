package com.feed_the_beast.ftbbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import org.lwjgl.opengl.GL11;

/**
 * @author LatvianModder
 */
public class BannerBlockRenderer extends TileEntitySpecialRenderer<BannerBlockEntity>
{
	@Override
	public void render(BannerBlockEntity banner, double rx, double ry, double rz, float partialTicks, int destroyStage, float alpha)
	{
		Minecraft mc = Minecraft.getMinecraft();

		float a = banner.getAlpha(mc.player) / 255F * alpha;

		if (a <= 0F)
		{
			return;
		}

		float lx = 0F, ly = 0F;

		if (banner.glow)
		{
			lx = OpenGlHelper.lastBrightnessX;
			ly = OpenGlHelper.lastBrightnessY;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(rx, ry, rz);
		GlStateManager.disableLighting();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableTexture2D();

		if (!banner.culling)
		{
			GlStateManager.disableCull();
		}

		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.5D, 0.5D, 0.5D);
		GlStateManager.scale(1F, 1F, -1F);
		GlStateManager.rotate(EnumFacing.byHorizontalIndex(banner.getBlockMetadata()).getHorizontalAngle() + banner.rotation, 0F, 1F, 0F);
		GlStateManager.translate(banner.offsetX, banner.offsetY, banner.offsetZ);

		double w2 = banner.width / 2D;
		double h2 = banner.height / -2D;

		double wind1 = 0D;
		double wind2 = 0D;

		if (banner.wind > 0D)
		{
			double ws = banner.windSpeed * 0.001D;
			double d = System.currentTimeMillis() * ws + banner.getPos().hashCode() * 0.19D;

			if (banner.hover)
			{
				GlStateManager.translate(0D, Math.sin(d * 1.5D) * (banner.wind * banner.height * 0.9D), 0D);
			}
			else
			{
				wind1 = Math.cos(d) * (banner.wind * banner.height);
				wind2 = Math.sin(d) * (banner.wind * banner.height);
			}
		}

		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0F);
		mc.getTextureManager().bindTexture(banner.image);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		buffer.pos(-w2, -h2, 0).tex(0, 0).color(1F, 1F, 1F, a).endVertex();
		buffer.pos(-w2, h2, wind1).tex(0, 1).color(1F, 1F, 1F, a).endVertex();
		buffer.pos(w2, h2, wind2).tex(1, 1).color(1F, 1F, 1F, a).endVertex();
		buffer.pos(w2, -h2, 0).tex(1, 0).color(1F, 1F, 1F, a).endVertex();
		tessellator.draw();

		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

		GlStateManager.popMatrix();

		if (!banner.culling)
		{
			GlStateManager.enableCull();
		}

		GlStateManager.enableLighting();
		GlStateManager.popMatrix();

		if (banner.glow)
		{
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lx, ly);
		}
	}
}