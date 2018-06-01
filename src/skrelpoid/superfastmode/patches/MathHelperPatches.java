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
	public static class MouseLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "cardLerpSnap")
	public static class CardLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "cardScaleLerpSnap")
	public static class CardScaleLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "uiLerpSnap")
	public static class UILerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "orbLerpSnap")
	public static class OrbLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "scaleLerpSnap")
	public static class ScaleLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "fadeLerpSnap")
	public static class FadeLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "popLerpSnap")
	public static class PopLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "angleLerpSnap")
	public static class AngleLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "slowColorLerpSnap")
	public static class SlowColorLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}

	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.MathHelper", method = "scrollSnapLerpSpeed")
	public static class ScrollLerp {
		public static void Prefix(@ByRef float[] start, float target) {
			SuperFastMode.lerp(start, target);
		}
	}
}
