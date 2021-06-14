package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import dev.ftb.mods.ftblibrary.util.ClientTextComponentUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class CachedText {
	public final FormattedCharSequence[] lines;
	public int fontWidth;
	public int width;
	public int[] widths;
	public float[] textX;

	public float bgR, bgG, bgB;

	public CachedText(BannerTextLayer layer, String[] slines) {
		Font font = Minecraft.getInstance().font;

		List<FormattedCharSequence> linesList = new ArrayList<>();

		for (String s : slines) {
			linesList.addAll(font.split(ClientTextComponentUtils.parse(s), 200));
		}

		lines = linesList.toArray(new FormattedCharSequence[0]);
		widths = new int[lines.length];
		textX = new float[lines.length];

		for (int i = 0; i < lines.length; i++) {
			widths[i] = font.width(lines[i]);
			fontWidth = Math.max(fontWidth, widths[i]);
		}

		width = (fontWidth + 20) / 2;

		for (int i = 0; i < lines.length; i++) {
			widths[i] = font.width(lines[i]);

			switch (layer.alignment) {
				case "center":
					textX[i] = -(widths[i] / 2F);
					break;
				case "right":
					textX[i] = (width - widths[i]) - 10;
					break;
				default:
					textX[i] = -width + 10;
			}
		}

		if (!layer.bgColor.isEmpty() && layer.bgColor.contains("#")) {
			TextColor color = TextColor.parseColor(layer.bgColor);
			int bg = color == null
					? 0x0000FF
					: color.getValue();

			bgR = ((bg >> 16) & 0xFF) / 255f;
			bgG = ((bg >> 8) & 0xFF) / 255f;
			bgB = (bg & 0xFF) / 255f;
		}
	}
}
