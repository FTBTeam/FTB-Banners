package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import dev.ftb.mods.ftblibrary.icon.Color4I;
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
	public float height;
	public int[] widths;
	public float[] textX;
	public float[] textY;

	public float bgR, bgG, bgB;
	public Color4I bgColor;

	public CachedText(BannerTextLayer layer, List<String> slines) {
		Font font = Minecraft.getInstance().font;

		List<FormattedCharSequence> linesList = new ArrayList<>();

		if (slines != null) {
			for (String s : slines) {
				if (s == null || s.isEmpty()) {
					linesList.add(FormattedCharSequence.EMPTY);
				} else {
					// linesList.addAll(font.split(ClientTextComponentUtils.parse(s), 200));
					linesList.add(ClientTextComponentUtils.parse(s).getVisualOrderText());
				}
			}
		}

		lines = linesList.toArray(new FormattedCharSequence[0]);
		widths = new int[lines.length];
		textX = new float[lines.length];
		textY = new float[lines.length];

		for (int i = 0; i < lines.length; i++) {
			widths[i] = font.width(lines[i]);
			fontWidth = Math.max(fontWidth, widths[i]);
		}

		width = (fontWidth + 20) / 2;
		height = ((font.lineHeight - 3.5f) * lines.length) + 3f;

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

			textY[i] = -height + (i * (font.lineHeight + 2)) + 4.5f;
		}

		if (!layer.bgColor.isEmpty() && layer.bgColor.contains("#")) {
			TextColor color = TextColor.parseColor(layer.bgColor);
			bgColor = Color4I.rgb(color == null ? 0x0000FF : color.getValue()).withAlphaf(layer.bgAlpha);
		} else {
			bgColor = Color4I.BLACK;
		}

		bgR = bgColor.redf();
		bgG = bgColor.bluef();
		bgB = bgColor.greenf();
	}
}
