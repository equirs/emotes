package dev.yequi.emotes;

import java.awt.Color;
import lombok.Value;
import lombok.With;

@With
@Value
public class EmoteHighlight
{
	// as of v0.0.4, this is the Emote enum ordinal, not the sprite id
	int spriteId;
	Color fillColor;
	Color borderColor;
	Color textColor;
	String label;
}
