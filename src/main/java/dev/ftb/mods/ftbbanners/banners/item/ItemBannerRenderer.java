package dev.ftb.mods.ftbbanners.banners.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import dev.ftb.mods.ftbbanners.layers.BannerItemLayer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

/**
 * @author LatvianModder
 */
public class ItemBannerRenderer extends BlockEntityRenderer<ItemBannerEntity> {

	public ItemBannerRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(ItemBannerEntity banner, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLights, int combinedOverlay) {
		Minecraft mc = Minecraft.getInstance();
		float blockRotation = banner.getBlockState().getValue(HorizontalDirectionalBlock.FACING).toYRot();

		matrix.pushPose();
		matrix.translate(0.5D, 0.5D, 0.5D);
		if (banner.followPlayer) {
			matrix.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		}
		matrix.scale(banner.scale, banner.scale, banner.scale);
		matrix.translate(banner.offsetX, banner.offsetY, banner.offsetZ);

		matrix.mulPose(Vector3f.YP.rotationDegrees(180 + blockRotation + banner.rotationY));
		matrix.mulPose(Vector3f.ZP.rotationDegrees(banner.rotationZ));
		matrix.mulPose(Vector3f.XP.rotationDegrees(banner.rotationX));

		double boop = Util.getMillis() / 800D;
		for (BannerItemLayer layer : banner.getLayers()) {
			if (!layer.isVisible(mc.player)) {
				continue;
			}

			matrix.pushPose();
			matrix.translate(-layer.items.size(), 0, 0);
			int light = layer.glow
					? 15728880
					: combinedLights;

			for (int i = 0; i < layer.items.size(); i++) {
				matrix.pushPose();
				matrix.translate(.5f + (layer.items.size() / 2f) + i, 0, 0);
				if (layer.spinning) {
					matrix.mulPose(Vector3f.YP.rotationDegrees((float) (boop * 40D % 360)));
				}
				try {
					Minecraft.getInstance().getItemRenderer().renderStatic(
							layer.items.get(i), ItemTransforms.TransformType.FIXED, light, combinedOverlay, matrix, buffer
					);
				} catch (Exception ignored) {
				}
				matrix.popPose();
			}
			matrix.popPose();
		}
		matrix.popPose();
	}
}
