package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.scenes.TitleCloud;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.ui.buttons.CardSelectConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.ui.panels.BottomBgPanel;
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;
import com.megacrit.cardcrawl.vfx.EnemyTurnEffect;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.GameDeckGlowEffect;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import com.megacrit.cardcrawl.vfx.RoomShineEffect;
import com.megacrit.cardcrawl.vfx.RoomShineEffect2;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashIntentEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.HealNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerIconShowEffect;
import com.megacrit.cardcrawl.vfx.scene.BottomFogEffect;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

//All methods patched in this class should always get the real delta time

public class DefaultDeltaPatches {
	// @formatter:off

	@SpirePatch(clz = CardCrawlGame.class, method = "updateFade")
	// Makes time not increase by multiplier.
	@SpirePatch(clz = com.megacrit.cardcrawl.dungeons.AbstractDungeon.class, method = "update")
	// Fixes mouse events not registering on map and flickering "Select a Starting Room"
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "updateAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "updateMouse")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.DungeonMapScreen.class, method = "oscillateColor")
	// Display SpeechBubbles long enough to read
	@SpirePatch(clz = DialogWord.class, method = "update")
	@SpirePatch(clz = DialogWord.class, method = "applyEffects")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.SpeechWord.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.SpeechWord.class, method = "applyEffects")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.SpeechBubble.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.SpeechBubble.class, method = "updateScale")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.CloudBubble.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.ThoughtBubble.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble.class, method = "updateScale")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.ShopSpeechBubble.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.ShopSpeechBubble.class, method = "updateScale")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.SpeechTextEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.MegaSpeechBubble.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.MegaSpeechBubble.class, method = "updateScale")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.MegaDialogTextEffect.class, method = "update")
	// Next prevent some flickering and make UI independent from multiplied delta
	@SpirePatch(clz = CardSelectConfirmButton.class, method = "update")
	@SpirePatch(clz = SingingBowlButton.class, method = "update")
	@SpirePatch(clz = SkipCardButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.DynamicBanner.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.TintEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.CancelButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.CancelButton.class, method = "updateGlow")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton.class, method = "updateGlow")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.ConfirmButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.ConfirmButton.class, method = "updateGlow")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.EndTurnButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.EndTurnButton.class, method = "glow")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton.class, method = "updateGlow")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.ProceedButton.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.map.MapRoomNode.class, method = "oscillateColor")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.TopPanel.class, method = "updateSettingsButtonLogic")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.TopPanel.class, method = "updateDeckViewButtonLogic")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.TopPanel.class, method = "updateMapButtonLogic")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.TopPanel.class, method = "updatePotions")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.DiscardGlowEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.DiscardPilePanel.class, method = "updatePositions")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.ExhaustPileParticle.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.ExhaustPanel.class, method = "updatePositions")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.GameDeckGlowEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.DrawPilePanel.class, method = "updatePositions")
	@SpirePatch(clz = com.megacrit.cardcrawl.cards.AbstractCard.class, method = "updateGlow")
	@SpirePatch(clz = com.megacrit.cardcrawl.cards.AbstractCard.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.cards.AbstractCard.class, method = "updateHoverLogic")
	@SpirePatch(clz = com.megacrit.cardcrawl.cards.AbstractCard.class, method = "updateColor")
	@SpirePatch(clz = com.megacrit.cardcrawl.cards.AbstractCard.class, method = "updateTransparency")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.rewards.RewardItem.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.RewardGlowEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.orbs.EmptyOrbSlot.class, method = "updateAnimation")
	//Energy
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.EnergyPanel.class, method = "updateVfx")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue.class, method = "updateOrb")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed.class, method = "updateOrb")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen.class, method = "updateOrb")
	@SpirePatch(clz = basemod.abstracts.CustomEnergyOrb.class, method = "updateOrb")
	// Intents
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.BobEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "updateIntentVFX")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "updateIntent")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "render")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "renderName")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "renderIntent")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "updateDeathAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.monsters.AbstractMonster.class, method = "updateEscapeAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.combat.StunStarEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.combat.UnknownParticleEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.ShieldParticleEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.combat.BuffParticleEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.DebuffParticleEffect.class, method = "update")
	// Orbs
	@SpirePatch(clz = com.megacrit.cardcrawl.orbs.Dark.class, method = "updateAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.orbs.Frost.class, method = "updateAnimation")
	@SpirePatch(clz = FrostOrbPassiveEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.orbs.Lightning.class, method = "updateAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.orbs.Plasma.class, method = "updateAnimation")
	// Relic
	@SpirePatch(clz = com.megacrit.cardcrawl.relics.AbstractRelic.class, method = "updateFlash")
	@SpirePatch(clz = com.megacrit.cardcrawl.relics.AbstractRelic.class, method = "updateAnimation")
	@SpirePatch(clz = com.megacrit.cardcrawl.relics.AbstractRelic.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.FloatyEffect.class, method = "update")
	// MainMenuScreen
	@SpirePatch(clz = com.megacrit.cardcrawl.scenes.TitleBackground.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.scenes.TitleBackground.class, method = "updateFlame")
	@SpirePatch(clz = com.megacrit.cardcrawl.scenes.TitleBackground.class, method = "updateDust")
	@SpirePatch(clz = TitleCloud.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.LogoFlameEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.TitleDustEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.panels.RenamePopup.class, method = "update")
	// Credits
	@SpirePatch(clz = com.megacrit.cardcrawl.credits.CreditsScreen.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.credits.CreditsScreen.class, method = "updateFade")
	@SpirePatch(clz = com.megacrit.cardcrawl.credits.CreditsScreen.class, method = "skipLogic")
	// Splash
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.splash.SplashScreen.class, method = "update")
	// Text effects
	@SpirePatch(clz = com.megacrit.cardcrawl.events.GenericEventDialog.class, method = "bodyTextEffectCN")
	@SpirePatch(clz = com.megacrit.cardcrawl.events.GenericEventDialog.class, method = "bodyTextEffect")
	@SpirePatch(clz = com.megacrit.cardcrawl.events.RoomEventDialog.class, method = "bodyTextEffectCN")
	@SpirePatch(clz = com.megacrit.cardcrawl.events.RoomEventDialog.class, method = "bodyTextEffect")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.DialogWord.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.ui.DialogWord.class, method = "applyEffects")
	// ShopScreen
	@SpirePatch(clz = com.megacrit.cardcrawl.shop.ShopScreen.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.shop.ShopScreen.class, method = "updateSpeech")
	@SpirePatch(clz = com.megacrit.cardcrawl.shop.ShopScreen.class, method = "updateHand")
	@SpirePatch(clz = com.megacrit.cardcrawl.shop.ShopScreen.class, method = "updateRug")
	// RestRoom
	@SpirePatch(clz = com.megacrit.cardcrawl.rooms.CampfireUI.class, method = "updateFire")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.campfire.CampfireBurningEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.campfire.CampfireBubbleEffect.class, method = "update")
	// Particles Exordium
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.DustEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.BottomFogEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.TorchParticleSEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.LightFlareSEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.TorchParticleMEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.LightFlareMEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.TorchParticleLEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.LightFlareLEffect.class, method = "update")
	// Particles The City
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.CeilingDustCloudEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.FallingDustEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.scenes.TheCityScene.class, method = "updateParticles")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.FireFlyEffect.class, method = "update")
	// Chests
	@SpirePatch(clz = com.megacrit.cardcrawl.rooms.TreasureRoom.class, method = "updateShiny")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.ChestShineEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.SpookyChestEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.scene.SpookierChestEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.BossChestShineEffect.class, method = "update")
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen.class, method = "update")
	// DeathScreen
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.DeathScreenFloatyEffect.class, method = "update")
	// GridSelectScreen (Tramsform and Upgrade)
	@SpirePatch(clz = com.megacrit.cardcrawl.screens.select.GridCardSelectScreen.class, method = "update")
	// Math
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "mouseLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "cardLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "cardScaleLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "uiLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "orbLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "scaleLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "fadeLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "popLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "angleLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "slowColorLerpSnap")
	@SpirePatch(clz = com.megacrit.cardcrawl.helpers.MathHelper.class, method = "scrollSnapLerpSpeed")
	// Blights
	@SpirePatch(clz = AbstractBlight.class, method = "updateAnimation")
	@SpirePatch(clz = AbstractBlight.class, method = "updateFlash")
	@SpirePatch(clz = AbstractBlight.class, method = "update")
	//BattleStart
	@SpirePatch(clz = BattleStartEffect.class, method = "update")
	@SpirePatch(clz = BattleStartEffect.class, method = "updateSwords")
	// AbstractCreature
	@SpirePatch(clz = AbstractCreature.class, method = "updateFastAttackAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateSlowAttackAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateFastShakeAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateShakeAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateHopAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateJumpAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateStaggerAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateHbHoverFade")
	@SpirePatch(clz = AbstractCreature.class, method = "updateBlockAnimations")
	@SpirePatch(clz = AbstractCreature.class, method = "updateHbPopInAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateHbDamageAnimation")
	@SpirePatch(clz = AbstractCreature.class, method = "updateReticle")
	// Misc
	@SpirePatch(clz = PowerIconShowEffect.class, method = "update")
	@SpirePatch(clz = EnemyTurnEffect.class, method = "update")
	@SpirePatch(clz = GainPennyEffect.class, method = "update")
	@SpirePatch(clz = GameDeckGlowEffect.class, method = "update")
	@SpirePatch(clz = PlayerTurnEffect.class, method = "update")
	@SpirePatch(clz = RoomShineEffect.class, method = "update")
	@SpirePatch(clz = RoomShineEffect2.class, method = "update")
	@SpirePatch(clz = AbstractScene.class, method = "updateBgOverlay")
	@SpirePatch(clz = AbstractPower.class, method = "updateFontScale")
	@SpirePatch(clz = DiscardPilePanel.class, method = "updatePositions")
	@SpirePatch(clz = DiscardPilePanel.class, method = "updatePop")
	@SpirePatch(clz = DrawPilePanel.class, method = "updatePositions")
	@SpirePatch(clz = DrawPilePanel.class, method = "updatePop")
	@SpirePatch(clz = PowerBuffEffect.class, method = "update")
	@SpirePatch(clz = PowerDebuffEffect.class, method = "update")
	@SpirePatch(clz = TextAboveCreatureEffect.class, method = "update")
	@SpirePatch(clz = FlashAtkImgEffect.class, method = "update")
	@SpirePatch(clz = FlashIntentEffect.class, method = "update")
	@SpirePatch(clz = FlashPotionEffect.class, method = "update")
	@SpirePatch(clz = BlockedNumberEffect.class, method = "update")
	@SpirePatch(clz = BottomFogEffect.class, method = "update")
	@SpirePatch(clz = BottomBgPanel.class, method = "updatePositions")
	@SpirePatch(clz = CardGlowBorder.class, method = "update")
	@SpirePatch(clz = HealNumberEffect.class, method = "update")
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
	@SpirePatch(clz = com.megacrit.cardcrawl.vfx.combat.BattleStartEffect.class, method = "update")
	public static class IntentFix {
		@SpireInsertPatch(rloc = 10)
		public static void Insert(BattleStartEffect effect) {
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
				if (m.intent == Intent.DEBUG) {
					m.createIntent();
				}
			}
		}
	}
}
