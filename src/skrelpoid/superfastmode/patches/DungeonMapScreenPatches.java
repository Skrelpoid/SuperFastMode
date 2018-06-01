package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class DungeonMapScreenPatches {

	// ClickTimer should have unmodified delta time
	@SpirePatch(cls = "com.megacrit.cardcrawl.screens.DungeonMapScreen", method = "updateMouse")
	public static class ClickTimer {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getDeltaTime")) {
						m.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.getDelta(0.016f); } ");
					}
				}
			};
		}
	}

}
