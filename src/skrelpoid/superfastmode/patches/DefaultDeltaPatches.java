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
        // Next 2 prevent some flickering
		@SpirePatch(cls = "com.megacrit.cardcrawl.ui.buttons.DynamicBanner", method = "update")
		@SpirePatch(cls = "com.megacrit.cardcrawl.vfx.TintEffect", method = "update")
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
