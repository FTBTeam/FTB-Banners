package dev.ftb.mods.ftbbanners.banners.text;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbbanners.layers.BannerTextLayer;
import dev.ftb.mods.ftbbanners.net.UpdateBannerPacket;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.ClientUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public class TextBannerLayerScreen extends BaseScreen {
	public final TextBannerEntity entity;
	public final BannerTextLayer layer;

	public Button previewButton;
	public Button nbtButton;
	public Button deleteButton;
	public Panel textPanel;

	public TextBannerLayerScreen(TextBannerEntity e, BannerTextLayer l) {
		entity = e;
		layer = l;
		setSize(300, 200);
	}

	@Override
	public void addWidgets() {
		add(previewButton = new SimpleTextButton(this, Component.literal("Preview"), Icons.ART) {
			@Override
			public void onClicked(MouseButton mouseButton) {
				playClickSound();
				entity.clearCache();
				new PreviewScreen().openGui();
			}
		});

		add(nbtButton = new SimpleTextButton(this, Component.literal("Edit NBT"), Icons.NOTES) {
			@Override
			public void onClicked(MouseButton mouseButton) {
				playClickSound();
				new UpdateBannerPacket(entity).sendToServer();
				BlockPos pos = entity.getBlockPos();
				ClientUtils.execClientCommand("/ftblibrary nbtedit block " + pos.getX() + " " + pos.getY() + " " + pos.getZ(), true);
			}
		});

		add(deleteButton = new SimpleTextButton(this, Component.literal("Delete Layer"), Icons.REMOVE) {
			@Override
			public void onClicked(MouseButton mouseButton) {
				playClickSound();
			}
		});

		add(textPanel = new Panel(this) {
			@Override
			public void addWidgets() {
				setPosAndSize(5, 21, parent.width - 10, parent.height - 26);

				if (layer.text.isEmpty()) {
					layer.text.add("");
				}

				for (int i = 0; i < layer.text.size(); i++) {
					final int j = i;

					TextBox box = new TextBox(this) {
						@Override
						public void onTextChanged() {
							layer.text.set(j, getText());
							layer.clearCache();
						}

						@Override
						public void onEnterPressed() {
							super.onEnterPressed();
							layer.text.add(j + 1, "");
							textPanel.refreshWidgets();
							setOnlyOneFocused(j + 1);
						}

						@Override
						public void deleteFromCursor(int num) {
							if (getText().isEmpty()) {
								layer.text.remove(j);
								textPanel.refreshWidgets();
								setOnlyOneFocused(j - 1);
							} else {
								super.deleteFromCursor(num);
							}
						}

						@Override
						public boolean mousePressed(MouseButton button) {
							setOnlyOneFocused(-1);

							if (button.isRight()) {
								return false;
							}

							return super.mousePressed(button);
						}

						@Override
						public boolean keyPressed(Key key) {
							if (isFocused()) {
								if (key.is(GLFW.GLFW_KEY_UP)) {
									if (j > 0) {
										setOnlyOneFocused(j - 1);
										return true;
									}
								} else if (key.is(GLFW.GLFW_KEY_DOWN)) {
									if (j < layer.text.size() - 1) {
										setOnlyOneFocused(j + 1);
										return true;
									}
								}
							}

							return super.keyPressed(key);
						}
					};

					box.setPosAndSize(0, i * 14, width, 14);
					box.setText(layer.text.get(j), false);
					add(box);

					if (j == layer.text.size() - 1) {
						box.setFocused(true);
						box.setCursorPosition(box.getText().length());
					}
				}
			}

			@Override
			public void alignWidgets() {
				for (Widget w : widgets) {
					if (w instanceof TextBox) {
						w.setWidth(width);
					}
				}

				align(WidgetLayout.VERTICAL);
			}

			@Override
			public void drawBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
				Color4I.GRAY.draw(matrixStack, x, y, w, h);
			}
		});
	}

	private void setOnlyOneFocused(int index) {
		for (Widget w : textPanel.widgets) {
			((TextBox) w).setFocused(false);
		}

		if (index >= 0 && index < textPanel.widgets.size()) {
			TextBox box = ((TextBox) textPanel.widgets.get(index));
			box.setFocused(true);
			box.setCursorPosition(box.getText().length());
		}
	}

	@Override
	public void alignWidgets() {
		previewButton.setPosAndSize(5, 5, 60, 14);
		nbtButton.setPosAndSize(70, 5, 60, 14);
		deleteButton.setPosAndSize(135, 5, 87, 14);
		textPanel.alignWidgets();
	}

	@Override
	public void onClosed() {
		super.onClosed();
		new UpdateBannerPacket(entity).sendToServer();
	}

	public class PreviewScreen extends BaseScreen {
		@Override
		public boolean onInit() {
			return setFullscreen();
		}

		@Override
		public void addWidgets() {
		}

		@Override
		public void onClosed() {
			TextBannerLayerScreen.this.openGuiLater();
		}

		@Override
		public void drawBackground(PoseStack matrixStack, Theme theme, int x, int y, int w, int h) {
			CachedText text = layer.getText();
			int w1 = text.width * 2;
			int h1 = Mth.ceil(text.height * 2F);

			text.bgColor.draw(matrixStack, (w - w1) / 2, (h - h1) / 2, w1, h1);

			int flags = layer.textShadow ? Theme.SHADOW : 0;

			for (int i = 0; i < text.lines.length; i++) {
				theme.drawString(matrixStack, text.lines[i], text.textX[i] + w / 2F, text.textY[i] + h / 2F, Color4I.WHITE, flags);
			}
		}

		@Override
		public boolean drawDefaultBackground(PoseStack matrixStack) {
			return true;
		}
	}
}
