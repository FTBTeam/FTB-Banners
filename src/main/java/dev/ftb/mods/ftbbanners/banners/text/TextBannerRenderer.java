package dev.ftb.mods.ftbbanners.banners.text;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.ftb.mods.ftbbanners.FTBBanners;
import dev.ftb.mods.ftbbanners.banners.CustomRenders;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.Color;

/**
 * @author LatvianModder
 */
public class TextBannerRenderer extends TileEntityRenderer<TextBannerEntity> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(FTBBanners.MOD_ID, "textures/bg.png");

    public TextBannerRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TextBannerEntity banner, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLights, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        float blockRotation = banner.getBlockState().getValue(HorizontalBlock.FACING).toYRot();

        matrix.pushPose();
        matrix.translate(0.5D, 0.5D, 0.5D);
        matrix.translate(banner.offsetX, banner.offsetY, banner.offsetZ);
        matrix.scale(-0.025F, -0.025F, 0.025F);
        matrix.scale(banner.scale, banner.scale, banner.scale);
        matrix.mulPose(Vector3f.YP.rotationDegrees(180 + blockRotation + banner.rotationY));

        for (BannerTextLayer layer : banner.layers) {
            if (!layer.isVisible(mc.player) || layer.text.isEmpty()) {
                continue;
            }
            int light = layer.glow
                ? 15728880
                : combinedLights;

            String layerText = "Hello, you are\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello"; //new TranslationTextComponent(layer.text).getString();
            String[] parts = layerText.split("\n");
            int fontWidth = 0;
            for (String part : parts) {
                int w = mc.font.width(part);
                if (w > fontWidth) {
                    fontWidth = w;
                }
            }

            int width = (fontWidth + 20) / 2;
            int height = (mc.font.lineHeight + 2) * parts.length;

            matrix.pushPose();
            matrix.translate(0, 0, .1f);

            RenderType text = layer.culling
                ? RenderType.text(BACKGROUND)
                : CustomRenders.transparencyRender(BACKGROUND);

            IVertexBuilder vertexBuilder = buffer.getBuffer(text);
            float r = 0f, g = 0f, b = 0f, alpha = layer.bgAlpha;
            if (!layer.bgColor.isEmpty() && layer.bgColor.contains("#")) {
                Color color = Color.parseColor(layer.bgColor);
                int bg = color == null
                    ? 0x0000FF
                    : color.getValue();

                r = ((bg >> 16) & 0xFF) / 255f;
                g = ((bg >> 8) & 0xFF) / 255f;
                b = (bg & 0xFF) / 255f;
            }

            float bgHeight = height - (4f * parts.length);
            vertexBuilder.vertex(matrix.last().pose(), (float) -width, -bgHeight, 0.5f).color(r, g, b, alpha).uv(0.0F, 1.0F).uv2(light).endVertex();
            vertexBuilder.vertex(matrix.last().pose(), (float) -width, bgHeight, 0.5f).color(r, g, b, alpha).uv(1.0F, 1.0F).uv2(light).endVertex();
            vertexBuilder.vertex(matrix.last().pose(), (float) width, bgHeight, 0.5f).color(r, g, b, alpha).uv(1.0F, 0.0F).uv2(light).endVertex();
            vertexBuilder.vertex(matrix.last().pose(), (float) width, -bgHeight, 0.5f).color(r, g, b, alpha).uv(0.0F, 0.0F).uv2(light).endVertex();

            matrix.popPose();

            matrix.pushPose();
            matrix.translate(0, (-height) / 2f, 0);
            for (int i = 0; i < parts.length; i++) {
                float textWidth = (-width) + 10;
                if (!layer.alignment.equals("left")) {
                    textWidth = layer.alignment.equals("center")
                        ? -(mc.font.width(parts[i]) / 2f)
                        : (width - mc.font.width(parts[i])) - 10;
                }


                int textY = i * (mc.font.lineHeight + 2);
                if (layer.textShadow) {
                    mc.font.drawShadow(matrix, parts[i], textWidth, textY, 0xFFFFFF);
                } else {
                    mc.font.draw(matrix, parts[i], textWidth, textY, 0xFFFFFF);
                }

                if (!layer.culling) {
                    matrix.pushPose();
                    matrix.mulPose(Vector3f.YP.rotationDegrees(180));
                    matrix.translate(0, 0, -2);
                    if (layer.textShadow) {
                        mc.font.drawShadow(matrix, parts[i], textWidth, textY, 0xFFFFFF);
                    } else {
                        mc.font.draw(matrix, parts[i], textWidth, textY, 0xFFFFFF);
                    }
                    matrix.popPose();
                }
            }
            matrix.popPose();
        }
        matrix.popPose();
    }
}
