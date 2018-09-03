package com.feed_the_beast.ftbbanners;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LatvianModder
 */
@Mod.EventBusSubscriber(modid = FTBBanners.MOD_ID, value = Side.CLIENT)
public class FTBBannersClientEventHandler
{
	public static final Map<String, Banner> BANNERS = new HashMap<>();

	@SubscribeEvent
	public static void renderLast(RenderWorldLastEvent event)
	{
		if (BANNERS.isEmpty())
		{
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();
		int dim = mc.world.provider.getDimension();

		double renderDistanceSq = 128D * 128D;

		double px = mc.getRenderManager().viewerPosX;
		double py = mc.getRenderManager().viewerPosY;
		double pz = mc.getRenderManager().viewerPosZ;
		//Frustum frustum = new Frustum();
		//frustum.setPosition(px, py, pz);
		GlStateManager.pushMatrix();
		GlStateManager.translate(-px, -py, -pz);
		GlStateManager.disableLighting();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.disableCull();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		for (Banner banner : BANNERS.values())
		{
			double x = banner.x;
			double y = banner.y;
			double z = banner.z;

			if (banner.alpha > 0 && banner.dimension == dim && (px - x) * (px - x) + (py - y) * (py - y) + (pz - z) * (pz - z) <= renderDistanceSq)// && frustum.isBoxInFrustum(x - s2, y - s2, z - s2, x + s2, y + s2, z + s2))
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, z);
				GlStateManager.rotate(banner.rotation, 0F, 1F, 0F);

				double w2 = banner.width / 2D;
				double h2 = banner.height / -2D;

				if (banner.alpha < 255)
				{
					GlStateManager.enableBlend();

					if (banner.alpha < 30)
					{
						GlStateManager.alphaFunc(GL11.GL_GREATER, 0F);
					}
				}

				double wind1 = 0D;
				double wind2 = 0D;

				if (banner.wind > 0D)
				{
					wind1 = Math.cos(System.currentTimeMillis() * 0.001D) * (banner.wind * banner.height);
					wind2 = Math.sin(System.currentTimeMillis() * 0.001D) * (banner.wind * banner.height);
				}

				mc.getTextureManager().bindTexture(banner.image);
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
				buffer.pos(-w2, -h2, 0).tex(0, 0).color(255, 255, 255, banner.alpha).endVertex();
				buffer.pos(-w2, h2, wind1).tex(0, 1).color(255, 255, 255, banner.alpha).endVertex();
				buffer.pos(w2, h2, wind2).tex(1, 1).color(255, 255, 255, banner.alpha).endVertex();
				buffer.pos(w2, -h2, 0).tex(1, 0).color(255, 255, 255, banner.alpha).endVertex();
				tessellator.draw();

				if (banner.alpha < 255)
				{
					GlStateManager.disableBlend();

					if (banner.alpha < 30)
					{
						GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
					}
				}

				GlStateManager.popMatrix();
			}
		}

		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
}