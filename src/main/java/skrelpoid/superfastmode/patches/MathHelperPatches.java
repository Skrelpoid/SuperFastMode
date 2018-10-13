package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

// The methods patched here normally return a value between start and target.
// If start == target, these methods all return target. This makes many things instant
// E.G. Cards that animate from your deck to your hand will be instantly in
// your hand because currentX will be set to targetX instantly
public class MathHelperPatches {

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "mouseLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "cardLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "cardScaleLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "uiLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "orbLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "scaleLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "fadeLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "popLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "angleLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "slowColorLerpSnap")
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "scrollSnapLerpSpeed")
	public static class InstantLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.instantLerp(start, target);
		}
	}
}
