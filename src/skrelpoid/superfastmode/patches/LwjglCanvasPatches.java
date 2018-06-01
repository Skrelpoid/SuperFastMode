package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

public class LwjglCanvasPatches {
	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglCanvas$3", method = "run")
	public static class AccelerateFPS {
		// Should patch directly before call to render
		@SpireInsertPatch(rloc = 35)
		public static void Insert(Object o) {
			SuperFastMode.accelerateFPS();
		}
	}

}
