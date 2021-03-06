package dev.ftb.mods.ftbbanners.banners.image;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.ftb.mods.ftbbanners.banners.CustomRenders;
import dev.ftb.mods.ftbbanners.layers.BannerImageLayer;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author LatvianModder
 */
public class ImageBannerRenderer extends TileEntityRenderer<ImageBannerEntity> {
    public ImageBannerRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ImageBannerEntity banner, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLights, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();

        float a = banner.alpha / 255F;
        if (a <= 0F) {
            return;
        }

        matrix.pushPose();
        matrix.translate(0.5D, 0.5D, 0.5D);
        matrix.translate(banner.offsetX, banner.offsetY, banner.offsetZ);

        matrix.scale(1F, 1F, -1F);
        if (banner.scale != 1F) {
            matrix.scale(banner.scale, banner.scale, banner.scale);
        }

        float f = banner.getBlockState().getValue(HorizontalBlock.FACING).toYRot();
        matrix.mulPose(Vector3f.YP.rotationDegrees(f + banner.rotationY));
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-90 + banner.rotationZ));
        matrix.mulPose(Vector3f.XP.rotationDegrees(banner.rotationX));

        double w2 = banner.width / 2D;
        double h2 = banner.height / -2D;

        double wind1 = 0D;
        double wind2 = 0D;

        // Compute wind
        if (banner.wind > 0D) {
            double ws = banner.windSpeed * 0.001D;
            double d = System.currentTimeMillis() * ws + (banner.hover
                ? 0D
                : banner.getBlockPos().hashCode() * 0.19D);

            if (banner.hover) {
                matrix.translate(0D, Math.sin(d * 1.5D) * (banner.wind * banner.height * 0.9D), 0D);
            } else {
                wind1 = Math.cos(d) * (banner.wind * banner.height);
                wind2 = Math.sin(d) * (banner.wind * banner.height);
            }
        }

        for (BannerImageLayer layer : banner.getLayers()) {
            if (layer.isVisible(mc.player)) {
                int light = layer.glow
                    ? 15728880
                    : combinedLights;

                ResourceLocation image = layer.image;
                RenderType text = layer.culling
                    ? RenderType.text(image)
                    : CustomRenders.transparencyRender(image);

                IVertexBuilder vertexBuilder = buffer.getBuffer(text);
                vertexBuilder.vertex(matrix.last().pose(), (float) -h2, (float) -w2, (float) wind1).color(1F, 1F, 1F, a).uv(0.0F, 1.0F).uv2(light).endVertex();
                vertexBuilder.vertex(matrix.last().pose(), (float) -h2, (float) w2, (float) wind2).color(1F, 1F, 1F, a).uv(1.0F, 1.0F).uv2(light).endVertex();
                vertexBuilder.vertex(matrix.last().pose(), (float) h2, (float) w2, 0).color(1F, 1F, 1F, a).uv(1.0F, 0.0F).uv2(light).endVertex();
                vertexBuilder.vertex(matrix.last().pose(), (float) h2, (float) -w2, 0).color(1F, 1F, 1F, a).uv(0.0F, 0.0F).uv2(light).endVertex();
            }
        }

        matrix.popPose();
    }
}
