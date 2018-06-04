package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

public class MathUtilsPatches {

	// I do not know why this is not the way this method works by default
	// Fixes pretty much everything that uses this method
	@SpirePatch(cls = "com.badlogic.gdx.math.MathUtils", method = "lerp")
	public static class LerpPatch {
		public static float Replace(float fromValue, float toValue, float progress) {
			float result = lerp(fromValue, toValue, progress);
			// result should never be higher than toValue
			if (result > toValue || SuperFastMode.isInstantLerp) {
				result = toValue;
			}
			return result;
		}
	}

	public static float lerp(float from, float to, float progress) {
		return from + (to - from) * progress;
	}
}
