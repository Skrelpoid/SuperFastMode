package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

public class LwjglApplicationPatches {

	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglApplication", method = "mainLoop")
	public static class AccelerateFPS {
		// Should patch directly before call to render
		@SpireInsertPatch(rloc = 84)
		public static void Insert(Object o) {
			SuperFastMode.accelerateFPS();
		}
	}
}
