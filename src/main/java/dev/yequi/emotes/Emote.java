/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package dev.yequi.emotes;

import lombok.Getter;
import static net.runelite.api.SpriteID.*;

/**
 * Represents an emote in the emotes panel.
 */
@Getter
public enum Emote
{
	YES("Yes", EMOTE_YES),
	NO("No", EMOTE_NO),
	BOW("Bow", EMOTE_BOW),
	ANGRY("Angry", EMOTE_ANGRY),

	THINK("Think", EMOTE_THINK),
	WAVE("Wave", EMOTE_WAVE),
	SHRUG("Shrug", EMOTE_SHRUG),
	CHEER("Cheer", EMOTE_CHEER),

	BECKON("Beckon", EMOTE_BECKON),
	LAUGH("Laugh", EMOTE_LAUGH),
	JUMP_FOR_JOY("Jump for Joy", EMOTE_JUMP_FOR_JOY),
	YAWN("Yawn", EMOTE_YAWN),

	DANCE("Dance", EMOTE_DANCE),
	JIG("Jig", EMOTE_JIG),
	SPIN("Spin", EMOTE_SPIN),
	HEADBANG("Headbang", EMOTE_HEADBANG),

	CRY("Cry", EMOTE_CRY),
	BLOW_KISS("Blow Kiss", EMOTE_BLOW_KISS),
	PANIC("Panic", EMOTE_PANIC),
	RASPBERRY("Raspberry", EMOTE_RASPBERRY),

	CLAP("Clap", EMOTE_CLAP),
	SALUTE("Salute", EMOTE_SALUTE),
	GOBLIN_BOW("Goblin Bow", EMOTE_GOBLIN_BOW),
	GOBLIN_SALUTE("Goblin Salute", EMOTE_GOBLIN_SALUTE),

	GLASS_BOX("Glass Box", EMOTE_GLASS_BOX),
	CLIMB_ROPE("Climb Rope", EMOTE_CLIMB_ROPE),
	LEAN("Lean", EMOTE_LEAN),
	GLASS_WALL("Glass Wall", EMOTE_GLASS_WALL),

	IDEA("Idea", EMOTE_IDEA),
	STAMP("Stamp", EMOTE_STAMP),
	FLAP("Flap", EMOTE_FLAP),
	SLAP_HEAD("Slap Head", EMOTE_SLAP_HEAD),

	ZOMBIE_WALK("Zombie Walk", EMOTE_ZOMBIE_WALK),
	ZOMBIE_DANCE("Zombie Dance", EMOTE_ZOMBIE_DANCE),
	SCARED("Scared", EMOTE_SCARED),
	RABBIT_HOP("Rabbit Hop", EMOTE_RABBIT_HOP),

	SIT_UP("Sit up", EMOTE_SIT_UP),
	PUSH_UP("Push up", EMOTE_PUSH_UP),
	STAR_JUMP("Star jump", EMOTE_STAR_JUMP),
	JOG("Jog", EMOTE_JOG),

	FLEX("Flex", 2426),
	ZOMBIE_HAND("Zombie Hand", EMOTE_ZOMBIE_HAND),
	HYPERMOBILE_DRINKER("Hypermobile Drinker", EMOTE_HYPERMOBILE_DRINKER),
	SKILL_CAPE("Skill Cape", EMOTE_SKILLCAPE),

	AIR_GUITAR("Air Guitar", EMOTE_AIR_GUITAR),
	URI_TRANSFORM("Uri transform", EMOTE_URI_TRANSFORM),
	SMOOTH_DANCE("Smooth dance", EMOTE_SMOOTH_DANCE),
	CRAZY_DANCE("Crazy dance", EMOTE_CRAZY_DANCE),

	PREMIER_SHIELD("Premier Shield", EMOTE_PREMIER_SHIELD),
	EXPLORE("Explore", 2423),
	RELIC_UNLOCK("Relic unlock", 2425);

	private final String name;
	private final int spriteId;

	Emote(String name, int spriteId)
	{
		this.name = name;
		this.spriteId = spriteId;
	}
}
