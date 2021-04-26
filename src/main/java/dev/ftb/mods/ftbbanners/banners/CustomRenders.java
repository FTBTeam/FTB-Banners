package dev.ftb.mods.ftbbanners.banners;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

public final class CustomRenders extends RenderType {
    public CustomRenders(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }

    public static RenderType transparencyRender(ResourceLocation p_228660_0_) {
        return create(
            "transparency_render",
            DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP,
            7,
            256,
            false,
            true,
            State.builder().setTextureState(new TextureState(p_228660_0_, false, false))
                .setAlphaState(DEFAULT_ALPHA)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setCullState(RenderState.NO_CULL)
                .setWriteMaskState(RenderState.COLOR_DEPTH_WRITE)
                //                .setDepthTestState(RenderState.NO_DEPTH_TEST)
                .createCompositeState(false)
        );
    }
}
