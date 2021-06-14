package dev.ftb.mods.ftbbanners.banners.text;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.CustomRenders;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

/**
 * @author LatvianModder
 */
public class TextBannerRenderer extends BlockEntityRenderer<TextBannerEntity> {
	private static final ResourceLocation BACKGROUND = new ResourceLocation(FTBBanners.MOD_ID, "textures/bg.png");

	public TextBannerRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	public static void drawString(PoseStack stack, String string, float x, float y, boolean shadow) {
		if (shadow) {
			Minecraft.getInstance().font.drawShadow(stack, string, x, y, 0xFFFFFF);
		} else {
			Minecraft.getInstance().font.draw(stack, string, x, y, 0xFFFFFF);
		}
	}

	@Override
	public void render(TextBannerEntity banner, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLights, int combinedOverlay) {
		Minecraft mc = Minecraft.getInstance();
		float blockRotation = banner.getBlockState().getValue(HorizontalDirectionalBlock.FACING).toYRot();

		matrix.pushPose();
		matrix.translate(0.5D, 0.5D, 0.5D);
		matrix.translate(banner.offsetX, banner.offsetY, banner.offsetZ);

		if (banner.followPlayer) {
			matrix.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		}
		matrix.scale(-0.025F, -0.025F, 0.025F);
		matrix.scale(banner.scale, banner.scale, banner.scale);
		if (!banner.followPlayer) {
			matrix.mulPose(Vector3f.YP.rotationDegrees(180 + blockRotation + banner.rotationY));
			matrix.mulPose(Vector3f.ZP.rotationDegrees(banner.rotationZ));
			matrix.mulPose(Vector3f.XP.rotationDegrees(banner.rotationX));
		}

		for (BannerTextLayer layer : banner.layers) {
			if (!layer.isVisible(mc.player) || layer.text.isEmpty()) {
				continue;
			}
			int light = layer.glow
					? 15728880
					: combinedLights;

			String layerText = new TranslatableComponent(layer.text).getString();

			// Run over the
			String[] parts = layerText.split("\n");
			int fontWidth = 0;
			for (String part : parts) {
				int w = mc.font.width(part);
				if (w > fontWidth) {
					fontWidth = w;
				}
			}

			int width = (fontWidth + 20) / 2;
			float height = ((mc.font.lineHeight - 3.5f) * parts.length) + 3f;

			matrix.pushPose();
			matrix.translate(0, 0, .1f);

			RenderType text = layer.culling
					? RenderType.text(BACKGROUND)
					: CustomRenders.transparencyRender(BACKGROUND);

			VertexConsumer vertexBuilder = buffer.getBuffer(text);
			float r = 0f, g = 0f, b = 0f, alpha = layer.bgAlpha;
			if (!layer.bgColor.isEmpty() && layer.bgColor.contains("#")) {
				TextColor color = TextColor.parseColor(layer.bgColor);
				int bg = color == null
						? 0x0000FF
						: color.getValue();

				r = ((bg >> 16) & 0xFF) / 255f;
				g = ((bg >> 8) & 0xFF) / 255f;
				b = (bg & 0xFF) / 255f;
			}

			vertexBuilder.vertex(matrix.last().pose(), (float) -width, -height, 0.5f).color(r, g, b, alpha).uv(0.0F, 1.0F).uv2(light).endVertex();
			vertexBuilder.vertex(matrix.last().pose(), (float) -width, height, 0.5f).color(r, g, b, alpha).uv(1.0F, 1.0F).uv2(light).endVertex();
			vertexBuilder.vertex(matrix.last().pose(), (float) width, height, 0.5f).color(r, g, b, alpha).uv(1.0F, 0.0F).uv2(light).endVertex();
			vertexBuilder.vertex(matrix.last().pose(), (float) width, -height, 0.5f).color(r, g, b, alpha).uv(0.0F, 0.0F).uv2(light).endVertex();

			matrix.popPose();

			for (int i = 0; i < parts.length; i++) {
				float textWidth = (-width) + 10;
				if (!layer.alignment.equals("left")) {
					textWidth = layer.alignment.equals("center")
							? -(mc.font.width(parts[i]) / 2f)
							: (width - mc.font.width(parts[i])) - 10;
				}

				float textY = -height + (i * (mc.font.lineHeight + 2)) + 4.5f;
				drawString(matrix, parts[i], textWidth, textY, layer.textShadow);

				if (!layer.culling) {
					matrix.pushPose();
					matrix.mulPose(Vector3f.YP.rotationDegrees(180));
					matrix.translate(0, 0, -2);
					drawString(matrix, parts[i], textWidth, textY, layer.textShadow);
					matrix.popPose();
				}
			}
		}
		matrix.popPose();
	}
}
