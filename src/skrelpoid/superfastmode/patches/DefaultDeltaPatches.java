package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class DefaultDeltaPatches {
	//@formatter:off

		// Makes time not increase by multiplier.
		@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "update")
		// Fixes mouse events not registering on map
		@SpirePatch(cls = "com.megacrit.cardcrawl.screens.DungeonMapScreen", method = "updateMouse")
        // Display SpeechBubbles long enough to read
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechBubble", method = "update")
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.SpeechBubble", method = "updateScale")
        // Next prevent some flickering
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.DynamicBanner", method = "update")
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.TintEffect", method = "update")
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.CancelButton", method = "updateGlow")
		@SpirePatch(cls = "com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton", method = "updateGlow")
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.ConfirmButton", method = "updateGlow")
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.EndTurnButton", method = "glow")
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton", method = "updateGlow")
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.ProceedButton", method = "update")
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
