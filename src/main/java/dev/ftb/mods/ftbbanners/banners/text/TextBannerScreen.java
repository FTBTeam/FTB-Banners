package dev.ftb.mods.ftbbanners.banners.text;

import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import dev.ftb.mods.ftbbanners.net.UpdateBannerPacket;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.ButtonListBaseScreen;
import net.minecraft.network.chat.Component;

public class TextBannerScreen extends ButtonListBaseScreen {
	public final TextBannerEntity entity;

	public TextBannerScreen(TextBannerEntity e) {
		entity = e;
	}

	@Override
	public void addButtons(Panel panel) {
		for (int i = 0; i < entity.layers.size(); i++) {
			final int j = i;
			panel.add(new SimpleTextButton(panel, Component.literal("Layer " + (j + 1)), Icons.ART) {
				@Override
				public void onClicked(MouseButton mouseButton) {
					playClickSound();
					new TextBannerLayerScreen(entity, entity.layers.get(j)).openGui();
				}
			});
		}

		panel.add(new SimpleTextButton(panel, Component.translatable("gui.add"), Icons.ADD) {
			@Override
			public void onClicked(MouseButton mouseButton) {
				playClickSound();
				BannerTextLayer layer = new BannerTextLayer();
				layer.text.clear();
				layer.text.add("Enter text here");
				entity.layers.add(layer);
				refreshWidgets();
				new UpdateBannerPacket(entity).sendToServer();
			}
		});
	}

	@Override
	public void onClosed() {
		super.onClosed();
		new UpdateBannerPacket(entity).sendToServer();
	}
}
