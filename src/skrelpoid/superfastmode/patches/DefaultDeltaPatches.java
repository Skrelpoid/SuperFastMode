package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

//All methods patched in this class should always get the real delta time

public class DefaultDeltaPatches {
	// @formatter:off

	// Makes time not increase by multiplier.
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "update")
	// Fixes mouse events not registering on map and flickering "Select a Starting Room"
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.DungeonMapScreen", method = "updateMouse")
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.DungeonMapScreen", method = "oscillateColor")
	// Display SpeechBubbles long enough to read
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.SpeechWord", method = "applyEffects")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechBubble", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechBubble", method = "updateScale")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.CloudBubble", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.ThoughtBubble", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble", method = "updateScale")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.ShopSpeechBubble", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.ShopSpeechBubble", method = "updateScale")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechTextEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.MegaSpeechBubble", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.MegaSpeechBubble", method = "updateScale")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.MegaDialogTextEffect", method = "update")
	// Next prevent some flickering and make UI independent from multiplied delta
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.DynamicBanner", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.TintEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.CancelButton", method = "updateGlow")
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton", method = "updateGlow")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.ConfirmButton", method = "updateGlow")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.EndTurnButton", method = "glow")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton", method = "updateGlow")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.ProceedButton", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.map.MapRoomNode", method = "oscillateColor")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.TopPanel", method = "updateSettingsButtonLogic")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.TopPanel", method = "updateDeckViewButtonLogic")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.TopPanel", method = "updateMapButtonLogic")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.TopPanel", method = "updatePotions")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.EnergyPanel", method = "updateRed")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.EnergyPanel", method = "updateVfx")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.DiscardGlowEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.DiscardPilePanel", method = "updatePositions")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.ExhaustPileParticle", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.ExhaustPanel", method = "updatePositions")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.GameDeckGlowEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.DrawPilePanel", method = "updatePositions")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.EnergyPanel", method = "updateVfx")
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "updateGlow")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.rewards.RewardItem", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.RewardGlowEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.EmptyOrbSlot", method = "updateAnimation")
	// Intents
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.BobEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateIntentVFX")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateIntent")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "render")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "renderName")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "renderIntent")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateDeathAnimation")
	@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateEscapeAnimation")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.combat.StunStarEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.ShieldParticleEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.DebuffParticleEffect", method = "update")
	// Orbs
	@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Dark", method = "updateAnimation")
	@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Frost", method = "updateAnimation")
	@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Lightning", method = "updateAnimation")
	@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Plasma", method = "updateAnimation")
	// MainMenuScreen
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TitleBackground", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TitleBackground", method = "updateFlame")
	@SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TitleBackground", method = "updateDust")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.scene.TitleDustEffect", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.RenamePanel", method = "update")
	// Credits
	@SpirePatch(cls = "com.megacrit.cardcrawl.credits.CreditsScreen", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.credits.CreditsScreen", method = "updateFade")
	@SpirePatch(cls = "com.megacrit.cardcrawl.credits.CreditsScreen", method = "skipLogic")
	// Splash
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.splash.SplashScreen", method = "update")
	// Text effects
	@SpirePatch(cls = "com.megacrit.cardcrawl.events.GenericEventDialog", method = "bodyTextEffectCN")
	@SpirePatch(cls = "com.megacrit.cardcrawl.events.GenericEventDialog", method = "bodyTextEffect")
	@SpirePatch(cls = "com.megacrit.cardcrawl.events.RoomEventDialog", method = "bodyTextEffectCN")
	@SpirePatch(cls = "com.megacrit.cardcrawl.events.RoomEventDialog", method = "bodyTextEffect")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.DialogWord", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.ui.DialogWord", method = "applyEffects")
	// ShopScreen
	@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "update")
	@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "updateSpeech")
	@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "updateHand")
	public static class DeltaPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getDeltaTime")) {
						m.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.getDelta(); }");
					}
				}
			};
		}
	}

	// Fixes Intents sometimes not being visible
	@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.combat.BattleStartEffect", method = "update")
	public static class IntentFix {
		@SpireInsertPatch(rloc = 10)
		public static void Insert(@SuppressWarnings("unused") BattleStartEffect effect) {
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
				if (m.intent == Intent.DEBUG) {
					m.createIntent();
				}
			}
		}
	}
}
