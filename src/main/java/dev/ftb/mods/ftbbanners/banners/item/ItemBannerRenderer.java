package dev.ftb.mods.ftbbanners.banners.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

/**
 * @author LatvianModder
 */
public class ItemBannerRenderer extends TileEntityRenderer<ItemBannerEntity> {

    public ItemBannerRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ItemBannerEntity banner, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLights, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        float blockRotation = banner.getBlockState().getValue(HorizontalBlock.FACING).toYRot();

        matrix.pushPose();
        matrix.translate(0.5D, 0.5D, 0.5D);
        //        matrix.scale(-0.025F, -0.025F, 0.025F);
        //        matrix.scale(banner.scale, banner.scale, banner.scale);
        //        matrix.translate(banner.offsetX, banner.offsetY, banner.offsetZ);
        //        matrix.mulPose(Vector3f.YP.rotationDegrees(180 + blockRotation + banner.rotationY));
        //
        //        //        matrix.mulPose(Vector3f.ZP.rotationDegrees(-90 + banner.rotationZ));
        //        //        matrix.mulPose(Vector3f.XP.rotationDegrees(banner.rotationX));
        //
        //        for (BannerLayer bannerLayer : banner.layers) {
        //            if (!bannerLayer.isVisible(mc.player) || !(bannerLayer instanceof BannerTextLayer) || ((BannerTextLayer) bannerLayer).text.isEmpty()) {
        //                continue;
        //            }
        //
        //            BannerTextLayer layer = (BannerTextLayer) bannerLayer;
        //            int light = layer.glow
        //                ? 15728880
        //                : combinedLights;
        //
        //            String layerText = new TranslationTextComponent(layer.text).getString();
        //            String[] parts = layerText.split("\n");
        //            int fontWidth = 0;
        //            for (String part : parts) {
        //                int w = mc.font.width(part);
        //                if (w > fontWidth) {
        //                    fontWidth = w;
        //                }
        //            }
        //
        //            int width = (fontWidth + 20) / 2;
        //            int height = ((mc.font.lineHeight - 2) * parts.length) - 1;
        //
        //            matrix.pushPose();
        //            matrix.translate(0, 0, .1f);
        //
        //            RenderType text = layer.culling
        //                ? RenderType.text(BACKGROUND)
        //                : CustomRenders.transparencyRender(BACKGROUND);
        //
        //            IVertexBuilder vertexBuilder = buffer.getBuffer(text);
        //            float r = 0f, g = 0f, b = 0f, alpha = layer.bgAlpha;
        //            if (!layer.bgColor.isEmpty() && layer.bgColor.contains("#")) {
        //                Color color = Color.parseColor(layer.bgColor);
        //                int bg = color == null
        //                    ? 0x0000FF
        //                    : color.getValue();
        //
        //                r = ((bg >> 16) & 0xFF) / 255f;
        //                g = ((bg >> 8) & 0xFF) / 255f;
        //                b = (bg & 0xFF) / 255f;
        //            }
        //
        //            vertexBuilder.vertex(matrix.last().pose(), (float) -width, (float) -height, 0).color(r, g, b, alpha).uv(0.0F, 1.0F).uv2(light).endVertex();
        //            vertexBuilder.vertex(matrix.last().pose(), (float) -width, (float) height, 0).color(r, g, b, alpha).uv(1.0F, 1.0F).uv2(light).endVertex();
        //            vertexBuilder.vertex(matrix.last().pose(), (float) width, (float) height, 0).color(r, g, b, alpha).uv(1.0F, 0.0F).uv2(light).endVertex();
        //            vertexBuilder.vertex(matrix.last().pose(), (float) width, (float) -height, 0).color(r, g, b, alpha).uv(0.0F, 0.0F).uv2(light).endVertex();
        //
        //            matrix.popPose();
        //
        //            for (int i = 0; i < parts.length; i++) {
        //                float textWidth = (-width) + 10;
        //                if (!layer.alignment.equals("left")) {
        //                    textWidth = layer.alignment.equals("center")
        //                        ? -(mc.font.width(parts[i]) / 2f)
        //                        : (width - mc.font.width(parts[i])) - 10;
        //                }
        //
        //
        //                int textY = (-height + 14) + (i * (mc.font.lineHeight + 2));
        //                mc.font.drawShadow(matrix, parts[i], textWidth, textY, 0xFFFFFF);
        //                matrix.pushPose();
        //                matrix.mulPose(Vector3f.YP.rotationDegrees(180));
        //                matrix.translate(0, 0, -2);
        //                mc.font.drawShadow(matrix, parts[i], textWidth, textY, 0xFFFFFF);
        //                matrix.popPose();
        //            }
        //        }
        matrix.popPose();
    }
}
