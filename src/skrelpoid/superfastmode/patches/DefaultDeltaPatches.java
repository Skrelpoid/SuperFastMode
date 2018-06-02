package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

//All methods patched in this class should always get the real delta time

//TODO add to this patch: icons on the top right + energy display, draw pile, discard pile, enemy intents
public class DefaultDeltaPatches {
	//@formatter:off

		// Makes time not increase by multiplier.
		@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "update")
		// Fixes mouse events not registering on map
		@SpirePatch(cls = "com.megacrit.cardcrawl.screens.DungeonMapScreen", method = "updateMouse")
        // Display SpeechBubbles long enough to read
        // Known issue: text stays longer than bubble. Probably leave it like that
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.SpeechWord", method = "applyEffects")
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechBubble", method = "update")
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechBubble", method = "updateScale")
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
        // Fixes Intents
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.BobEffect", method = "update")
		@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateIntentVFX")
		@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "render")
		@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "renderName")
		@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "renderIntent")
		@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateDeathAnimation")
		@SpirePatch(cls = "com.megacrit.cardcrawl.monsters.AbstractMonster", method = "updateEscapeAnimation")
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
}
