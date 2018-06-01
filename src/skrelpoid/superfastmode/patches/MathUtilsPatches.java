package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

public class MathUtilsPatches {

	// I do not know why this is not the way this method works by default
	// Fixes pretty much everything that uses this method
	@SpirePatch(cls = "com.badlogic.gdx.math.MathUtils", method = "lerp")
	public static class LerpPatch {
		public static float Replace(float fromValue, float toValue, float progress) {
			float result = fromValue + (toValue - fromValue) * progress;
			// result should never be higher than toValue
			if (result > toValue || SuperFastMode.isInstantLerp) {
				result = toValue;
			}
			return result;
		}
	}
}
