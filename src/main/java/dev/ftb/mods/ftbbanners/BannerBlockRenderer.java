package dev.ftb.mods.ftbbanners;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author LatvianModder
 */
public class BannerBlockRenderer extends TileEntityRenderer<BannerBlockEntity> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(FTBBanners.MOD_ID, "textures/bg.png");

    public BannerBlockRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(BannerBlockEntity banner, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLights, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();

        float a = banner.alpha / 255F;
        if (a <= 0F) {
            return;
        }

        matrix.pushPose();
        matrix.translate(0.5D, 0.5D, 0.5D);
        matrix.scale(1F, 1F, -1F);
        matrix.translate(banner.offsetX, banner.offsetY, banner.offsetZ);

        float f = banner.getBlockState().getValue(HorizontalBlock.FACING).toYRot();
        matrix.mulPose(Vector3f.YP.rotationDegrees(-f + banner.rotationY));
        matrix.mulPose(Vector3f.ZP.rotationDegrees(-90 + banner.rotationZ));
        matrix.mulPose(Vector3f.XP.rotationDegrees(banner.rotationX));

        double w2 = banner.width / 2D;
        double h2 = banner.height / -2D;

        double wind1 = 0D;
        double wind2 = 0D;

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

        for (BannerLayer layer : banner.layers) {
            if (layer.isVisible(mc.player)) {
                int light = layer.glow
                    ? 15728880
                    : combinedLights;

                RenderType text = RenderType.text(layer.image);
                IVertexBuilder vertexBuilder = buffer.getBuffer(text);
                vertexBuilder.vertex(matrix.last().pose(), (float) -h2, (float) -w2, (float) wind1).color(1F, 1F, 1F, a).uv(0.0F, 1.0F).uv2(light).endVertex();
                vertexBuilder.vertex(matrix.last().pose(), (float) -h2, (float) w2, (float) wind2).color(1F, 1F, 1F, a).uv(1.0F, 1.0F).uv2(light).endVertex();
                vertexBuilder.vertex(matrix.last().pose(), (float) h2, (float) w2, 0).color(1F, 1F, 1F, a).uv(1.0F, 0.0F).uv2(light).endVertex();
                vertexBuilder.vertex(matrix.last().pose(), (float) h2, (float) -w2, 0).color(1F, 1F, 1F, a).uv(0.0F, 0.0F).uv2(light).endVertex();

                if (!layer.culling) {
                    // Renders the back
                    matrix.mulPose(Vector3f.XP.rotationDegrees(180));
                    vertexBuilder.vertex(matrix.last().pose(), (float) -h2, (float) -w2, (float) -wind2).color(1F, 1F, 1F, a).uv(0.0F, 1.0F).uv2(light).endVertex();
                    vertexBuilder.vertex(matrix.last().pose(), (float) -h2, (float) w2, (float) -wind1).color(1F, 1F, 1F, a).uv(1.0F, 1.0F).uv2(light).endVertex();
                    vertexBuilder.vertex(matrix.last().pose(), (float) h2, (float) w2, 0).color(1F, 1F, 1F, a).uv(1.0F, 0.0F).uv2(light).endVertex();
                    vertexBuilder.vertex(matrix.last().pose(), (float) h2, (float) -w2, 0).color(1F, 1F, 1F, a).uv(0.0F, 0.0F).uv2(light).endVertex();
                }
            }
        }

        matrix.popPose();

        matrix.pushPose();
        matrix.translate(0.5D, 0.5D, 0.5D);
        matrix.scale(-0.025F, -0.025F, 0.025F);
        matrix.mulPose(Vector3f.YP.rotationDegrees(180 + f + banner.rotationY));
        for (BannerLayer layer : banner.layers) {
            if (!layer.isVisible(mc.player) || layer.text.isEmpty()) {
                continue;
            }

            float scale = layer.textScale == 0
                ? 1f
                : layer.textScale;

            matrix.pushPose();
            matrix.scale(scale, scale, scale);

            int light = layer.glow
                ? 15728880
                : combinedLights;

            String layerText = new TranslationTextComponent(layer.text).getString();
            String[] parts = layerText.split("\n");
            int fontWidth = 0;
            for (String part : parts) {
                int w = mc.font.width(part);
                if (w > fontWidth) {
                    fontWidth = w;
                }
            }

            int width = (fontWidth + 20) / 2;
            int height = ((mc.font.lineHeight - 2) * parts.length) - 1;

            matrix.pushPose();
            matrix.translate(0, 0, .1f);
            IVertexBuilder textBuff = buffer.getBuffer(RenderType.text(BACKGROUND));
            float r = 0f, g = 0f, b = 0f, alpha = layer.textBackgroundAlpha;
            if (!layer.textBackground.isEmpty() && layer.textBackground.contains("#")) {
                Color color = Color.parseColor(layer.textBackground);
                int bg = color == null
                    ? 0x0000FF
                    : color.getValue();

                r = ((bg >> 16) & 0xFF) / 255f;
                g = ((bg >> 8) & 0xFF) / 255f;
                b = (bg & 0xFF) / 255f;
            }

            textBuff.vertex(matrix.last().pose(), (float) -width, (float) -height, 0).color(r, g, b, alpha).uv(0.0F, 1.0F).uv2(light).endVertex();
            textBuff.vertex(matrix.last().pose(), (float) -width, (float) height, 0).color(r, g, b, alpha).uv(1.0F, 1.0F).uv2(light).endVertex();
            textBuff.vertex(matrix.last().pose(), (float) width, (float) height, 0).color(r, g, b, alpha).uv(1.0F, 0.0F).uv2(light).endVertex();
            textBuff.vertex(matrix.last().pose(), (float) width, (float) -height, 0).color(r, g, b, alpha).uv(0.0F, 0.0F).uv2(light).endVertex();
            matrix.popPose();

            for (int i = 0; i < parts.length; i++) {
                float textWidth = (-width) + 10;
                if (!layer.textAlign.equals("left")) {
                    textWidth = layer.textAlign.equals("center")
                        ? -(mc.font.width(parts[i]) / 2f)
                        : (width - mc.font.width(parts[i])) - 10;
                }

                mc.font.drawShadow(matrix, parts[i], textWidth, (-height + 9) + (i * (mc.font.lineHeight + 2)), 0xFFFFFF);
            }

            matrix.popPose();
        }
        matrix.popPose();
    }

    private static final class CustomRender extends RenderType {
        public static final RenderType BACKGROUND = create("text", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, true, RenderType.State.builder().setAlphaState(DEFAULT_ALPHA).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setCullState(RenderState.NO_CULL).createCompositeState(false));

        public CustomRender(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
            super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
        }
    }
}
