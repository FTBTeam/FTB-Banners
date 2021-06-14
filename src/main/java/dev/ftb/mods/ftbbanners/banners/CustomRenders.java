package dev.ftb.mods.ftbbanners.banners;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class CustomRenders extends RenderType {
	public CustomRenders(String string, VertexFormat arg, int i, int j, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
		super(string, arg, i, j, bl, bl2, runnable, runnable2);
	}

	public static RenderType transparencyRender(ResourceLocation p_228660_0_) {
		return create(
				"transparency_render",
				DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
				7,
				256,
				false,
				true,
				CompositeState.builder().setTextureState(new TextureStateShard(p_228660_0_, false, false))
						.setAlphaState(DEFAULT_ALPHA)
						.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
						.setLightmapState(LIGHTMAP)
						.setCullState(RenderStateShard.NO_CULL)
						.setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
						//.setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
						.createCompositeState(false)
		);
	}
}
