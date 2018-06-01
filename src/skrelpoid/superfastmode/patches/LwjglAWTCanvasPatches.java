package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import skrelpoid.superfastmode.SuperFastMode;

// This is only backup as Slay the Spire currently uses LwjglApplication
public class LwjglAWTCanvasPatches {
	@SpirePatch(cls = "com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas", method = "render")
	public static class AccelerateFPS {
		// Should patch directly before call to render
		@SpireInsertPatch(rloc = 28)
		public static void Insert(Object o, boolean b) {
			SuperFastMode.accelerateFPS();
		}
	}
}
