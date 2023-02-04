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
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.inject.Provides;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.KeyCode;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.ScriptID;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Emotes",
	description = "Highlights and labels for emotes"
)
public class EmotesPlugin extends Plugin
{
	static final Set<Integer> ALL_EMOTE_SPRITES = Arrays.stream(Emote.values())
		.map(Emote::getSpriteId)
		.collect(Collectors.toSet());

	@Inject
	private Client client;

	@Inject
	private EmoteOverlay overlay;

	@Inject
	private EmotesConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private Gson gson;

	@Getter
	private Map<Integer, EmoteHighlight> highlights = new HashMap<>();

	private boolean shouldScroll;

	@Provides
	EmotesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EmotesConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		refreshHighlights();
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals(EmotesConfig.GROUP) || !event.getKey().equals(EmotesConfig.KEY_SAVED))
		{
			return;
		}
		refreshHighlights();
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		// re-perform the scroll every time the widget is reloaded (normally would scroll back to top)
		if (event.getGroupId() == WidgetID.EMOTES_GROUP_ID)
		{
			shouldScroll = true;
		}
	}

	@Subscribe
	public void onMenuOpened(MenuOpened event)
	{
		if (!client.isKeyPressed(KeyCode.KC_SHIFT))
		{
			return;
		}
		Widget emoteWidget = Arrays.stream(event.getMenuEntries())
			.map(MenuEntry::getWidget)
			.filter(w -> w != null && ALL_EMOTE_SPRITES.contains(w.getSpriteId()))
			.findFirst()
			.orElse(null);
		if (emoteWidget != null)
		{
			final int spriteId = emoteWidget.getSpriteId();
			final EmoteHighlight highlight = highlights.get(spriteId);
			final boolean highlighted = highlight != null;
			client.createMenuEntry(1)
				.setOption(highlighted ? "Remove highlight" : "Add highlight")
				.setTarget("")
				.setType(MenuAction.RUNELITE)
				.onClick(e -> toggleHighlight(spriteId, highlighted));
			if (highlighted && config.rememberEmoteColors())
			{
				client.createMenuEntry(1)
					.setOption("Update colors")
					.setTarget("")
					.setType(MenuAction.RUNELITE)
					.onClick(e -> updateColors(highlight));
			}
			if (highlighted && config.displayLabels())
			{
				client.createMenuEntry(1)
					.setOption("Change label")
					.setTarget("")
					.setType(MenuAction.RUNELITE)
					.onClick(e -> editLabel(spriteId, highlight.getLabel()));
			}
		}
	}

	void scrollToHighlight(Widget widget)
	{
		if (!shouldScroll || !config.scrollToHighlighted() || widget == null)
		{
			return;
		}
		final Widget parent = client.getWidget(WidgetInfo.EMOTE_SCROLL_CONTAINER);
		if (parent == null)
		{
			return;
		}
		shouldScroll = false;
		final int y = widget.getRelativeY() + widget.getHeight() / 2;
		final int scroll = Math.max(0, Math.min(parent.getScrollHeight(), y - parent.getHeight() / 2));
		client.runScript(ScriptID.UPDATE_SCROLLBAR, WidgetInfo.EMOTE_SCROLLBAR.getId(),
			WidgetInfo.EMOTE_SCROLL_CONTAINER.getId(), scroll);
	}

	private void toggleHighlight(int spriteId, boolean highlighted)
	{
		if (highlighted)
		{
			highlights.remove(spriteId);
		}
		else
		{
			EmoteHighlight highlight = new EmoteHighlight(spriteId, config.fillColor(), config.borderColor(),
				config.labelColor(), "");
			highlights.put(spriteId, highlight);
		}
		saveHighlights();
	}

	private void updateColors(EmoteHighlight highlight)
	{
		EmoteHighlight newHighlight = new EmoteHighlight(highlight.getSpriteId(), config.fillColor(),
			config.borderColor(), config.labelColor(), highlight.getLabel());
		highlights.put(highlight.getSpriteId(), newHighlight);
		saveHighlights();
	}

	private void editLabel(int spriteId, String currentLabel)
	{
		chatboxPanelManager.openTextInput("Emote label")
			.value(Optional.ofNullable(currentLabel).orElse(""))
			.onDone(label -> {
				EmoteHighlight highlight = highlights.get(spriteId);
				if (highlight != null)
				{
					highlights.put(spriteId, highlight.withLabel(label));
					saveHighlights();
				}
			})
			.build();
	}

	private void refreshHighlights()
	{
		String json = config.savedHighlightInfo();
		if (Strings.isNullOrEmpty(json))
		{
			return;
		}
		Type type = new TypeToken<Collection<EmoteHighlight>>()
		{
		}.getType();
		Collection<EmoteHighlight> saved = gson.fromJson(json, type);
		highlights = saved.stream()
			.collect(Collectors.toMap(EmoteHighlight::getSpriteId, s -> s));
	}

	private void saveHighlights()
	{
		config.setSavedHighlightInfo(gson.toJson(highlights.values()));
	}
}
