package dev.ftb.mods.ftbbanners.banners.text;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.CustomRenders;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

/**
 * @author LatvianModder
 */
public class TextBannerRenderer extends BlockEntityRenderer<TextBannerEntity> {
	private static final ResourceLocation BACKGROUND = new ResourceLocation(FTBBanners.MOD_ID, "textures/bg.png");

	public TextBannerRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	public static void drawString(PoseStack stack, FormattedCharSequence string, float x, float y, boolean shadow) {
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
			if (!layer.isVisible(mc.player)) {
				continue;
			}
			int light = layer.glow ? 15728880 : combinedLights;

			CachedText t = layer.getText();

			matrix.pushPose();
			matrix.translate(0, 0, .1f);

			RenderType renderType = layer.culling
					? RenderType.text(BACKGROUND)
					: CustomRenders.transparencyRender(BACKGROUND);

			VertexConsumer vertexBuilder = buffer.getBuffer(renderType);

			Matrix4f m = matrix.last().pose();
			vertexBuilder.vertex(m, (float) -t.width, -t.height, 0.5f).color(t.bgR, t.bgG, t.bgB, layer.bgAlpha).uv(0.0F, 1.0F).uv2(light).endVertex();
			vertexBuilder.vertex(m, (float) -t.width, t.height, 0.5f).color(t.bgR, t.bgG, t.bgB, layer.bgAlpha).uv(1.0F, 1.0F).uv2(light).endVertex();
			vertexBuilder.vertex(m, (float) t.width, t.height, 0.5f).color(t.bgR, t.bgG, t.bgB, layer.bgAlpha).uv(1.0F, 0.0F).uv2(light).endVertex();
			vertexBuilder.vertex(m, (float) t.width, -t.height, 0.5f).color(t.bgR, t.bgG, t.bgB, layer.bgAlpha).uv(0.0F, 0.0F).uv2(light).endVertex();

			matrix.popPose();

			for (int i = 0; i < t.lines.length; i++) {
				drawString(matrix, t.lines[i], t.textX[i], t.textY[i], layer.textShadow);

				if (!layer.culling) {
					matrix.pushPose();
					matrix.mulPose(Vector3f.YP.rotationDegrees(180));
					matrix.translate(0, 0, -2);
					drawString(matrix, t.lines[i], t.textX[i], t.textY[i], layer.textShadow);
					matrix.popPose();
				}
			}
		}
		matrix.popPose();
	}
}
