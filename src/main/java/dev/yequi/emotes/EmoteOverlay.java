/*
 * Copyright (c) 2016-2017, Seth <Sethtroll3@gmail.com>
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package dev.yequi.emotes;

import com.google.common.base.Strings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.TextComponent;

public class EmoteOverlay extends Overlay
{
	private final EmotesPlugin plugin;
	private final EmotesConfig config;
	private final Client client;
	private final TextComponent textComponent = new TextComponent();

	@Inject
	private EmoteOverlay(EmotesPlugin plugin, EmotesConfig config, Client client)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		this.plugin = plugin;
		this.config = config;
		this.client = client;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Widget emoteContainer = client.getWidget(WidgetInfo.EMOTE_CONTAINER);
		if (emoteContainer == null || emoteContainer.isHidden())
		{
			return null;
		}
		Widget emoteWindow = client.getWidget(WidgetInfo.EMOTE_WINDOW);
		if (emoteWindow == null)
		{
			return null;
		}
		Map<Integer, EmoteHighlight> highlights = plugin.getHighlights();
		int[] spriteIds = config.emoteToScrollTo().getSpriteIds();
		for (Widget emoteWidget : emoteContainer.getDynamicChildren())
		{
			// scroll to the specified item
			for (int spriteId : spriteIds)
			{
				if (spriteId == emoteWidget.getSpriteId())
				{
					plugin.scrollToHighlight(emoteWidget);
					break;
				}
			}
			// highlight the emote
			Emote emote = EmotesPlugin.emoteFromWidget(emoteWidget);
			if (emote != null)
			{
				EmoteHighlight value = highlights.get(emote.ordinal());
				if (value != null)
				{
					highlight(graphics, value, emoteWidget, emoteWindow);
				}
			}
		}
		return null;
	}

	private void highlight(Graphics2D graphics, EmoteHighlight value, Widget emoteWidget, Widget container)
	{
		Point canvasLocation = emoteWidget.getCanvasLocation();
		if (canvasLocation == null)
		{
			return;
		}

		Point windowLocation = container.getCanvasLocation();
		if (windowLocation.getY() > canvasLocation.getY() + emoteWidget.getHeight()
			|| windowLocation.getY() + container.getHeight() < canvasLocation.getY())
		{
			return;
		}

		// Visible area of widget
		Area widgetArea = new Area(
			new Rectangle(
				canvasLocation.getX(),
				Math.max(canvasLocation.getY(), windowLocation.getY()),
				emoteWidget.getWidth(),
				Math.min(
					Math.min(windowLocation.getY() + container.getHeight() - canvasLocation.getY(), emoteWidget.getHeight()),
					Math.min(canvasLocation.getY() + emoteWidget.getHeight() - windowLocation.getY(), emoteWidget.getHeight()))
			));

		Color fillColor = config.rememberEmoteColors() ? value.getFillColor() : config.fillColor();
		Color borderColor = config.rememberEmoteColors() ? value.getBorderColor() : config.borderColor();
		Color borderHoverColor = borderColor.darker();
		OverlayUtil.renderHoverableArea(graphics, widgetArea, client.getMouseCanvasPosition(), fillColor, borderColor,
			borderHoverColor);

		String text = value.getLabel();
		if (!config.displayLabels() || Strings.isNullOrEmpty(text)
			|| emoteWidget.getHeight() + canvasLocation.getY() > windowLocation.getY() + container.getHeight())
		{
			return;
		}
		Color textColor = config.rememberEmoteColors() ? value.getTextColor() : config.labelColor();
		FontMetrics fontMetrics = graphics.getFontMetrics();
		textComponent.setPosition(new java.awt.Point(
			canvasLocation.getX() + emoteWidget.getWidth() / 2 - fontMetrics.stringWidth(text) / 2,
			canvasLocation.getY() + emoteWidget.getHeight()));
		textComponent.setText(text);
		textComponent.setColor(textColor);
		textComponent.render(graphics);
	}
}
