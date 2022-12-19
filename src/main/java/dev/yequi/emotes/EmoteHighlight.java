package dev.yequi.emotes;

import java.awt.Color;
import lombok.Value;
import lombok.With;

@With
@Value
public class EmoteHighlight
{
	int spriteId;
	Color fillColor;
	Color borderColor;
	Color textColor;
	String label;
}
