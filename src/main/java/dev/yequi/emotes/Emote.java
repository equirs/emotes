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
 * Ordinals matter for saving EmoteHighlight items in config -> always add new emotes below existing
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
	GOBLIN_BOW("Goblin Bow", EMOTE_GOBLIN_BOW, EMOTE_GOBLIN_BOW_LOCKED),
	GOBLIN_SALUTE("Goblin Salute", EMOTE_GOBLIN_SALUTE, EMOTE_GOBLIN_SALUTE_LOCKED),

	GLASS_BOX("Glass Box", EMOTE_GLASS_BOX, EMOTE_GLASS_BOX_LOCKED),
	CLIMB_ROPE("Climb Rope", EMOTE_CLIMB_ROPE, EMOTE_CLIMB_ROPE_LOCKED),
	LEAN("Lean", EMOTE_LEAN, EMOTE_LEAN_LOCKED),
	GLASS_WALL("Glass Wall", EMOTE_GLASS_WALL, EMOTE_GLASS_BOX_LOCKED),

	IDEA("Idea", EMOTE_IDEA, EMOTE_IDEA_LOCKED),
	STAMP("Stamp", EMOTE_STAMP, EMOTE_STAMP_LOCKED),
	FLAP("Flap", EMOTE_FLAP, EMOTE_FLAP),
	SLAP_HEAD("Slap Head", EMOTE_SLAP_HEAD, EMOTE_SLAP_HEAD_LOCKED),

	ZOMBIE_WALK("Zombie Walk", EMOTE_ZOMBIE_WALK, EMOTE_ZOMBIE_WALK_LOCKED),
	ZOMBIE_DANCE("Zombie Dance", EMOTE_ZOMBIE_DANCE, EMOTE_ZOMBIE_DANCE_LOCKED),
	SCARED("Scared", EMOTE_SCARED, EMOTE_SCARED_LOCKED),
	RABBIT_HOP("Rabbit Hop", EMOTE_RABBIT_HOP, EMOTE_RABBIT_HOP_LOCKED),

	SIT_UP("Sit up", EMOTE_SIT_UP, EMOTE_SIT_UP_LOCKED),
	PUSH_UP("Push up", EMOTE_PUSH_UP, EMOTE_PUSH_UP_LOCKED),
	STAR_JUMP("Star jump", EMOTE_STAR_JUMP, EMOTE_STAR_JUMP_LOCKED),
	JOG("Jog", EMOTE_JOG, EMOTE_JOG_LOCKED),

	FLEX("Flex", ExtraSpriteID.FLEX, ExtraSpriteID.FLEX_LOCKED),
	ZOMBIE_HAND("Zombie Hand", EMOTE_ZOMBIE_HAND, EMOTE_ZOMBIE_HAND_LOCKED),
	HYPERMOBILE_DRINKER("Hypermobile Drinker", EMOTE_HYPERMOBILE_DRINKER, EMOTE_HYPERMOBILE_DRINKER_LOCKED),
	SKILL_CAPE("Skill Cape", EMOTE_SKILLCAPE, EMOTE_SKILLCAPE_LOCKED),

	AIR_GUITAR("Air Guitar", EMOTE_AIR_GUITAR, EMOTE_AIR_GUITAR_LOCKED),
	URI_TRANSFORM("Uri transform", EMOTE_URI_TRANSFORM, EMOTE_URI_TRANSFORM_LOCKED),
	SMOOTH_DANCE("Smooth dance", EMOTE_SMOOTH_DANCE, EMOTE_SMOOTH_DANCE_LOCKED),
	CRAZY_DANCE("Crazy dance", EMOTE_CRAZY_DANCE, EMOTE_CRAZY_DANCE_LOCKED),

	PREMIER_SHIELD("Premier Shield", EMOTE_PREMIER_SHIELD, EMOTE_PREMIER_SHIELD_LOCKED),
	EXPLORE("Explore", ExtraSpriteID.EXPLORE, ExtraSpriteID.EXPLORE_LOCKED),
	RELIC_UNLOCK("Relic unlock", ExtraSpriteID.RELIC_UNLOCK_TWISTED,
		ExtraSpriteID.RELIC_UNLOCK_TRAILBLAZER, ExtraSpriteID.FRAGMENT_UNLOCK, ExtraSpriteID.RELIC_UNLOCK_LOCKED),
	PARTY("Party", ExtraSpriteID.PARTY, ExtraSpriteID.PARTY_LOCKED),
	TRICK("Trick", ExtraSpriteID.TRICK, ExtraSpriteID.TRICK_LOCKED);

	private final String label;
	private final int[] spriteIds;

	public String toString()
	{
		return label;
	}

	Emote(String label, int... spriteIds)
	{
		this.label = label;
		this.spriteIds = spriteIds;
	}
}
