package skrelpoid.superfastmode.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class AbstractDungeonPatches {
	//@formatter:off

	// Makes time not increase by multiplier.
    // Do not increase the timer as this would count it multiple times per frame
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "update")
	public static class TimerPatch {
		public static ExprEditor Instrument() {
			return new ExprEditor() {
				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (m.getMethodName().equals("getDeltaTime")) {
						m.replace("{ $_ = skrelpoid.superfastmode.SuperFastMode.getDelta(); }");
					}
				}
			};
		}
	}

}
