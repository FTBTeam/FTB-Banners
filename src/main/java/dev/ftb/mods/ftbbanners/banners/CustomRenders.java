package dev.ftb.mods.ftbbanners.banners;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;

import java.util.function.Function;

public final class CustomRenders extends RenderType {
	public CustomRenders(String name, VertexFormat format, VertexFormat.Mode drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable pre, Runnable post) {
		super(name, format, drawMode, bufferSize, useDelegate, needsSorting, pre, post);
	}

	private static final Function<ResourceLocation,RenderType> TRANSPARENCY_RENDER = Util.memoize((rl) -> {
		RenderType.CompositeState state = CompositeState.builder()
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setTextureState(new RenderStateShard.TextureStateShard(rl, false, false))
				.setLightmapState(LIGHTMAP)
				.setCullState(RenderStateShard.NO_CULL)
				.setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
				.setShaderState(RenderStateShard.RENDERTYPE_TEXT_SHADER)
				.createCompositeState(false);
		return create("transparency_render",
				DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256,
				false, true, state);
	});

	public static RenderType transparencyRender(ResourceLocation texture) {
		return TRANSPARENCY_RENDER.apply(texture);
	}
}
