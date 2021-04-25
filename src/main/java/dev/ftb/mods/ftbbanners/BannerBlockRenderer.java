package dev.ftb.mods.ftbbanners;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author LatvianModder
 */
public class BannerBlockRenderer extends TileEntityRenderer<BannerBlockEntity> {

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
        float lx = 0F, ly = 0F;

        //        RenderSystem.pushMatrix();
        //        RenderSystem.disableLighting();
        //        RenderHelper.disableStandardItemLighting();
        //        RenderHelper.enableTexture2D();
        //        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
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

        //        RenderSystem.enableBlend();
        //        RenderSystem.alphaFunc(GL11.GL_GREATER, 0F);
        for (BannerLayer layer : banner.layers) {
            if (layer.isVisible(mc.player)) {
                if (layer.glow) {
                    //                    lx = OpenGlHelper.lastBrightnessX;
                    //                    ly = OpenGlHelper.lastBrightnessY;
                    //                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
                }

                int light = layer.glow
                    ? 15728880
                    : combinedLights;

                RenderType text = RenderType.text(layer.image);
                IVertexBuilder vertexBuilder = buffer.getBuffer(text);
                Matrix4f pose = matrix.last().pose();

                vertexBuilder.vertex(pose, (float) -h2, (float) -w2, 0).color(1F, 1F, 1F, a).uv(0.0F, 1.0F).uv2(light).endVertex();
                vertexBuilder.vertex(pose, (float) -h2, (float) w2, (float) wind1).color(1F, 1F, 1F, a).uv(1.0F, 1.0F).uv2(light).endVertex();
                vertexBuilder.vertex(pose, (float) h2, (float) w2, (float) wind2).color(1F, 1F, 1F, a).uv(1.0F, 0.0F).uv2(light).endVertex();
                vertexBuilder.vertex(pose, (float) h2, (float) -w2, 0).color(1F, 1F, 1F, a).uv(0.0F, 0.0F).uv2(light).endVertex();

                if (!layer.culling) {
                    // Renders the back
                    matrix.mulPose(Vector3f.XP.rotationDegrees(180));
                    vertexBuilder.vertex(pose, (float) -h2, (float) -w2, (float) -wind1).color(1F, 1F, 1F, a).uv(0.0F, 1.0F).uv2(light).endVertex();
                    vertexBuilder.vertex(pose, (float) -h2, (float) w2, 0).color(1F, 1F, 1F, a).uv(1.0F, 1.0F).uv2(light).endVertex();
                    vertexBuilder.vertex(pose, (float) h2, (float) w2, 0).color(1F, 1F, 1F, a).uv(1.0F, 0.0F).uv2(light).endVertex();
                    vertexBuilder.vertex(pose, (float) h2, (float) -w2, (float) -wind2).color(1F, 1F, 1F, a).uv(0.0F, 0.0F).uv2(light).endVertex();
                }
            }
        }

        //        RenderSystem.disableBlend();
        //        RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
        //        RenderSystem.enableLighting();
        //        RenderSystem.popMatrix();
        matrix.popPose();
    }
}
