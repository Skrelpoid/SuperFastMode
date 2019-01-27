package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

// The methods patched here normally return a value between start and target.
// If start == target, these methods all return target. This makes many things instant
// E.G. Cards that animate from your deck to your hand will be instantly in
// your hand because currentX will be set to targetX instantly
public class MathHelperPatches {

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
	public static class InstantLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.instantLerp(start, target);
		}
	}
}
