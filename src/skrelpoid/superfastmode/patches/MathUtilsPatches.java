package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

public class MathUtilsPatches {

	@SpirePatch(cls = "com.badlogic.gdx.math.MathUtils", method = "lerp")
	public static class LerpPatch {
		public static float Replace(float fromValue, float toValue, float progress) {
			float result = fromValue + (toValue - fromValue) * progress;
			if (result > toValue) {
				result = toValue;
			}
			return result;
		}
	}
}
